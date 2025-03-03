package net.digitalingot.feather.serverapi.api.ui.handler;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

/** Convenience class to create {@link UIFocusHandler} objects. */
public class UIFocusHandlerAdapter implements UIFocusHandler {

  /**
   * Called when the overlay gains focus.
   *
   * @param player The player who gained focus on the overlay.
   */
  @Override
  public void onFocusGained(@NotNull FeatherPlayer player) {}

  /**
   * Called when the overlay loses focus.
   *
   * @param player The player who lost focus on the overlay.
   */
  @Override
  public void onFocusLost(@NotNull FeatherPlayer player) {}
}
