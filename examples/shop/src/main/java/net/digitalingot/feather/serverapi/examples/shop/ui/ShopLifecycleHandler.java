package net.digitalingot.feather.serverapi.examples.shop.ui;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.digitalingot.feather.serverapi.api.ui.handler.UILifecycleHandlerAdapter;
import net.digitalingot.feather.serverapi.examples.shop.ShopPlugin;
import org.jetbrains.annotations.NotNull;

public class ShopLifecycleHandler extends UILifecycleHandlerAdapter {
  private final ShopPlugin plugin;

  public ShopLifecycleHandler(ShopPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void onCreated(@NotNull FeatherPlayer player) {
    this.plugin.getLogger().info("Shop UI created for " + player.getName());
  }

  @Override
  public void onDestroyed(@NotNull FeatherPlayer player) {
    this.plugin.getLogger().info("Shop UI destroyed for " + player.getName());
  }
}
