package net.digitalingot.feather.serverapi.api.ui.handler;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

/** Convenience class to create {@link UILifecycleHandler} objects. */
public class UILifecycleHandlerAdapter implements UILifecycleHandler {
  @Override
  public void onCreated(@NotNull FeatherPlayer player) {}

  @Override
  public void onDestroyed(@NotNull FeatherPlayer player) {}
}
