package net.digitalingot.feather.serverapi.api.ui.handler;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

/** Handler interface for UI load events. */
public interface UILoadHandler {
  /**
   * Called when an error occurs while loading the UI page for the specified player.
   *
   * @param player the player for whom the UI page failed to load
   * @param errorText the error message describing the load failure
   */
  void onLoadError(@NotNull FeatherPlayer player, @NotNull String errorText);
}
