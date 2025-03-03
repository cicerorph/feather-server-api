---
id: ui-comprehensive-example
title: Comprehensive UI Example
sidebar_label: Comprehensive Example
description: A complete example of a server shop UI using Feather UI's
sidebar_position: 3
---

import ReactPlayer from 'react-player'
import ShopDemoVideoUrl from './assets/shop-demo.mp4';

# Comprehensive UI Example: Server Shop

This guide walks through building a complete server shop UI using Feather UI's. We'll cover the entire process from crafting the server-side code to designing the client-side interface.

<ReactPlayer playing loop muted url={ShopDemoVideoUrl} />
*Example of the completed server shop UI showing categories, items, and player balance.*

## Project Overview

Our shop UI will feature:

1. Item categories and browsing
2. Item purchasing with confirmation
3. Player balance display and management
4. Search functionality
5. Responsive design for different screen sizes

> **Note:** The complete source code for this example is available on [GitHub](https://github.com/FeatherMC/feather-server-api/tree/main/examples/) with pre-built downloads available in the [releases section](https://github.com/FeatherMC/feather-server-api/releases).

## Server-Side Implementation

### 1. Main Plugin Class

```java
public class ShopPlugin extends JavaPlugin {
  private UIPage shopPage;
  private ShopManager shopManager;
  private EconomyProvider economyProvider;

  @Override
  public void onEnable() {
    // Initialize dependencies
    this.shopManager = new ShopManager();
    this.economyProvider = new VaultEconomyProvider();

    // Load shop items
    loadShopItems();

    // Create the UI page
    createShopUI();

    // Register event listeners
    FeatherAPI.getEventService().subscribe(PlayerHelloEvent.class, this::onPlayerHello, this);

    // Register commands
    getCommand("shop").setExecutor(new ShopCommand(this));
  }

  private void loadShopItems() {
    // Load shop categories and items from config
    ConfigurationSection categoriesSection = getConfig().getConfigurationSection("categories");
    if (categoriesSection == null) {
      saveDefaultConfig();
      categoriesSection = getConfig().getConfigurationSection("categories");
    }

    for (String categoryKey : categoriesSection.getKeys(false)) {
      String categoryName = categoriesSection.getString(categoryKey + ".name");
      String categoryIcon = categoriesSection.getString(categoryKey + ".icon");

      ShopCategory category = new ShopCategory(categoryKey, categoryName, categoryIcon);

      ConfigurationSection itemsSection =
          categoriesSection.getConfigurationSection(categoryKey + ".items");
      if (itemsSection != null) {
        for (String itemKey : itemsSection.getKeys(false)) {
          String itemName = itemsSection.getString(itemKey + ".name");
          String itemMaterial = itemsSection.getString(itemKey + ".material");
          double buyPrice = itemsSection.getDouble(itemKey + ".buy-price", -1);
          double sellPrice = itemsSection.getDouble(itemKey + ".sell-price", -1);

          ShopItem item = new ShopItem(itemKey, itemName, itemMaterial, buyPrice, sellPrice);
          category.addItem(item);
        }
      }

      this.shopManager.addCategory(category);
    }
  }

  private void createShopUI() {
    // Load HTML content from resources
    String htmlContent;
    try {
      htmlContent = loadResource("shop.html");
    } catch (IOException e) {
      getLogger().severe("Failed to load shop UI HTML");
      e.printStackTrace();
      return;
    }

    // Create data URL
    String dataUrl =
        "data:text/html;base64," + Base64.getEncoder().encodeToString(htmlContent.getBytes());

    // Register UI page
    this.shopPage = FeatherAPI.getUIService().registerPage(this, dataUrl);

    // Set up handlers
    this.shopPage.setLifecycleHandler(new ShopLifecycleHandler(this));
    this.shopPage.setFocusHandler(new ShopFocusHandler(this));
    this.shopPage.setVisibilityHandler(new ShopVisibilityHandler(this));

    // Register RPC controller
    FeatherAPI.getUIService().registerCallbacks(this.shopPage, new ShopController(this));
  }

  private String loadResource(String resourceName) throws IOException {
    try (InputStream is = getClassLoader().getResourceAsStream(resourceName)) {
      if (is == null) {
        throw new IOException("Resource not found: " + resourceName);
      }
      return new String(ByteStreams.toByteArray(is), StandardCharsets.UTF_8);
    }
  }

  public void openShopForPlayer(FeatherPlayer player) {
    // Create the page if it doesn't exist for this player
    FeatherAPI.getUIService().createPageForPlayer(player, this.shopPage);

    // Show the page and give it focus
    FeatherAPI.getUIService().openPageForPlayer(player, this.shopPage);

    // Send initial shop data
    sendShopData(player);
  }

  private void sendShopData(FeatherPlayer player) {
    // Create shop data message
    JsonObject message = new JsonObject();
    message.addProperty("type", "shopData");

    // Add player balance
    message.addProperty("balance", this.economyProvider.getBalance(player));

    // Add categories
    JsonArray categoriesArray = new JsonArray();
    for (ShopCategory category : this.shopManager.getCategories()) {
      JsonObject categoryObj = new JsonObject();
      categoryObj.addProperty("id", category.getId());
      categoryObj.addProperty("name", category.getName());
      categoryObj.addProperty("icon", category.getIcon());
      categoriesArray.add(categoryObj);
    }
    message.add("categories", categoriesArray);

    // Send message
    FeatherAPI.getUIService().sendPageMessage(player, this.shopPage, new Gson().toJson(message));
  }

  private void onPlayerHello(PlayerHelloEvent event) {
    // Optionally open shop automatically when player joins
    // or just wait for them to use the /shop command
  }

  public ShopManager getShopManager() {
    return this.shopManager;
  }

  public EconomyProvider getEconomyProvider() {
    return this.economyProvider;
  }

  public UIPage getShopPage() {
    return this.shopPage;
  }
}
```

### 2. Shop Data Models

```java
// Shop Category
public class ShopCategory {
  private final String id;
  private final String name;
  private final String icon;
  private final Map<String, ShopItem> items = new HashMap<>();
  
  public ShopCategory(String id, String name, String icon) {
    this.id = id;
    this.name = name;
    this.icon = icon;
  }
  
  public void addItem(ShopItem item) {
    this.items.put(item.getId(), item);
  }
  
  public Collection<ShopItem> getItems() {
    return Collections.unmodifiableCollection(this.items.values());
  }
  
  public ShopItem getItem(String id) {
    return this.items.get(id);
  }
  
  // Getters
  public String getId() { return this.id; }
  public String getName() { return this.name; }
  public String getIcon() { return this.icon; }
}

// Shop Item
public class ShopItem {
  private final String id;
  private final String name;
  private final String material;
  private final double buyPrice;
  private final double sellPrice;
  
  public ShopItem(String id, String name, String material, double buyPrice, double sellPrice) {
    this.id = id;
    this.name = name;
    this.material = material;
    this.buyPrice = buyPrice;
    this.sellPrice = sellPrice;
  }
  
  // Getters
  public String getId() { return this.id; }
  public String getName() { return this.name; }
  public String getMaterial() { return this.material; }
  public double getBuyPrice() { return this.buyPrice; }
  public double getSellPrice() { return this.sellPrice; }
  public boolean canBuy() { return this.buyPrice >= 0; }
  public boolean canSell() { return this.sellPrice >= 0; }
}

// Shop Manager
public class ShopManager {
  private final Map<String, ShopCategory> categories = new HashMap<>();

  public void addCategory(ShopCategory category) {
    this.categories.put(category.getId(), category);
  }

  public Collection<ShopCategory> getCategories() {
    return Collections.unmodifiableCollection(this.categories.values());
  }

  public ShopCategory getCategory(String id) {
    return this.categories.get(id);
  }

  public ShopItem getItem(String categoryId, String itemId) {
    ShopCategory category = getCategory(categoryId);
    return category != null ? category.getItem(itemId) : null;
  }
}
```

### 3. UI Handlers

```java
// Lifecycle Handler
public class ShopLifecycleHandler extends UILifecycleHandlerAdapter {
  private final ShopPlugin plugin;

  public ShopLifecycleHandler(ShopPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void onCreated(FeatherPlayer player) {
    this.plugin.getLogger().info("Shop UI created for " + player.getName());
  }

  @Override
  public void onDestroyed(FeatherPlayer player) {
    this.plugin.getLogger().info("Shop UI destroyed for " + player.getName());
  }
}

// Focus Handler
public class ShopFocusHandler extends UIFocusHandlerAdapter {
  private final ShopPlugin plugin;

  public ShopFocusHandler(ShopPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void onFocusGained(FeatherPlayer player) {
    // Update player balance when focus is gained
    updatePlayerBalance(player);
  }

  private void updatePlayerBalance(FeatherPlayer player) {
    JsonObject message = new JsonObject();
    message.addProperty("type", "updateBalance");
    message.addProperty("balance", this.plugin.getEconomyProvider().getBalance(player));

    FeatherAPI.getUIService()
        .sendPageMessage(player, this.plugin.getShopPage(), new Gson().toJson(message));
  }
}


// Visibility Handler
public class ShopVisibilityHandler extends UIVisibilityHandlerAdapter {
  private final ShopPlugin plugin;

  public ShopVisibilityHandler(ShopPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void onShow(FeatherPlayer player) {
    // Update shop data when shown
    this.plugin.sendShopData(player);
  }
}

```

### 4. RPC Controller

```java
public class ShopController implements RpcController {
  private final ShopPlugin plugin;

  public ShopController(ShopPlugin plugin) {
    this.plugin = plugin;
  }

  @RpcHandler("getCategory")
  public void getCategory(RpcRequest request, RpcResponse response) {
    FeatherPlayer player = request.getSource();

    try {
      JsonObject requestData = new Gson().fromJson(request.getBody(), JsonObject.class);
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

        itemsArray.add(itemObj);
      }

      responseData.add("items", itemsArray);
      response.respond(new Gson().toJson(responseData));

    } catch (Exception e) {
      this.plugin.getLogger().warning("Error processing getCategory request: " + e.getMessage());
      sendErrorResponse(response, "Invalid request format");
    }
  }

  @RpcHandler("buyItem")
  public void buyItem(RpcRequest request, RpcResponse response) {
    FeatherPlayer player = request.getSource();

    try {
      JsonObject requestData = new Gson().fromJson(request.getBody(), JsonObject.class);
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

      response.respond(new Gson().toJson(responseData));

    } catch (Exception e) {
      this.plugin.getLogger().warning("Error processing buyItem request: " + e.getMessage());
      sendErrorResponse(response, "Invalid request format");
    }
  }

  @RpcHandler("sellItem")
  public void sellItem(RpcRequest request, RpcResponse response) {
    FeatherPlayer player = request.getSource();

    try {
      JsonObject requestData = new Gson().fromJson(request.getBody(), JsonObject.class);
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

      response.respond(new Gson().toJson(responseData));

    } catch (Exception e) {
      this.plugin.getLogger().warning("Error processing sellItem request: " + e.getMessage());
      sendErrorResponse(response, "Invalid request format");
    }
  }

  @RpcHandler("searchItems")
  public void searchItems(RpcRequest request, RpcResponse response) {
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
              || item.getMaterial().toLowerCase().contains(searchQuery)) {

            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("categoryId", category.getId());
            resultObj.addProperty("categoryName", category.getName());
            resultObj.addProperty("id", item.getId());
            resultObj.addProperty("name", item.getName());
            resultObj.addProperty("material", item.getMaterial());

            if (item.canBuy()) {
              resultObj.addProperty("buyPrice", item.getBuyPrice());
            }

            if (item.canSell()) {
              resultObj.addProperty("sellPrice", item.getSellPrice());
            }

            resultsArray.add(resultObj);
          }
        }
      }

      responseData.add("results", resultsArray);
      responseData.addProperty("query", searchQuery);

      response.respond(new Gson().toJson(responseData));

    } catch (Exception e) {
      this.plugin.getLogger().warning("Error processing searchItems request: " + e.getMessage());
      sendErrorResponse(response, "Invalid request format");
    }
  }

  private void sendErrorResponse(RpcResponse response, String errorMessage) {
    JsonObject error = new JsonObject();
    error.addProperty("success", false);
    error.addProperty("error", errorMessage);
    response.respond(new Gson().toJson(error));
  }

  private int getPlayerItemQuantity(FeatherPlayer featherPlayer, ShopItem item) {
    Player player = Bukkit.getPlayer(featherPlayer.getUniqueId());
    if (player == null) return 0;

    int count = 0;
    Material material = Material.getMaterial(item.getMaterial());
    if (material == null) return 0;

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
    Material material = Material.getMaterial(item.getMaterial());
    if (material == null) return false;

    ItemStack itemStack = new ItemStack(material, quantity);
    Map<Integer, ItemStack> leftover = player.getInventory().addItem(itemStack);

    if (!leftover.isEmpty()) {
      // Drop items that don't fit in inventory
      for (ItemStack stack : leftover.values()) {
        player.getWorld().dropItemNaturally(player.getLocation(), stack);
      }

      player.sendMessage("Some items were dropped because your inventory is full.");
    }

    return true;
  }

  private boolean processSale(
      FeatherPlayer featherPlayer, ShopItem item, int quantity, double totalValue) {
    Player player = Bukkit.getPlayer(featherPlayer.getUniqueId());
    if (player == null) return false;

    Material material = Material.getMaterial(item.getMaterial());
    if (material == null) return false;

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
    return this.plugin.getEconomyProvider().deposit(featherPlayer, totalValue);
  }
}
```

## Client-Side Implementation

### HTML and CSS

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Server Shop</title>
    <style>
      :root {
        --primary-color: #4CAF50;
        --primary-hover: #45a049;
        --secondary-color: #2196F3;
        --danger-color: #f44336;
        --bg-color: rgba(0, 0, 0, 0.8);
        --panel-color: rgba(51, 51, 51, 0.95);
        --border-color: #444;
      }

      * {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
      }

      body {
        font-family: Arial, sans-serif;
        color: white;
        background-color: transparent;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
        user-select: none;
      }

      .shop-container {
        display: flex;
        width: 90%;
        max-width: 1200px;
        height: 80%;
        background-color: var(--bg-color);
        border-radius: 8px;
        overflow: hidden;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.5);
      }

      .sidebar {
        width: 25%;
        min-width: 200px;
        background-color: var(--panel-color);
        padding: 20px 10px;
        border-right: 1px solid var(--border-color);
        overflow-y: auto;
      }

      .main-content {
        flex: 1;
        display: flex;
        flex-direction: column;
      }

      .shop-header {
        padding: 15px 20px;
        background-color: var(--panel-color);
        border-bottom: 1px solid var(--border-color);
        display: flex;
        justify-content: space-between;
        align-items: center;
      }

      .shop-title {
        font-size: 24px;
        font-weight: bold;
      }

      .shop-balance {
        font-size: 18px;
        color: gold;
      }

      .search-bar {
        padding: 10px 20px;
        background-color: var(--panel-color);
        border-bottom: 1px solid var(--border-color);
      }

      .search-input {
        width: 100%;
        padding: 8px 12px;
        background-color: rgba(0, 0, 0, 0.3);
        border: 1px solid var(--border-color);
        border-radius: 4px;
        color: white;
        font-family: inherit;
      }

      .search-input:focus {
        outline: none;
        border-color: var(--primary-color);
      }

      .items-container {
        flex: 1;
        padding: 20px;
        overflow-y: auto;
      }

      .item-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
        gap: 15px;
      }

      .category-title {
        margin-bottom: 15px;
        padding-bottom: 5px;
        border-bottom: 1px solid var(--border-color);
      }

      .search-results-header {
        display: flex;
        justify-content: space-between;
        margin-bottom: 15px;
        padding-bottom: 5px;
        border-bottom: 1px solid var(--border-color);
      }

      .back-to-categories {
        color: var(--secondary-color);
        cursor: pointer;
      }

      .back-to-categories:hover {
        text-decoration: underline;
      }

      .category-item {
        padding: 10px;
        margin-bottom: 8px;
        background-color: rgba(255, 255, 255, 0.1);
        border-radius: 4px;
        cursor: pointer;
        transition: all 0.2s;
        display: flex;
        align-items: center;
      }

      .category-item:hover {
        background-color: rgba(255, 255, 255, 0.2);
      }

      .category-item.active {
        background-color: var(--primary-color);
      }

      .category-icon {
        margin-right: 10px;
        font-weight: bold;
      }

      .shop-item {
        background-color: var(--panel-color);
        border-radius: 6px;
        padding: 15px;
        display: flex;
        flex-direction: column;
        align-items: center;
        border: 1px solid var(--border-color);
      }

      .item-image {
        width: 40px;
        height: 40px;
        background-color: #555;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: 10px;
        font-size: 20px;
        font-weight: bold;
      }

      .item-name {
        margin-bottom: 10px;
        text-align: center;
        font-weight: bold;
      }

      .item-price {
        margin-bottom: 5px;
        color: gold;
      }

      .item-quantity {
        margin-bottom: 10px;
        font-size: 14px;
        color: #aaa;
      }

      .item-buttons {
        display: flex;
        gap: 5px;
        width: 100%;
      }

      .buy-button,
      .sell-button {
        padding: 6px 12px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-family: inherit;
        font-weight: bold;
        flex: 1;
      }

      .buy-button {
        background-color: var(--primary-color);
        color: white;
      }

      .buy-button:hover {
        background-color: var(--primary-hover);
      }

      .sell-button {
        background-color: var(--danger-color);
        color: white;
      }

      .sell-button:hover {
        background-color: #d32f2f;
      }

      .button-disabled {
        opacity: 0.5;
        cursor: not-allowed;
      }

      .empty-message {
        text-align: center;
        padding: 40px;
        color: #aaa;
      }

      .modal-overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.7);
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 100;
      }

      .modal-content {
        background-color: var(--panel-color);
        padding: 20px;
        border-radius: 8px;
        width: 90%;
        max-width: 400px;
      }

      .modal-header {
        font-size: 18px;
        margin-bottom: 15px;
        border-bottom: 1px solid var(--border-color);
        padding-bottom: 10px;
      }

      .modal-body {
        margin-bottom: 20px;
      }

      .quantity-selector {
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 20px 0;
      }

      .quantity-button {
        padding: 5px 12px;
        background-color: var(--secondary-color);
        border: none;
        color: white;
        font-weight: bold;
        cursor: pointer;
        font-size: 16px;
      }

      .quantity-button:hover {
        background-color: #0b7dda;
      }

      .quantity-input {
        width: 80px;
        padding: 5px;
        text-align: center;
        margin: 0 10px;
        background-color: rgba(0, 0, 0, 0.3);
        border: 1px solid var(--border-color);
        color: white;
        font-family: inherit;
        font-size: 16px;
      }

      .quantity-total {
        text-align: center;
        font-size: 18px;
        color: gold;
        margin-top: 10px;
      }

      .modal-footer {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
      }

      .modal-button,
      .close-button {
        padding: 8px 16px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-family: inherit;
      }

      .confirm-button {
        background-color: var(--primary-color);
        color: white;
      }

      .confirm-button:hover {
        background-color: var(--primary-hover);
      }

      .cancel-button {
        background-color: #555;
        color: white;
      }

      .cancel-button:hover {
        background-color: #666;
      }

      .loading-spinner {
        border: 3px solid rgba(255, 255, 255, 0.3);
        border-radius: 50%;
        border-top: 3px solid var(--primary-color);
        width: 20px;
        height: 20px;
        animation: spin 1s linear infinite;
        display: inline-block;
        margin-right: 10px;
        vertical-align: middle;
      }

      @keyframes spin {
        0% {
          transform: rotate(0deg);
        }

        100% {
          transform: rotate(360deg);
        }
      }

      .notification {
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 20px;
        border-radius: 4px;
        color: white;
        font-weight: bold;
        z-index: 1000;
        opacity: 0;
        transform: translateY(-20px);
        transition: opacity 0.3s, transform 0.3s;
      }

      .notification.success {
        background-color: var(--primary-color);
      }

      .notification.error {
        background-color: var(--danger-color);
      }

      .notification.show {
        opacity: 1;
        transform: translateY(0);
      }

      /* Responsive adjustments */
      @media (max-width: 768px) {
        .shop-container {
          width: 95%;
          height: 90%;
          flex-direction: column;
        }

        .sidebar {
          width: 100%;
          min-width: initial;
          max-height: 120px;
          border-right: none;
          border-bottom: 1px solid var(--border-color);
          display: flex;
          flex-wrap: wrap;
          align-items: center;
          gap: 5px;
          overflow-x: auto;
        }

        .category-item {
          margin-bottom: 0;
          flex: 0 0 auto;
        }

        .item-grid {
          grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
        }
      }
    </style>
  </head>
  <body>
    <div id="shop-app"></div>
    <script>
      // Shop application will be loaded here
    </script>
  </body>
