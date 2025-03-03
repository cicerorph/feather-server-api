package net.digitalingot.feather.serverapi.api.ui.handler;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

public interface UIFocusHandler {

  /**
   * Called when the overlay gains focus.
   *
   * @param player The player who gained focus on the overlay.
   */
  void onFocusGained(@NotNull FeatherPlayer player);

  /**
   * Called when the overlay loses focus.
   *
   * @param player The player who lost focus on the overlay.
   */
  void onFocusLost(@NotNull FeatherPlayer player);
}
