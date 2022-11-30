package net.digitalingot.feather.serverapi.api.ui.handler;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;

public interface UIVisibilityHandler {

  /**
   *
   * @param player
   */
  void onShow(FeatherPlayer player);

  /**
   *
   * @param player
   */
  void onHide(FeatherPlayer player);
}
