package net.digitalingot.feather.serverapi.api.ui.handler;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

/** Handler interface for UI lifecycle events. */
public interface UILifecycleHandler {
  /**
   * Called when the UI page is created for the specified player.
   *
   * @param player the player for whom the UI page was created
   */
  void onCreated(@NotNull FeatherPlayer player);

  /**
   * Called when the UI page is destroyed for the specified player.
   *
   * @param player the player for whom the UI page was destroyed
   */
  void onDestroyed(@NotNull FeatherPlayer player);
}
