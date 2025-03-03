package net.digitalingot.feather.serverapi.examples.shop;

/** Represents an item in the shop */
public class ShopItem {
  private final String id;
  private String name;
  private String material;
  private double buyPrice;
  private double sellPrice;
  private String description;
  private int maxPurchase;

  /**
   * Creates a new shop item
   *
   * @param id The item ID
   * @param name The item name
   * @param material The item material
   * @param buyPrice The buy price
   * @param sellPrice The sell price
   */
  public ShopItem(String id, String name, String material, double buyPrice, double sellPrice) {
    this.id = id;
    this.name = name;
    this.material = material;
    this.buyPrice = buyPrice;
    this.sellPrice = sellPrice;
    this.description = "";
    this.maxPurchase = 0; // 0 means no limit
  }

  /**
   * Gets the item ID
   *
   * @return The item ID
   */
  public String getId() {
    return this.id;
  }

  /**
   * Gets the item name
   *
   * @return The item name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the item name
   *
   * @param name The new item name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the item material
   *
   * @return The item material
   */
  public String getMaterial() {
    return this.material;
  }

  /**
   * Sets the item material
   *
   * @param material The new item material
   */
  public void setMaterial(String material) {
    this.material = material;
  }

  /**
   * Gets the buy price
   *
   * @return The buy price
   */
  public double getBuyPrice() {
    return this.buyPrice;
  }

  /**
   * Sets the buy price
   *
   * @param buyPrice The new buy price
   */
  public void setBuyPrice(double buyPrice) {
    this.buyPrice = buyPrice;
  }

  /**
   * Gets the sell price
   *
   * @return The sell price
   */
  public double getSellPrice() {
    return this.sellPrice;
  }

  /**
   * Sets the sell price
   *
   * @param sellPrice The new sell price
   */
  public void setSellPrice(double sellPrice) {
    this.sellPrice = sellPrice;
  }

  /**
   * Gets the item description
   *
   * @return The item description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Sets the item description
   *
   * @param description The new item description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the maximum purchase amount per transaction
   *
   * @return The maximum purchase amount
   */
  public int getMaxPurchase() {
    return this.maxPurchase;
  }

  /**
   * Sets the maximum purchase amount per transaction
   *
   * @param maxPurchase The new maximum purchase amount
   */
  public void setMaxPurchase(int maxPurchase) {
    this.maxPurchase = maxPurchase;
  }

  /**
   * Checks if the item can be bought
   *
   * @return True if the item can be bought, false otherwise
   */
  public boolean canBuy() {
    return this.buyPrice >= 0;
  }

  /**
   * Checks if the item can be sold
   *
   * @return True if the item can be sold, false otherwise
   */
  public boolean canSell() {
    return this.sellPrice >= 0;
  }
}
