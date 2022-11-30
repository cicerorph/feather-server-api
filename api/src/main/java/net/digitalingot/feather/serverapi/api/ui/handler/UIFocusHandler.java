package net.digitalingot.feather.serverapi.api.ui.handler;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

public interface UIFocusHandler {

  /**
   *
   * @param player
   */
  void onFocusGained(@NotNull FeatherPlayer player);

  /**
   *
   * @param player
   */
  void onFocusLost(@NotNull FeatherPlayer player);
}
