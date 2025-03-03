package net.digitalingot.feather.serverapi.examples.shop.ui;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Map;
import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.digitalingot.feather.serverapi.api.ui.rpc.RpcController;
import net.digitalingot.feather.serverapi.api.ui.rpc.RpcHandler;
import net.digitalingot.feather.serverapi.api.ui.rpc.RpcRequest;
import net.digitalingot.feather.serverapi.api.ui.rpc.RpcResponse;
import net.digitalingot.feather.serverapi.examples.shop.ShopCategory;
import net.digitalingot.feather.serverapi.examples.shop.ShopItem;
import net.digitalingot.feather.serverapi.examples.shop.ShopPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopController implements RpcController {
  private static final Gson GSON = new Gson();
  private final ShopPlugin plugin;

  public ShopController(ShopPlugin plugin) {
    this.plugin = plugin;
  }

  @RpcHandler("getCategory")
  public void getCategory(RpcRequest request, RpcResponse response) {
    FeatherPlayer player = request.getSource();

    try {
      JsonObject requestData = GSON.fromJson(request.getBody(), JsonObject.class);
      String categoryId = requestData.get("categoryId").getAsString();

      ShopCategory category = this.plugin.getShopManager().getCategory(categoryId);
      if (category == null) {
        sendErrorResponse(response, "Category not found");
        return;
      }

      // Create response with category items
      JsonObject responseData = new JsonObject();
      responseData.addProperty("categoryId", category.getId());
      responseData.addProperty("categoryName", category.getName());

      JsonArray itemsArray = new JsonArray();
      for (ShopItem item : category.getItems()) {
        JsonObject itemObj = new JsonObject();
        itemObj.addProperty("id", item.getId());
        itemObj.addProperty("name", item.getName());
        itemObj.addProperty("material", item.getMaterial());

        if (item.canBuy()) {
          itemObj.addProperty("buyPrice", item.getBuyPrice());
        }

        if (item.canSell()) {
          itemObj.addProperty("sellPrice", item.getSellPrice());
        }

        // Check if player has this item and how many
        int playerQuantity = getPlayerItemQuantity(player, item);
        itemObj.addProperty("playerQuantity", playerQuantity);

        // Add description if available
        if (!item.getDescription().isEmpty()) {
          itemObj.addProperty("description", item.getDescription());
        }

        // Add purchase limit if applicable
        if (item.getMaxPurchase() > 0) {
          itemObj.addProperty("maxPurchase", item.getMaxPurchase());
        }

        itemsArray.add(itemObj);
      }

      responseData.add("items", itemsArray);
      response.respond(GSON.toJson(responseData));

    } catch (Exception error) {
      this.plugin
          .getLogger()
          .warning("Error processing getCategory request: " + error.getMessage());
      sendErrorResponse(response, "Invalid request format");
    }
  }

  @RpcHandler("buyItem")
  public void buyItem(RpcRequest request, RpcResponse response) {
    FeatherPlayer player = request.getSource();

    try {
      JsonObject requestData = GSON.fromJson(request.getBody(), JsonObject.class);
      String categoryId = requestData.get("categoryId").getAsString();
      String itemId = requestData.get("itemId").getAsString();
      int quantity = requestData.get("quantity").getAsInt();

      if (quantity <= 0) {
        sendErrorResponse(response, "Invalid quantity");
        return;
      }

      // Get the item
      ShopItem item = this.plugin.getShopManager().getItem(categoryId, itemId);
      if (item == null) {
        sendErrorResponse(response, "Item not found");
        return;
      }

      if (!item.canBuy()) {
        sendErrorResponse(response, "This item cannot be purchased");
        return;
      }

      // Check purchase limits
      if (item.getMaxPurchase() > 0 && quantity > item.getMaxPurchase()) {
        // Check if player can bypass limits
        Player bukkitPlayer = Bukkit.getPlayer(player.getUniqueId());
        if (bukkitPlayer != null && !bukkitPlayer.hasPermission("shop.bypass.limit")) {
          sendErrorResponse(
              response, "You can only buy " + item.getMaxPurchase() + " of this item at once");
          return;
        }
      }

      // Apply global limit if configured
      int globalLimit = this.plugin.getConfig().getInt("settings.max-purchase-quantity", 64);
      if (globalLimit > 0 && quantity > globalLimit) {
        // Check if player can bypass limits
        Player bukkitPlayer = Bukkit.getPlayer(player.getUniqueId());
        if (bukkitPlayer != null && !bukkitPlayer.hasPermission("shop.bypass.limit")) {
          sendErrorResponse(response, "You can only buy up to " + globalLimit + " items at once");
          return;
        }
      }

      // Calculate total cost
      double totalCost = item.getBuyPrice() * quantity;

      // Check player balance
      double playerBalance = this.plugin.getEconomyProvider().getBalance(player);
      if (playerBalance < totalCost) {
        sendErrorResponse(response, "Insufficient funds");
        return;
      }

      // Process purchase
      boolean success = processPurchase(player, item, quantity, totalCost);

      if (!success) {
        sendErrorResponse(response, "Failed to process purchase");
        return;
      }

      // Send success response
      JsonObject responseData = new JsonObject();
      responseData.addProperty("success", true);
      responseData.addProperty("newBalance", this.plugin.getEconomyProvider().getBalance(player));
      responseData.addProperty(
          "message", "Successfully purchased " + quantity + " " + item.getName());

      response.respond(GSON.toJson(responseData));

    } catch (Exception error) {
      this.plugin.getLogger().warning("Error processing buyItem request: " + error.getMessage());
      sendErrorResponse(response, "Invalid request format");
    }
  }

  @RpcHandler("sellItem")
  public void sellItem(RpcRequest request, RpcResponse response) {
    FeatherPlayer player = request.getSource();

    try {
      JsonObject requestData = GSON.fromJson(request.getBody(), JsonObject.class);
      String categoryId = requestData.get("categoryId").getAsString();
      String itemId = requestData.get("itemId").getAsString();
      int quantity = requestData.get("quantity").getAsInt();

      if (quantity <= 0) {
        sendErrorResponse(response, "Invalid quantity");
        return;
      }

      // Get the item
      ShopItem item = this.plugin.getShopManager().getItem(categoryId, itemId);
      if (item == null) {
        sendErrorResponse(response, "Item not found");
        return;
      }

      if (!item.canSell()) {
        sendErrorResponse(response, "This item cannot be sold");
        return;
      }

      // Check if player has enough items
      int playerQuantity = getPlayerItemQuantity(player, item);
      if (playerQuantity < quantity) {
        sendErrorResponse(response, "You don't have enough items to sell");
        return;
      }

      // Calculate total value
      double totalValue = item.getSellPrice() * quantity;

      // Process sale
      boolean success = processSale(player, item, quantity, totalValue);

      if (!success) {
        sendErrorResponse(response, "Failed to process sale");
        return;
      }

      // Send success response
      JsonObject responseData = new JsonObject();
      responseData.addProperty("success", true);
      responseData.addProperty("newBalance", this.plugin.getEconomyProvider().getBalance(player));
      responseData.addProperty("message", "Successfully sold " + quantity + " " + item.getName());

      response.respond(GSON.toJson(responseData));

    } catch (Exception error) {
      this.plugin.getLogger().warning("Error processing sellItem request: " + error.getMessage());
      sendErrorResponse(response, "Invalid request format");
    }
  }

  @RpcHandler("searchItems")
  public void searchItems(RpcRequest request, RpcResponse response) {
    FeatherPlayer player = request.getSource();

    try {
      String searchQuery = request.getBody().trim().toLowerCase();

      if (searchQuery.isEmpty()) {
        sendErrorResponse(response, "Empty search query");
        return;
      }

      JsonObject responseData = new JsonObject();
      JsonArray resultsArray = new JsonArray();

      // Search through all categories and items
      for (ShopCategory category : this.plugin.getShopManager().getCategories()) {
        for (ShopItem item : category.getItems()) {
          if (item.getName().toLowerCase().contains(searchQuery)
              || item.getMaterial().toLowerCase().contains(searchQuery)
              || item.getDescription().toLowerCase().contains(searchQuery)) {

            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("categoryId", category.getId());
            resultObj.addProperty("categoryName", category.getName());
            resultObj.addProperty("id", item.getId());
            resultObj.addProperty("name", item.getName());
            resultObj.addProperty("material", item.getMaterial());

            // Check if player has this item and how many
            int playerQuantity = getPlayerItemQuantity(player, item);
            resultObj.addProperty("playerQuantity", playerQuantity);

            if (!item.getDescription().isEmpty()) {
              resultObj.addProperty("description", item.getDescription());
            }

            if (item.canBuy()) {
              resultObj.addProperty("buyPrice", item.getBuyPrice());
            }

            if (item.canSell()) {
              resultObj.addProperty("sellPrice", item.getSellPrice());
            }

            if (item.getMaxPurchase() > 0) {
              resultObj.addProperty("maxPurchase", item.getMaxPurchase());
            }

            resultsArray.add(resultObj);
          }
        }
      }

      responseData.add("results", resultsArray);
      responseData.addProperty("query", searchQuery);

      response.respond(GSON.toJson(responseData));

    } catch (Exception error) {
      this.plugin
          .getLogger()
          .warning("Error processing searchItems request: " + error.getMessage());
      sendErrorResponse(response, "Invalid request format");
    }
  }

  private void sendErrorResponse(RpcResponse response, String errorMessage) {
    JsonObject error = new JsonObject();
    error.addProperty("success", false);
    error.addProperty("error", errorMessage);
    response.respond(GSON.toJson(error));
  }

  private int getPlayerItemQuantity(FeatherPlayer featherPlayer, ShopItem item) {
    Player player = Bukkit.getPlayer(featherPlayer.getUniqueId());
    if (player == null) return 0;

    int count = 0;
    Material material;
    try {
      material = Material.valueOf(item.getMaterial());
    } catch (IllegalArgumentException e) {
      return 0;
    }

    for (ItemStack stack : player.getInventory().getContents()) {
      if (stack != null && stack.getType() == material) {
        count += stack.getAmount();
      }
    }

    return count;
  }

  private boolean processPurchase(
      FeatherPlayer featherPlayer, ShopItem item, int quantity, double totalCost) {
    Player player = Bukkit.getPlayer(featherPlayer.getUniqueId());
    if (player == null) return false;

    // Withdraw money
    if (!this.plugin.getEconomyProvider().withdraw(featherPlayer, totalCost)) {
      return false;
    }

    // Give items
    Material material;
    try {
      material = Material.valueOf(item.getMaterial());
    } catch (IllegalArgumentException e) {
      // Refund the money since we couldn't give the item
      this.plugin.getEconomyProvider().deposit(featherPlayer, totalCost);
      return false;
    }

    ItemStack itemStack = new ItemStack(material, quantity);
    Map<Integer, ItemStack> leftover = player.getInventory().addItem(itemStack);

    if (!leftover.isEmpty()) {
      // Drop items that don't fit in inventory
      for (ItemStack stack : leftover.values()) {
        player.getWorld().dropItemNaturally(player.getLocation(), stack);
      }

      player.sendMessage("Some items were dropped because your inventory is full.");
    }

    // Log the transaction if debugging is enabled
    if (this.plugin.getConfig().getBoolean("debug.enabled", false)) {
      this.plugin
          .getLogger()
          .info(
              String.format(
                  "Purchase: Player %s bought %d %s for %.2f %s",
                  player.getName(),
                  quantity,
                  item.getName(),
                  totalCost,
                  this.plugin.getEconomyProvider().getCurrencyNamePlural()));
    }

    return true;
  }

  private boolean processSale(
      FeatherPlayer featherPlayer, ShopItem item, int quantity, double totalValue) {
    Player player = Bukkit.getPlayer(featherPlayer.getUniqueId());
    if (player == null) return false;

    Material material;
    try {
      material = Material.valueOf(item.getMaterial());
    } catch (IllegalArgumentException e) {
      return false;
    }

    // Remove items
    int remaining = quantity;
    for (int iii = 0; iii < player.getInventory().getSize(); iii++) {
      ItemStack stack = player.getInventory().getItem(iii);
      if (stack != null && stack.getType() == material) {
        if (stack.getAmount() <= remaining) {
          remaining -= stack.getAmount();
          player.getInventory().setItem(iii, null);
        } else {
          stack.setAmount(stack.getAmount() - remaining);
          player.getInventory().setItem(iii, stack);
          remaining = 0;
        }

        if (remaining == 0) {
          break;
        }
      }
    }

    if (remaining > 0) {
      // This shouldn't happen if we checked correctly
      return false;
    }

    // Add money
    boolean success = this.plugin.getEconomyProvider().deposit(featherPlayer, totalValue);

    // Log the transaction if debugging is enabled
    if (success && this.plugin.getConfig().getBoolean("debug.enabled", false)) {
      this.plugin
          .getLogger()
          .info(
              String.format(
                  "Sale: Player %s sold %d %s for %.2f %s",
                  player.getName(),
                  quantity,
                  item.getName(),
                  totalValue,
                  this.plugin.getEconomyProvider().getCurrencyNamePlural()));
    }

    return success;
  }
}
