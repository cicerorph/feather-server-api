package net.digitalingot.feather.serverapi.api.ui.handler;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

/** Convenience class to create {@link UILoadHandler} objects. */
public class UILoadHandlerAdapter implements UILoadHandler {

  @Override
  public void onLoadError(@NotNull FeatherPlayer player, @NotNull String errorText) {}
}
