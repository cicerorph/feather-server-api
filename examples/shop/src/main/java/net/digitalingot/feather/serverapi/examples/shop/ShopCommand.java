package net.digitalingot.feather.serverapi.examples.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.digitalingot.feather.serverapi.api.FeatherAPI;
import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

/** Handles the /shop command with various subcommands. */
public class ShopCommand implements CommandExecutor, TabCompleter {

  private final ShopPlugin plugin;

  /**
   * Creates a new shop command handler.
   *
   * @param plugin The shop plugin instance
   */
  public ShopCommand(ShopPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    // Check if command is from a player
    if (!(sender instanceof Player)) {
      sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
      return true;
    }

    Player player = (Player) sender;

    // Check if player has permission to use the shop
    if (!player.hasPermission("shop.use")) {
      player.sendMessage(ChatColor.RED + "You don't have permission to use the shop.");
      return true;
    }

    // Handle subcommands
    if (args.length > 0) {
      String subCommand = args[0].toLowerCase();

      switch (subCommand) {
        case "reload":
          return handleReloadCommand(player, args);
        case "add":
          return handleAddCommand(player, args);
        case "remove":
          return handleRemoveCommand(player, args);
        case "edit":
          return handleEditCommand(player, args);
        case "help":
          return showHelp(player);
        default:
          player.sendMessage(
              ChatColor.RED + "Unknown subcommand. Use /shop help for available commands.");
          return true;
      }
    }

    // Default action - open shop UI
    openShopForPlayer(player);
    return true;
  }

  /**
   * Opens the shop UI for a player.
   *
   * @param player The player to open the shop for
   */
  private void openShopForPlayer(Player player) {
    // Get Feather player
    FeatherPlayer featherPlayer = FeatherAPI.getPlayerService().getPlayer(player.getUniqueId());

    if (featherPlayer == null) {
      player.sendMessage(ChatColor.RED + "You need Feather Client to use the shop.");
      return;
    }

    // Open the shop UI
    this.plugin.openShopForPlayer(featherPlayer);
  }

  /**
   * Handles the reload subcommand.
   *
   * @param player The player executing the command
   * @param args Command arguments
   * @return Whether the command was handled successfully
   */
  private boolean handleReloadCommand(Player player, String[] args) {
    // Check permission
    if (!player.hasPermission("shop.admin")) {
      player.sendMessage(ChatColor.RED + "You don't have permission to reload the shop.");
      return true;
    }

    // Reload the config
    this.plugin.reloadConfig();

    // Reload shop data
    this.plugin.reloadShopData();

    player.sendMessage(ChatColor.GREEN + "Shop configuration reloaded.");
    return true;
  }

  /**
   * Handles the add subcommand.
   *
   * @param player The player executing the command
   * @param args Command arguments
   * @return Whether the command was handled successfully
   */
  private boolean handleAddCommand(Player player, String[] args) {
    // Check permission
    if (!player.hasPermission("shop.admin")) {
      player.sendMessage(ChatColor.RED + "You don't have permission to add shop items.");
      return true;
    }

    // Syntax: /shop add <category> <id> <name> <material> <buy-price> <sell-price>
    if (args.length < 7) {
      player.sendMessage(
          ChatColor.RED
              + "Usage: /shop add <category> <id> <name> <material> <buy-price> <sell-price>");
      return true;
    }

    String categoryId = args[1];
    String itemId = args[2];
    String name = args[3].replace('_', ' ');
    String material = args[4].toUpperCase();

    double buyPrice;
    double sellPrice;

    try {
      buyPrice = Double.parseDouble(args[5]);
      sellPrice = Double.parseDouble(args[6]);
    } catch (NumberFormatException e) {
      player.sendMessage(ChatColor.RED + "Buy and sell prices must be valid numbers.");
      return true;
    }

    // Validate material
    try {
      Material.valueOf(material);
    } catch (IllegalArgumentException e) {
      player.sendMessage(ChatColor.RED + "Invalid material name: " + material);
      return true;
    }

    // Check if category exists
    if (!this.plugin.getShopManager().hasCategory(categoryId)) {
      player.sendMessage(ChatColor.RED + "Category does not exist: " + categoryId);
      return true;
    }

    // Check if item already exists
    if (this.plugin.getShopManager().hasItem(categoryId, itemId)) {
      player.sendMessage(
          ChatColor.RED
              + "Item with ID '"
              + itemId
              + "' already exists in category '"
              + categoryId
              + "'.");
      return true;
    }

    // Add the item
    boolean success =
        this.plugin
            .getShopManager()
            .addItem(categoryId, itemId, name, material, buyPrice, sellPrice);

    if (success) {
      player.sendMessage(
          ChatColor.GREEN + "Added item '" + name + "' to category '" + categoryId + "'.");
      // Save to config
      this.plugin.saveShopData();
    } else {
      player.sendMessage(ChatColor.RED + "Failed to add item to shop.");
    }

    return true;
  }

