package net.digitalingot.feather.serverapi.examples.shop.economy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;

/** A simple mock implementation of EconomyProvider for testing Stores player balances in memory */
public class MockEconomyProvider implements EconomyProvider {

  private final Map<UUID, Double> balances = new HashMap<>();
  private final double defaultBalance;

  /** Creates a new mock economy provider with a default starting balance of 0.0 */
  public MockEconomyProvider() {
    this(0.0);
  }

  /**
   * Creates a new mock economy provider with the specified default starting balance
   *
   * @param defaultBalance The default balance for players
   */
  public MockEconomyProvider(double defaultBalance) {
    this.defaultBalance = defaultBalance;
  }

  @Override
  public boolean isInitialized() {
    return true;
  }

  @Override
  public double getBalance(FeatherPlayer player) {
    return this.balances.computeIfAbsent(player.getUniqueId(), id -> this.defaultBalance);
  }

  @Override
  public boolean withdraw(FeatherPlayer player, double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Cannot withdraw negative amount");
    }

    UUID playerId = player.getUniqueId();
    double currentBalance = getBalance(player);

    if (currentBalance < amount) {
      return false;
    }

    this.balances.put(playerId, currentBalance - amount);
    return true;
  }

  @Override
  public boolean deposit(FeatherPlayer player, double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Cannot deposit negative amount");
    }

    UUID playerId = player.getUniqueId();
    double currentBalance = getBalance(player);

    this.balances.put(playerId, currentBalance + amount);
    return true;
  }

  /**
   * Sets a player's balance to a specific amount
   *
   * @param player The player whose balance to set
   * @param amount The amount to set the balance to
   */
  public void setBalance(FeatherPlayer player, double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Cannot set negative balance");
    }

    this.balances.put(player.getUniqueId(), amount);
  }

  /** Resets all balances to default */
  public void resetAllBalances() {
    this.balances.clear();
  }

  /**
   * Gets a copy of all current balances for debugging/testing
   *
   * @return An unmodifiable map of player UUIDs to balances
   */
  public Map<UUID, Double> getAllBalances() {
    return Collections.unmodifiableMap(this.balances);
  }

  @Override
  public String getCurrencyName() {
    return "coin";
  }

  @Override
  public String getCurrencyNamePlural() {
    return "coins";
  }

  @Override
  public String getCurrencySymbol() {
    return "⛃";
  }
}
