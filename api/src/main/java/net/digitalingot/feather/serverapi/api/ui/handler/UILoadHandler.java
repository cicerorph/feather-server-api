package net.digitalingot.feather.serverapi.api.ui.handler;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

public interface UILoadHandler {

  /**
   * @param player
   */
  void onLoadError(@NotNull FeatherPlayer player, @NotNull String errorText);
}