  /**
   * Handles the remove subcommand.
   *
   * @param player The player executing the command
   * @param args Command arguments
   * @return Whether the command was handled successfully
   */
  private boolean handleRemoveCommand(Player player, String[] args) {
    // Check permission
    if (!player.hasPermission("shop.admin")) {
      player.sendMessage(ChatColor.RED + "You don't have permission to remove shop items.");
      return true;
    }

    // Syntax: /shop remove <category> <id>
    if (args.length < 3) {
      player.sendMessage(ChatColor.RED + "Usage: /shop remove <category> <id>");
      return true;
    }

    String categoryId = args[1];
    String itemId = args[2];

    // Check if category exists
    if (!this.plugin.getShopManager().hasCategory(categoryId)) {
      player.sendMessage(ChatColor.RED + "Category does not exist: " + categoryId);
      return true;
    }

    // Check if item exists
    if (!this.plugin.getShopManager().hasItem(categoryId, itemId)) {
      player.sendMessage(
          ChatColor.RED
              + "Item with ID '"
              + itemId
              + "' does not exist in category '"
              + categoryId
              + "'.");
      return true;
    }

    // Remove the item
    boolean success = this.plugin.getShopManager().removeItem(categoryId, itemId);

    if (success) {
      player.sendMessage(
          ChatColor.GREEN + "Removed item '" + itemId + "' from category '" + categoryId + "'.");
      // Save to config
      this.plugin.saveShopData();
    } else {
      player.sendMessage(ChatColor.RED + "Failed to remove item from shop.");
    }

    return true;
  }

  /**
   * Handles the edit subcommand.
   *
   * @param player The player executing the command
   * @param args Command arguments
   * @return Whether the command was handled successfully
   */
  private boolean handleEditCommand(Player player, String[] args) {
    // Check permission
    if (!player.hasPermission("shop.admin")) {
      player.sendMessage(ChatColor.RED + "You don't have permission to edit shop items.");
      return true;
    }

    // Syntax: /shop edit <category> <id> <property> <value>
    if (args.length < 5) {
      player.sendMessage(ChatColor.RED + "Usage: /shop edit <category> <id> <property> <value>");
      player.sendMessage(ChatColor.YELLOW + "Properties: name, material, buy-price, sell-price");
      return true;
    }

    String categoryId = args[1];
    String itemId = args[2];
    String property = args[3].toLowerCase();
    String value = args[4];

    // Check if category exists
    if (!this.plugin.getShopManager().hasCategory(categoryId)) {
      player.sendMessage(ChatColor.RED + "Category does not exist: " + categoryId);
      return true;
    }

    // Check if item exists
    if (!this.plugin.getShopManager().hasItem(categoryId, itemId)) {
      player.sendMessage(
          ChatColor.RED
              + "Item with ID '"
              + itemId
              + "' does not exist in category '"
              + categoryId
              + "'.");
      return true;
    }

    // Edit the item property
    boolean success = false;

    switch (property) {
      case "name":
        success =
            this.plugin
                .getShopManager()
                .updateItemName(categoryId, itemId, value.replace('_', ' '));
        break;

      case "material":
        // Validate material
        try {
          Material.valueOf(value.toUpperCase());
          success =
              this.plugin
                  .getShopManager()
                  .updateItemMaterial(categoryId, itemId, value.toUpperCase());
        } catch (IllegalArgumentException e) {
          player.sendMessage(ChatColor.RED + "Invalid material name: " + value);
          return true;
        }
        break;

      case "buy-price":
        try {
          double price = Double.parseDouble(value);
          success = this.plugin.getShopManager().updateItemBuyPrice(categoryId, itemId, price);
        } catch (NumberFormatException e) {
          player.sendMessage(ChatColor.RED + "Buy price must be a valid number.");
          return true;
        }
        break;

      case "sell-price":
        try {
          double price = Double.parseDouble(value);
          success = this.plugin.getShopManager().updateItemSellPrice(categoryId, itemId, price);
        } catch (NumberFormatException e) {
          player.sendMessage(ChatColor.RED + "Sell price must be a valid number.");
          return true;
        }
        break;

      default:
        player.sendMessage(ChatColor.RED + "Unknown property: " + property);
        player.sendMessage(ChatColor.YELLOW + "Properties: name, material, buy-price, sell-price");
        return true;
    }

    if (success) {
      player.sendMessage(
          ChatColor.GREEN
              + "Updated "
              + property
              + " for item '"
              + itemId
              + "' in category '"
              + categoryId
              + "'.");
      // Save to config
      this.plugin.saveShopData();
    } else {
      player.sendMessage(ChatColor.RED + "Failed to update item property.");
    }

    return true;
  }

