package net.digitalingot.feather.serverapi.api.ui.handler;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

/** Convenience class to create {@link UIFocusHandler} objects. */
public class UIFocusHandlerAdapter implements UIFocusHandler {

  @Override
  public void onFocusGained(@NotNull FeatherPlayer player) {}

  @Override
  public void onFocusLost(@NotNull FeatherPlayer player) {}
}
