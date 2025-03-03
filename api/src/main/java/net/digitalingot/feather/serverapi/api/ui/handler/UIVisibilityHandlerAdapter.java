package net.digitalingot.feather.serverapi.api.ui.handler;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

public class UIVisibilityHandlerAdapter implements UIVisibilityHandler {

  /**
   * Called when the UI page is shown for the specified player.
   *
   * @param player the player for whom the UI page was shown
   */
  @Override
  public void onShow(@NotNull FeatherPlayer player) {}

  /**
   * Called when the UI page is hidden from the specified player.
   *
   * @param player the player for whom the UI page was hidden
   */
  @Override
  public void onHide(@NotNull FeatherPlayer player) {}
}
