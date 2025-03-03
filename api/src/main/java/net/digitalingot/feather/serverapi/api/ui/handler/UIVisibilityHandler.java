package net.digitalingot.feather.serverapi.api.ui.handler;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

/** Handler interface for UI visibility events. */
public interface UIVisibilityHandler {
  /**
   * Called when the UI page is shown for the specified player.
   *
   * @param player the player for whom the UI page was shown
   */
  void onShow(@NotNull FeatherPlayer player);

  /**
   * Called when the UI page is hidden from the specified player.
   *
   * @param player the player for whom the UI page was hidden
   */
  void onHide(@NotNull FeatherPlayer player);
}
