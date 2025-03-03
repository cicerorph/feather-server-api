package net.digitalingot.feather.serverapi.examples.shop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Represents a category in the shop */
public class ShopCategory {
  private final String id;
  private final String name;
  private final String icon;
  private final Map<String, ShopItem> items = new HashMap<>();

  /**
   * Creates a new shop category
   *
   * @param id The category ID
   * @param name The category name
   * @param icon The category icon (usually a single character)
   */
  public ShopCategory(String id, String name, String icon) {
    this.id = id;
    this.name = name;
    this.icon = icon;
  }

  /**
   * Gets the category ID
   *
   * @return The category ID
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the category name
   *
   * @return The category name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the category icon
   *
   * @return The category icon
   */
  public String getIcon() {
    return icon;
  }

  /**
   * Adds an item to the category
   *
   * @param item The item to add
   */
  public void addItem(ShopItem item) {
    this.items.put(item.getId(), item);
  }

  /**
   * Removes an item from the category
   *
   * @param itemId The ID of the item to remove
   * @return True if the item was removed, false otherwise
   */
  public boolean removeItem(String itemId) {
    return this.items.remove(itemId) != null;
  }

  /**
   * Checks if an item exists in this category
   *
   * @param itemId The ID of the item to check
   * @return True if the item exists, false otherwise
   */
  public boolean hasItem(String itemId) {
    return this.items.containsKey(itemId);
  }

  /**
   * Gets an item from this category
   *
   * @param itemId The ID of the item to get
   * @return The item, or null if not found
   */
  public ShopItem getItem(String itemId) {
    return this.items.get(itemId);
  }

  /**
   * Gets all items in this category
   *
   * @return A collection of all items
   */
  public Collection<ShopItem> getItems() {
    return this.items.values();
  }

  /**
   * Gets a list of all item IDs in this category
   *
   * @return A list of item IDs
   */
  public List<String> getItemIds() {
    return new ArrayList<>(this.items.keySet());
  }

  /**
   * Gets the number of items in this category
   *
   * @return The number of items
   */
  public int getItemCount() {
    return this.items.size();
  }
}
