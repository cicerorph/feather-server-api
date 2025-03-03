package net.digitalingot.feather.serverapi.examples.shop.economy;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * An implementation of EconomyProvider that integrates with Vault Requires the Vault plugin and a
 * compatible economy plugin
 */
public class VaultEconomyProvider implements EconomyProvider {

  private final Economy economy;
  private final boolean initialized;

  /**
   * Creates a new Vault economy provider
   *
   * @param plugin The plugin instance
   */
  public VaultEconomyProvider(Plugin plugin) {
    RegisteredServiceProvider<Economy> rsp =
        plugin.getServer().getServicesManager().getRegistration(Economy.class);

    if (rsp == null) {
      plugin
          .getLogger()
          .severe("No economy plugin found! Please install a Vault-compatible economy plugin.");
      this.economy = null;
      this.initialized = false;
    } else {
      this.economy = rsp.getProvider();
      this.initialized = true;
      plugin.getLogger().info("Successfully hooked into economy: " + economy.getName());
    }
  }

  /**
   * Checks if the economy service is available
   *
   * @return true if the economy service is available, false otherwise
   */
  public boolean isInitialized() {
    return this.initialized && this.economy != null;
  }

  @Override
  public double getBalance(FeatherPlayer player) {
    checkInitialized();
    return this.economy.getBalance(Bukkit.getPlayer(player.getUniqueId()));
  }

  @Override
  public boolean withdraw(FeatherPlayer player, double amount) {
    checkInitialized();
    return this.economy
        .withdrawPlayer(Bukkit.getPlayer(player.getUniqueId()), amount)
        .transactionSuccess();
  }

  @Override
  public boolean deposit(FeatherPlayer player, double amount) {
    checkInitialized();
    return this.economy
        .depositPlayer(Bukkit.getPlayer(player.getUniqueId()), amount)
        .transactionSuccess();
  }

  @Override
  public boolean has(FeatherPlayer player, double amount) {
    checkInitialized();
    return this.economy.has(Bukkit.getPlayer(player.getUniqueId()), amount);
  }

  @Override
  public String getCurrencyName() {
    checkInitialized();
    return this.economy.currencyNameSingular();
  }

  @Override
  public String getCurrencyNamePlural() {
    checkInitialized();
    return this.economy.currencyNamePlural();
  }

  @Override
  public String getCurrencySymbol() {
    return "$"; // Vault doesn't provide a currency symbol, so we use $ as default
  }

  private void checkInitialized() {
    if (!this.initialized || this.economy == null) {
      throw new IllegalStateException("Economy service is not available");
    }
  }
}
