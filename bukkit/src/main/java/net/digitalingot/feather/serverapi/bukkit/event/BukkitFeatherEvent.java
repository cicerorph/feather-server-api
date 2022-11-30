package net.digitalingot.feather.serverapi.bukkit.event;

import net.digitalingot.feather.serverapi.api.event.FeatherEvent;
import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class BukkitFeatherEvent extends Event implements FeatherEvent {

  private final FeatherPlayer player;

  public BukkitFeatherEvent(@NotNull FeatherPlayer player) {
    this.player = player;
  }

  public BukkitFeatherEvent(boolean isAsync, @NotNull FeatherPlayer player) {
    super(isAsync);
    this.player = player;
  }

  @NotNull
  @Override
  public FeatherPlayer getPlayer() {
    return this.player;
  }
}
