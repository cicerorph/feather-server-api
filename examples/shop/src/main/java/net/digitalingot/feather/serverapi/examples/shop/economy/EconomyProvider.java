package net.digitalingot.feather.serverapi.examples.shop.economy;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;

/** Interface for accessing currency/economy functionality */
public interface EconomyProvider {
  /**
   * Checks if the economy service is available
   *
   * @return true if the economy service is available, false otherwise
   */
  boolean isInitialized();

  /**
   * Gets the balance of a player
   *
   * @param player The player whose balance to check
   * @return The player's balance
   */
  double getBalance(FeatherPlayer player);

  /**
   * Withdraws an amount from a player's balance
   *
   * @param player The player to withdraw from
   * @param amount The amount to withdraw
   * @return true if successful, false otherwise
   */
  boolean withdraw(FeatherPlayer player, double amount);

  /**
   * Deposits an amount to a player's balance
   *
   * @param player The player to deposit to
   * @param amount The amount to deposit
   * @return true if successful, false otherwise
   */
  boolean deposit(FeatherPlayer player, double amount);

  /**
   * Checks if a player has at least the specified amount
   *
   * @param player The player to check
   * @param amount The amount to check for
   * @return true if the player has sufficient funds, false otherwise
   */
  default boolean has(FeatherPlayer player, double amount) {
    return getBalance(player) >= amount;
  }

  /**
   * Gets the currency name in singular form
   *
   * @return The name of the currency (e.g., "dollar")
   */
  default String getCurrencyName() {
    return "dollar";
  }

  /**
   * Gets the currency name in plural form
   *
   * @return The name of the currency in plural (e.g., "dollars")
   */
  default String getCurrencyNamePlural() {
    return "dollars";
  }

  /**
   * Gets the currency symbol
   *
   * @return The currency symbol (e.g., "$")
   */
  default String getCurrencySymbol() {
    return "$";
  }
}
