package net.digitalingot.feather.serverapi.examples.shop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopManager {
  private final Map<String, ShopCategory> categories = new HashMap<>();

  /**
   * Adds a category to the shop
   *
   * @param category The category to add
   */
  public void addCategory(ShopCategory category) {
    this.categories.put(category.getId(), category);
  }

  /**
   * Checks if a category exists
   *
   * @param categoryId The ID of the category to check
   * @return True if the category exists, false otherwise
   */
  public boolean hasCategory(String categoryId) {
    return this.categories.containsKey(categoryId);
  }

  /**
   * Gets a category by ID
   *
   * @param categoryId The ID of the category to get
   * @return The category, or null if not found
   */
  public ShopCategory getCategory(String categoryId) {
    return this.categories.get(categoryId);
  }

  /**
   * Gets all categories
   *
   * @return A collection of all categories
   */
  public Collection<ShopCategory> getCategories() {
    return this.categories.values();
  }

  /**
   * Gets the number of categories
   *
   * @return The number of categories
   */
  public int getCategoryCount() {
    return this.categories.size();
  }

  /**
   * Gets the total number of items across all categories
   *
   * @return The total number of items
   */
  public int getTotalItemCount() {
    int count = 0;
    for (ShopCategory category : this.categories.values()) {
      count += category.getItemCount();
    }
    return count;
  }

  /**
   * Gets all category IDs
   *
   * @return A list of all category IDs
   */
  public List<String> getCategoryIds() {
    return new ArrayList<>(this.categories.keySet());
  }

  /**
   * Checks if an item exists in a category
   *
   * @param categoryId The ID of the category
   * @param itemId The ID of the item
   * @return True if the item exists, false otherwise
   */
  public boolean hasItem(String categoryId, String itemId) {
    ShopCategory category = this.categories.get(categoryId);
    return category != null && category.hasItem(itemId);
  }

  /**
   * Gets an item by ID
   *
   * @param categoryId The ID of the category
   * @param itemId The ID of the item
   * @return The item, or null if not found
   */
  public ShopItem getItem(String categoryId, String itemId) {
    ShopCategory category = this.categories.get(categoryId);
    return category != null ? category.getItem(itemId) : null;
  }

  /**
   * Gets all item IDs in a category
   *
   * @param categoryId The ID of the category
   * @return A list of item IDs, or an empty list if the category doesn't exist
   */
  public List<String> getItemIds(String categoryId) {
    ShopCategory category = this.categories.get(categoryId);
    return category != null ? category.getItemIds() : new ArrayList<>();
  }

  /**
   * Adds an item to a category
   *
   * @param categoryId The ID of the category
   * @param itemId The ID of the item
   * @param name The name of the item
   * @param material The material of the item
   * @param buyPrice The buy price of the item
   * @param sellPrice The sell price of the item
   * @return True if the item was added, false otherwise
   */
  public boolean addItem(
      String categoryId,
      String itemId,
      String name,
      String material,
      double buyPrice,
      double sellPrice) {
    ShopCategory category = this.categories.get(categoryId);
    if (category == null) return false;

    ShopItem item = new ShopItem(itemId, name, material, buyPrice, sellPrice);
    category.addItem(item);
    return true;
  }

  /**
   * Removes an item from a category
   *
   * @param categoryId The ID of the category
   * @param itemId The ID of the item
   * @return True if the item was removed, false otherwise
   */
  public boolean removeItem(String categoryId, String itemId) {
    ShopCategory category = this.categories.get(categoryId);
    return category != null && category.removeItem(itemId);
  }

  /**
   * Updates an item's name
   *
   * @param categoryId The ID of the category
   * @param itemId The ID of the item
   * @param newName The new name for the item
   * @return True if the name was updated, false otherwise
   */
  public boolean updateItemName(String categoryId, String itemId, String newName) {
    ShopItem item = getItem(categoryId, itemId);
    if (item == null) return false;

    item.setName(newName);
    return true;
  }

  /**
   * Updates an item's material
   *
   * @param categoryId The ID of the category
   * @param itemId The ID of the item
   * @param newMaterial The new material for the item
   * @return True if the material was updated, false otherwise
   */
  public boolean updateItemMaterial(String categoryId, String itemId, String newMaterial) {
    ShopItem item = getItem(categoryId, itemId);
    if (item == null) return false;

    item.setMaterial(newMaterial);
    return true;
  }

  /**
   * Updates an item's buy price
   *
   * @param categoryId The ID of the category
   * @param itemId The ID of the item
   * @param newPrice The new buy price for the item
   * @return True if the price was updated, false otherwise
   */
  public boolean updateItemBuyPrice(String categoryId, String itemId, double newPrice) {
    ShopItem item = getItem(categoryId, itemId);
    if (item == null) return false;

    item.setBuyPrice(newPrice);
    return true;
  }

  /**
   * Updates an item's sell price
   *
   * @param categoryId The ID of the category
   * @param itemId The ID of the item
   * @param newPrice The new sell price for the item
   * @return True if the price was updated, false otherwise
   */
  public boolean updateItemSellPrice(String categoryId, String itemId, double newPrice) {
    ShopItem item = getItem(categoryId, itemId);
    if (item == null) return false;

    item.setSellPrice(newPrice);
    return true;
  }

  /** Clears all categories and items */
  public void clear() {
    this.categories.clear();
  }
}