  /**
   * Shows the help message to a player.
   *
   * @param player The player to show help to
   * @return Always returns true
   */
  private boolean showHelp(Player player) {
    player.sendMessage(ChatColor.GREEN + "==== Shop Commands ====");
    player.sendMessage(ChatColor.YELLOW + "/shop " + ChatColor.WHITE + "- Open the shop UI");

    if (player.hasPermission("shop.admin")) {
      player.sendMessage(
          ChatColor.YELLOW + "/shop reload " + ChatColor.WHITE + "- Reload the shop configuration");
      player.sendMessage(
          ChatColor.YELLOW
              + "/shop add <category> <id> <name> <material> <buy-price> <sell-price> "
              + ChatColor.WHITE
              + "- Add a new item");
      player.sendMessage(
          ChatColor.YELLOW
              + "/shop remove <category> <id> "
              + ChatColor.WHITE
              + "- Remove an item");
      player.sendMessage(
          ChatColor.YELLOW
              + "/shop edit <category> <id> <property> <value> "
              + ChatColor.WHITE
              + "- Edit an item property");
    }

    player.sendMessage(
        ChatColor.YELLOW + "/shop help " + ChatColor.WHITE + "- Show this help message");
    return true;
  }

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command command, String alias, String[] args) {
    List<String> completions = new ArrayList<>();

    if (!(sender instanceof Player)) {
      return completions;
    }

    Player player = (Player) sender;

    // If not enough permission, provide no tab completions
    if (!player.hasPermission("shop.use")) {
      return completions;
    }

    if (args.length == 1) {
      // First argument - subcommands
      List<String> subCommands = new ArrayList<>();
      subCommands.add("help");

      // Admin commands
      if (player.hasPermission("shop.admin")) {
        subCommands.add("reload");
        subCommands.add("add");
        subCommands.add("remove");
        subCommands.add("edit");
      }

      return filterCompletions(subCommands, args[0]);
    } else if (args.length >= 2) {
      // Admin commands that need tab completion
      if (player.hasPermission("shop.admin")) {
        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
          case "add":
          case "remove":
          case "edit":
            if (args.length == 2) {
              // Complete category IDs
              return filterCompletions(this.plugin.getShopManager().getCategoryIds(), args[1]);
            } else if (args.length == 3) {
              // Complete item IDs for the selected category
              if (this.plugin.getShopManager().hasCategory(args[1])) {
                return filterCompletions(this.plugin.getShopManager().getItemIds(args[1]), args[2]);
              }
            } else if (args.length == 4 && subCommand.equals("edit")) {
              // Complete property names for edit command
              List<String> properties =
                  Arrays.asList("name", "material", "buy-price", "sell-price");
              return filterCompletions(properties, args[3]);
            } else if (args.length == 5 && subCommand.equals("add")) {
              // Complete material names for add command
              return filterCompletions(
                  Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList()),
                  args[4]);
            } else if (args.length == 5
                && subCommand.equals("edit")
                && args[3].equalsIgnoreCase("material")) {
              // Complete material names for edit command when changing material
              return filterCompletions(
                  Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList()),
                  args[4]);
            }
            break;
        }
      }
    }

    return completions;
  }

  /**
   * Filters completions based on the current input prefix
   *
   * @param options List of possible completions
   * @param current Current input to match against
   * @return List of filtered completions
   */
  private List<String> filterCompletions(List<String> options, String current) {
    String lowercaseInput = current.toLowerCase();
    return options.stream()
        .filter(option -> option.toLowerCase().startsWith(lowercaseInput))
        .collect(Collectors.toList());
  }
}
