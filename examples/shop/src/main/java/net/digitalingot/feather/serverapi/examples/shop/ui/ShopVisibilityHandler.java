package net.digitalingot.feather.serverapi.examples.shop.ui;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.digitalingot.feather.serverapi.api.ui.handler.UIVisibilityHandlerAdapter;
import net.digitalingot.feather.serverapi.examples.shop.ShopPlugin;
import org.jetbrains.annotations.NotNull;

public class ShopVisibilityHandler extends UIVisibilityHandlerAdapter {
  private final ShopPlugin plugin;

  public ShopVisibilityHandler(ShopPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void onShow(@NotNull FeatherPlayer player) {
    // Update shop data when shown
    this.plugin.sendShopData(player);
  }
}
