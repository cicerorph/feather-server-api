package net.digitalingot.feather.serverapi.api.ui.handler;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

/** Convenience class to create {@link UILifecycleHandler} objects. */
public class UILifecycleHandlerAdapter implements UILifecycleHandler {
  /**
   * Called when the UI page is created for the specified player.
   *
   * @param player the player for whom the UI page was created
   */
  @Override
  public void onCreated(@NotNull FeatherPlayer player) {}

  /**
   * Called when the UI page is destroyed for the specified player.
   *
   * @param player the player for whom the UI page was destroyed
   */
  @Override
  public void onDestroyed(@NotNull FeatherPlayer player) {}
}