</html>
```

### JavaScript

```javascript
// Main application script
(function() {
  // State management
  const state = {
    categories: [],
    currentCategory: null,
    currentItems: [],
    searchResults: null,
    balance: 0,
    loading: false
  };

  // DOM elements
  let shopApp, notification;

  // Initialize the application
  function init() {
    renderApp();
    setupEventListeners();
  }

  // Create and render the main application
  function renderApp() {
    shopApp = document.getElementById('shop-app');

    const shopHTML = `
            <div class="shop-container">
                <div class="sidebar" id="categories-sidebar">
                    <!-- Categories will be added here -->
                    <div class="loading-spinner"></div> Loading...
                </div>
                <div class="main-content">
                    <div class="shop-header">
                        <div class="shop-title">Server Shop</div>
                        <div class="shop-balance" id="player-balance">Balance: $0.00</div>
                    </div>
                    <div class="search-bar">
                        <input type="text" class="search-input" id="search-input" placeholder="Search items...">
                    </div>
                    <div class="items-container" id="items-container">
                        <div class="empty-message">
                            Select a category to view items
                        </div>
                    </div>
                </div>
            </div>
            <div id="notification" class="notification"></div>
        `;

    shopApp.innerHTML = shopHTML;
    notification = document.getElementById('notification');
  }

  // Set up event listeners
  function setupEventListeners() {
    // Search input
    const searchInput = document.getElementById('search-input');
    searchInput.addEventListener('keypress', function(event) {
      if (event.key === 'Enter') {
        const query = searchInput.value.trim();
        if (query) {
          searchItems(query);
        }
      }
    });

    // Listen for messages from the server
    window.addEventListener('message', function(event) {
      handleServerMessage(event.data);
    });
  }

  // Handle messages from the server
  function handleServerMessage(data) {
    if (!data || typeof data !== 'object') return;

    switch (data.type) {
      case 'shopData':
        updateShopData(data);
        break;
      case 'updateBalance':
        updateBalance(data.balance);
        break;
      default:
        console.log('Unknown message type:', data.type);
        break;
    }
  }

  // Update shop data from server
  function updateShopData(data) {
    state.balance = data.balance || 0;
    state.categories = data.categories || [];

    updateBalance(state.balance);
    renderCategories();
  }

  // Update player balance
  function updateBalance(balance) {
    state.balance = balance;
    const balanceElement = document.getElementById('player-balance');
    balanceElement.textContent = `Balance: $${balance.toFixed(2)}`;
  }

  // Render category sidebar
  function renderCategories() {
    const sidebarElement = document.getElementById('categories-sidebar');

    if (!state.categories || state.categories.length === 0) {
      sidebarElement.innerHTML = '<div class="empty-message">No categories available</div>';
      return;
    }

    let categoriesHTML = '';

    state.categories.forEach(category => {
      const isActive = state.currentCategory && state.currentCategory.id === category.id;
      categoriesHTML += `
                <div class="category-item${isActive ? ' active' : ''}" data-category-id="${category.id}">
                    <div class="category-icon">${category.icon || category.name.charAt(0)}</div>
                    <div class="category-name">${category.name}</div>
                </div>
            `;
    });

    sidebarElement.innerHTML = categoriesHTML;

    // Add click handlers to categories
    document.querySelectorAll('.category-item').forEach(item => {
      item.addEventListener('click', function() {
        const categoryId = this.getAttribute('data-category-id');
        loadCategory(categoryId);
      });
    });
  }

  // Load items for a category
  function loadCategory(categoryId) {
    state.loading = true;
    state.searchResults = null;

    // Find category in state
    const category = state.categories.find(cat => cat.id === categoryId);
    if (!category) return;

    state.currentCategory = category;

    // Update active state in sidebar
    document.querySelectorAll('.category-item').forEach(item => {
      if (item.getAttribute('data-category-id') === categoryId) {
        item.classList.add('active');
      } else {
        item.classList.remove('active');
      }
    });

    // Show loading state
    const itemsContainer = document.getElementById('items-container');
    itemsContainer.innerHTML = '<div class="loading-spinner"></div> Loading items...';

    // Request items from server
    fetchCategoryItems(categoryId);
  }

  // Fetch category items via RPC
  function fetchCategoryItems(categoryId) {
    fetch(`https://${window.resourceName}/getCategory`, {
        method: 'POST',
        body: JSON.stringify({
          categoryId
        })
      })
      .then(response => response.json())
      .then(data => {
        state.currentItems = data.items || [];
        renderItems(state.currentCategory.name, state.currentItems);
        state.loading = false;
      })
      .catch(error => {
        console.error('Error loading category items:', error);
        showNotification('Error loading items', 'error');
        state.loading = false;

        // Show error in items container
        const itemsContainer = document.getElementById('items-container');
        itemsContainer.innerHTML = '<div class="empty-message">Failed to load items</div>';
      });
  }

  // Search for items
  function searchItems(query) {
    if (state.loading) return;

    state.loading = true;

    // Show loading state
    const itemsContainer = document.getElementById('items-container');
    itemsContainer.innerHTML = '<div class="loading-spinner"></div> Searching...';

    // Clear current category selection
    document.querySelectorAll('.category-item').forEach(item => {
      item.classList.remove('active');
    });

    // Send search request
    fetch(`https://${window.resourceName}/searchItems`, {
        method: 'POST',
        body: query
      })
      .then(response => response.json())
      .then(data => {
        state.searchResults = data;
        renderSearchResults(data.query, data.results);
        state.loading = false;
      })
      .catch(error => {
        console.error('Error searching items:', error);
        showNotification('Error searching items', 'error');
        state.loading = false;

        // Show error in items container
        const itemsContainer = document.getElementById('items-container');
        itemsContainer.innerHTML = '<div class="empty-message">Search failed</div>';
      });
  }

  // Render items for a category
  function renderItems(categoryName, items) {
    const itemsContainer = document.getElementById('items-container');

    if (!items || items.length === 0) {
      itemsContainer.innerHTML = '<div class="empty-message">No items available in this category</div>';
      return;
    }

    let itemsHTML = `
            <h2 class="category-title">${categoryName}</h2>
            <div class="item-grid">
        `;

    items.forEach(item => {
      itemsHTML += createItemHTML(item, state.currentCategory.id);
    });

    itemsHTML += '</div>';
    itemsContainer.innerHTML = itemsHTML;

    // Add event listeners for buy/sell buttons
    addItemEventListeners();
  }

  // Render search results
  function renderSearchResults(query, results) {
    const itemsContainer = document.getElementById('items-container');

    if (!results || results.length === 0) {
      itemsContainer.innerHTML = `
                <div class="search-results-header">
                    <h2>Search Results for "${query}"</h2>
                    <span class="back-to-categories" id="back-to-categories">Back to Categories</span>
                </div>
                <div class="empty-message">No items found matching your search</div>
            `;

      // Add back button handler
      document.getElementById('back-to-categories').addEventListener('click', backToCategories);
      return;
    }

    let itemsHTML = `
            <div class="search-results-header">
                <h2>Search Results for "${query}"</h2>
                <span class="back-to-categories" id="back-to-categories">Back to Categories</span>
            </div>
            <div class="item-grid">
        `;

    results.forEach(item => {
      itemsHTML += createItemHTML(item, item.categoryId);
    });

    itemsHTML += '</div>';
    itemsContainer.innerHTML = itemsHTML;

    // Add event listeners for buy/sell buttons
    addItemEventListeners();

    // Add back button handler
    document.getElementById('back-to-categories').addEventListener('click', backToCategories);
  }

  // Create HTML for a shop item
  function createItemHTML(item, categoryId) {
    const canBuy = item.buyPrice !== undefined;
    const canSell = item.sellPrice !== undefined;

    return `
            <div class="shop-item" data-item-id="${item.id}" data-category-id="${categoryId}">
                <div class="item-image" title="${item.material}">${item.name.charAt(0)}</div>
                <div class="item-name">${item.name}</div>
                ${canBuy ? `<div class="item-price">Buy: $${item.buyPrice.toFixed(2)}</div>` : ''}
                ${canSell ? `<div class="item-price">Sell: $${item.sellPrice.toFixed(2)}</div>` : ''}
                <div class="item-quantity">You have: ${item.playerQuantity || 0}</div>
                <div class="item-buttons">
                    ${canBuy ? `
                        <button class="buy-button" data-action="buy" ${state.balance < item.buyPrice ? 'disabled' : ''}>
                            Buy
                        </button>
                    ` : ''}
                    ${canSell ? `
                        <button class="sell-button" data-action="sell" ${(item.playerQuantity || 0) <= 0 ? 'disabled' : ''}>
                            Sell
                        </button>
                    ` : ''}
                </div>
            </div>
        `;
  }

  // Add event listeners to item buttons
  function addItemEventListeners() {
    // Buy buttons
    document.querySelectorAll('.buy-button:not([disabled])').forEach(button => {
      button.addEventListener('click', function() {
        const itemElement = this.closest('.shop-item');
        const itemId = itemElement.getAttribute('data-item-id');
        const categoryId = itemElement.getAttribute('data-category-id');

        // Find the item data
        const items = state.searchResults ? state.searchResults.results : state.currentItems;
        const item = items.find(i => i.id === itemId);

        if (item) {
          showBuyModal(item, categoryId);
        }
      });
    });

    // Sell buttons
    document.querySelectorAll('.sell-button:not([disabled])').forEach(button => {
      button.addEventListener('click', function() {
        const itemElement = this.closest('.shop-item');
        const itemId = itemElement.getAttribute('data-item-id');
        const categoryId = itemElement.getAttribute('data-category-id');

        // Find the item data
        const items = state.searchResults ? state.searchResults.results : state.currentItems;
        const item = items.find(i => i.id === itemId);

        if (item) {
          showSellModal(item, categoryId);
        }
      });
    });
  }

  // Show buy modal
  function showBuyModal(item, categoryId) {
    const maxAfford = Math.floor(state.balance / item.buyPrice);

    const modalHTML = `
            <div class="modal-overlay" id="buy-modal">
                <div class="modal-content">
                    <div class="modal-header">
                        Buy ${item.name}
                    </div>
                    <div class="modal-body">
                        <p>How many would you like to buy?</p>
                        <div class="quantity-selector">
                            <button class="quantity-button" id="decrease-quantity">-</button>
                            <input type="number" class="quantity-input" id="quantity-input" value="1" min="1" max="${maxAfford}">
                            <button class="quantity-button" id="increase-quantity">+</button>
                        </div>
                        <div class="quantity-total">
                            Total: $<span id="total-cost">${item.buyPrice.toFixed(2)}</span>
                        </div>
                        <div class="quantity-total">
                            You can afford: ${maxAfford} items
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="modal-button cancel-button" id="cancel-buy">Cancel</button>
                        <button class="modal-button confirm-button" id="confirm-buy">Buy</button>
                    </div>
                </div>
            </div>
        `;

    document.body.insertAdjacentHTML('beforeend', modalHTML);

    // Set up event listeners
    const quantityInput = document.getElementById('quantity-input');
    const totalCostElement = document.getElementById('total-cost');

    // Update total cost when quantity changes
    function updateTotalCost() {
      const quantity = parseInt(quantityInput.value) || 1;
      const totalCost = quantity * item.buyPrice;
      totalCostElement.textContent = totalCost.toFixed(2);
    }

    // Decrease quantity button
    document.getElementById('decrease-quantity').addEventListener('click', function() {
      let quantity = parseInt(quantityInput.value) || 1;
      quantity = Math.max(1, quantity - 1);
      quantityInput.value = quantity;
      updateTotalCost();
    });

    // Increase quantity button
    document.getElementById('increase-quantity').addEventListener('click', function() {
      let quantity = parseInt(quantityInput.value) || 1;
      quantity = Math.min(maxAfford, quantity + 1);
      quantityInput.value = quantity;
      updateTotalCost();
    });

    // Quantity input change
    quantityInput.addEventListener('change', function() {
      let quantity = parseInt(this.value) || 1;
      quantity = Math.max(1, Math.min(maxAfford, quantity));
      this.value = quantity;
      updateTotalCost();
    });

    // Cancel button
    document.getElementById('cancel-buy').addEventListener('click', function() {
      document.getElementById('buy-modal').remove();
    });

    // Confirm button
    document.getElementById('confirm-buy').addEventListener('click', function() {
      const quantity = parseInt(quantityInput.value) || 1;
      buyItem(categoryId, item.id, quantity);
      document.getElementById('buy-modal').remove();
    });
  }

  // Show sell modal
  function showSellModal(item, categoryId) {
    const maxSell = item.playerQuantity || 0;

    const modalHTML = `
            <div class="modal-overlay" id="sell-modal">
                <div class="modal-content">
                    <div class="modal-header">
                        Sell ${item.name}
                    </div>
                    <div class="modal-body">
                        <p>How many would you like to sell?</p>
                        <div class="quantity-selector">
                            <button class="quantity-button" id="decrease-quantity">-</button>
                            <input type="number" class="quantity-input" id="quantity-input" value="1" min="1" max="${maxSell}">
                            <button class="quantity-button" id="increase-quantity">+</button>
                        </div>
                        <div class="quantity-total">
                            Total: $<span id="total-value">${item.sellPrice.toFixed(2)}</span>
                        </div>
                        <div class="quantity-total">
                            You have: ${maxSell} items
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="modal-button cancel-button" id="cancel-sell">Cancel</button>
                        <button class="modal-button confirm-button" id="confirm-sell">Sell</button>
                    </div>
                </div>
            </div>
        `;

    document.body.insertAdjacentHTML('beforeend', modalHTML);

    // Set up event listeners
    const quantityInput = document.getElementById('quantity-input');
    const totalValueElement = document.getElementById('total-value');

    // Update total value when quantity changes
    function updateTotalValue() {
      const quantity = parseInt(quantityInput.value) || 1;
      const totalValue = quantity * item.sellPrice;
      totalValueElement.textContent = totalValue.toFixed(2);
    }

    // Decrease quantity button
    document.getElementById('decrease-quantity').addEventListener('click', function() {
      let quantity = parseInt(quantityInput.value) || 1;
      quantity = Math.max(1, quantity - 1);
      quantityInput.value = quantity;
      updateTotalValue();
    });

    // Increase quantity button
    document.getElementById('increase-quantity').addEventListener('click', function() {
      let quantity = parseInt(quantityInput.value) || 1;
      quantity = Math.min(maxSell, quantity + 1);
      quantityInput.value = quantity;
      updateTotalValue();
    });

    // Quantity input change
    quantityInput.addEventListener('change', function() {
      let quantity = parseInt(this.value) || 1;
      quantity = Math.max(1, Math.min(maxSell, quantity));
      this.value = quantity;
      updateTotalValue();
    });

    // Cancel button
    document.getElementById('cancel-sell').addEventListener('click', function() {
      document.getElementById('sell-modal').remove();
    });

    // Confirm button
    document.getElementById('confirm-sell').addEventListener('click', function() {
      const quantity = parseInt(quantityInput.value) || 1;
      sellItem(categoryId, item.id, quantity);
      document.getElementById('sell-modal').remove();
    });
  }

  // Buy an item
  function buyItem(categoryId, itemId, quantity) {
    state.loading = true;

    // Show loading notification
    showNotification('Processing purchase...', 'success', true);

    fetch(`https://${window.resourceName}/buyItem`, {
        method: 'POST',
        body: JSON.stringify({
          categoryId,
          itemId,
          quantity
        })
      })
      .then(response => response.json())
      .then(data => {
        state.loading = false;

        if (data.success) {
          // Update balance
          updateBalance(data.newBalance);

          // Show success notification
          showNotification(data.message, 'success');

          // Refresh current view
          if (state.searchResults) {
            searchItems(state.searchResults.query);
          } else if (state.currentCategory) {
            loadCategory(state.currentCategory.id);
          }
        } else {
          // Show error notification
          showNotification(data.error || 'Failed to purchase item', 'error');
        }
      })
      .catch(error => {
        console.error('Error buying item:', error);
        showNotification('Error processing purchase', 'error');
        state.loading = false;
      });
  }

  // Sell an item
  function sellItem(categoryId, itemId, quantity) {
    state.loading = true;

    // Show loading notification
    showNotification('Processing sale...', 'success', true);

    fetch(`https://${window.resourceName}/sellItem`, {
        method: 'POST',
        body: JSON.stringify({
          categoryId,
          itemId,
          quantity
        })
      })
      .then(response => response.json())
      .then(data => {
        state.loading = false;

        if (data.success) {
          // Update balance
          updateBalance(data.newBalance);

          // Show success notification
          showNotification(data.message, 'success');

          // Refresh current view
          if (state.searchResults) {
            searchItems(state.searchResults.query);
          } else if (state.currentCategory) {
            loadCategory(state.currentCategory.id);
          }
        } else {
          // Show error notification
          showNotification(data.error || 'Failed to sell item', 'error');
        }
      })
      .catch(error => {
        console.error('Error selling item:', error);
        showNotification('Error processing sale', 'error');
        state.loading = false;
      });
  }

  // Show notification
  function showNotification(message, type, loading = false) {
    const notification = document.getElementById('notification');
    notification.className = 'notification ' + type;
    notification.textContent = message;

    if (loading) {
      notification.innerHTML = '<span class="loading-spinner"></span> ' + message;
    } else {
      notification.textContent = message;
    }

    notification.classList.add('show');

    if (!loading) {
      setTimeout(() => {
        notification.classList.remove('show');
      }, 3000);
    }
  }

  // Go back to categories view
  function backToCategories() {
    state.searchResults = null;
    const searchInput = document.getElementById('search-input');
    searchInput.value = '';

    if (state.currentCategory) {
      loadCategory(state.currentCategory.id);
    } else {
      const itemsContainer = document.getElementById('items-container');
      itemsContainer.innerHTML = '<div class="empty-message">Select a category to view items</div>';
    }
  }

  // Initialize the shop when the DOM is loaded
  document.addEventListener('DOMContentLoaded', init);
})();
```

## Key Features Demonstrated

1. **Server-Side**
   - Event-driven UI lifecycle management
   - RPC controller with multiple endpoints
   - UI creation from data URLs
   - Dynamic state management with player-specific data
   - Resource loading from plugin

2. **Client-Side**
   - Responsive design with mobile considerations
   - Rich UI with modals, notifications, and transitions
   - State management without frameworks
   - Real-time balance updates
   - Search functionality

## Conclusion

This comprehensive example demonstrates how to build a full-featured shop UI using the Feather UIs. The techniques shown here can be adapted for many different types of UI applications like inventories, leaderboards, settings panels, mini-game interfaces, and more.

By using web technologies with the Feather UIs, you can create rich, interactive experiences that would be impossible with vanilla Minecraft interfaces.

## Resources

The complete implementation of this shop system can be found in our [GitHub repository](https://github.com/FeatherMC/feather-server-api/tree/main/examples). For those who prefer a ready-to-use solution, you can download the pre-built JAR file from our [releases page](https://github.com/FeatherMC/feather-server-api/releases).