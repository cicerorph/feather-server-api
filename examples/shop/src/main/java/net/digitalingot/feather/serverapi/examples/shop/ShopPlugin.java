package net.digitalingot.feather.serverapi.examples.shop;

import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import net.digitalingot.feather.serverapi.api.FeatherAPI;
import net.digitalingot.feather.serverapi.api.event.player.PlayerHelloEvent;
import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.digitalingot.feather.serverapi.api.ui.UIPage;
import net.digitalingot.feather.serverapi.examples.shop.economy.EconomyProvider;
import net.digitalingot.feather.serverapi.examples.shop.economy.EconomyProviderFactory;
import net.digitalingot.feather.serverapi.examples.shop.ui.ShopController;
import net.digitalingot.feather.serverapi.examples.shop.ui.ShopFocusHandler;
import net.digitalingot.feather.serverapi.examples.shop.ui.ShopLifecycleHandler;
import net.digitalingot.feather.serverapi.examples.shop.ui.ShopVisibilityHandler;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopPlugin extends JavaPlugin {
  private UIPage shopPage;
  private ShopManager shopManager;
  private EconomyProvider economyProvider;

  @Override
  public void onEnable() {
    // Save default config if it doesn't exist
    saveDefaultConfig();

    // Initialize managers and providers
    this.shopManager = new ShopManager();
    this.economyProvider =
        EconomyProviderFactory.createProvider(
            this, getConfig().getDouble("settings.default-balance", 1000.0));

    // Load shop items from config
    loadShopItems();

    // Create the UI page
    createShopUI();

    // Register event listeners
    FeatherAPI.getEventService().subscribe(PlayerHelloEvent.class, this::onPlayerHello, this);

    // Register commands
    getCommand("shop").setExecutor(new ShopCommand(this));
    getCommand("shop").setTabCompleter(new ShopCommand(this));

    getLogger().info("Shop Plugin has been enabled!");
  }

  @Override
  public void onDisable() {
    getLogger().info("Shop Plugin has been disabled!");
  }

  private void loadShopItems() {
    // Clear any existing items
    this.shopManager.clear();

    // Load shop categories and items from config
    ConfigurationSection categoriesSection = getConfig().getConfigurationSection("categories");
    if (categoriesSection == null) {
      getLogger().warning("No categories found in config!");
      return;
    }

    for (String categoryKey : categoriesSection.getKeys(false)) {
      String categoryName = categoriesSection.getString(categoryKey + ".name", categoryKey);
      String categoryIcon =
          categoriesSection.getString(categoryKey + ".icon", categoryName.substring(0, 1));

      ShopCategory category = new ShopCategory(categoryKey, categoryName, categoryIcon);

      ConfigurationSection itemsSection =
          categoriesSection.getConfigurationSection(categoryKey + ".items");
      if (itemsSection != null) {
        for (String itemKey : itemsSection.getKeys(false)) {
          String itemName = itemsSection.getString(itemKey + ".name", itemKey);
          String itemMaterial = itemsSection.getString(itemKey + ".material", "STONE");
          double buyPrice = itemsSection.getDouble(itemKey + ".buy-price", -1);
          double sellPrice = itemsSection.getDouble(itemKey + ".sell-price", -1);
          String description = itemsSection.getString(itemKey + ".description", "");

          ShopItem item = new ShopItem(itemKey, itemName, itemMaterial, buyPrice, sellPrice);
          item.setDescription(description);

          // Check for purchase limits
          if (itemsSection.contains(itemKey + ".max-purchase")) {
            item.setMaxPurchase(itemsSection.getInt(itemKey + ".max-purchase"));
          }

          category.addItem(item);
        }
      }

      this.shopManager.addCategory(category);
    }

    getLogger()
        .info(
            "Loaded "
                + this.shopManager.getCategoryCount()
                + " categories with a total of "
                + this.shopManager.getTotalItemCount()
                + " items!");
  }

  private void createShopUI() {
    // Load HTML content from resources
    String htmlContent;
    try {
      htmlContent = loadResource("shop.min.html");
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
    try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
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

  public void sendShopData(FeatherPlayer player) {
    // Create shop data message
    ShopDataMessage message = new ShopDataMessage();
    message.setBalance(this.economyProvider.getBalance(player));

    // Add all categories
    for (ShopCategory category : this.shopManager.getCategories()) {
      message.addCategory(category.getId(), category.getName(), category.getIcon());
    }

    // Send the message
    String jsonMessage = message.toJson();
    FeatherAPI.getUIService().sendPageMessage(player, this.shopPage, jsonMessage);
  }

  private void onPlayerHello(PlayerHelloEvent event) {
    if (getConfig().getBoolean("settings.open-on-join", false)) {
      openShopForPlayer(event.getPlayer());
    }
    getLogger().info("Feather player connected: " + event.getPlayer().getName());
  }

  /**
   * Gets the shop manager
   *
   * @return The shop manager
   */
  public ShopManager getShopManager() {
    return this.shopManager;
  }

  /**
   * Gets the economy provider
   *
   * @return The economy provider
   */
  public EconomyProvider getEconomyProvider() {
    return this.economyProvider;
  }

  /**
   * Gets the shop page
   *
   * @return The shop page
   */
  public UIPage getShopPage() {
    return this.shopPage;
  }

  /** Reloads the shop data from the config */
  public void reloadShopData() {
    reloadConfig();
    this.shopManager.clear();
    loadShopItems();
  }

  /** Saves the shop data to the config */
  public void saveShopData() {
    // For each category in the shop manager
    for (ShopCategory category : this.shopManager.getCategories()) {
      String categoryPath = "categories." + category.getId();

      // Save category name and icon
      getConfig().set(categoryPath + ".name", category.getName());
      getConfig().set(categoryPath + ".icon", category.getIcon());

      // For each item in the category
      for (ShopItem item : category.getItems()) {
        String itemPath = categoryPath + ".items." + item.getId();

        // Save item properties
        getConfig().set(itemPath + ".name", item.getName());
        getConfig().set(itemPath + ".material", item.getMaterial());
        getConfig().set(itemPath + ".buy-price", item.getBuyPrice());
        getConfig().set(itemPath + ".sell-price", item.getSellPrice());
        getConfig().set(itemPath + ".description", item.getDescription());

        if (item.getMaxPurchase() > 0) {
          getConfig().set(itemPath + ".max-purchase", item.getMaxPurchase());
        }
      }
    }

    // Save the config
    saveConfig();
  }
}
