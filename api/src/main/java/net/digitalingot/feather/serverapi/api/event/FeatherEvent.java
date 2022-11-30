package net.digitalingot.feather.serverapi.api.event;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

public interface FeatherEvent {
  @NotNull
  FeatherPlayer getPlayer();
}
