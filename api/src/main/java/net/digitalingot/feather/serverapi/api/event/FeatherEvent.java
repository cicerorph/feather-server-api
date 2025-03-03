package net.digitalingot.feather.serverapi.api.event;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

public interface FeatherEvent {

  /**
   * Returns the player associated with this event.
   *
   * @return the player associated with this event
   */
  @NotNull
  FeatherPlayer getPlayer();
}
