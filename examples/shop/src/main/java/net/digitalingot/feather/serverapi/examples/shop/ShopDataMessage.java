package net.digitalingot.feather.serverapi.examples.shop;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/** Helper class to create JSON messages for the shop UI */
public class ShopDataMessage {
  private static final Gson GSON = new Gson();
  private final JsonObject data = new JsonObject();
  private final JsonArray categories = new JsonArray();

  /** Creates a new shop data message */
  public ShopDataMessage() {
    this.data.addProperty("type", "shopData");
    this.data.add("categories", this.categories);
  }

  /**
   * Sets the player's balance
   *
   * @param balance The player's balance
   */
  public void setBalance(double balance) {
    this.data.addProperty("balance", balance);
  }

  /**
   * Adds a category to the message
   *
   * @param id The category ID
   * @param name The category name
   * @param icon The category icon
   */
  public void addCategory(String id, String name, String icon) {
    JsonObject category = new JsonObject();
    category.addProperty("id", id);
    category.addProperty("name", name);
    category.addProperty("icon", icon);
    this.categories.add(category);
  }

  /**
   * Converts the message to a JSON string
   *
   * @return The JSON string
   */
  public String toJson() {
    return GSON.toJson(this.data);
  }
}
