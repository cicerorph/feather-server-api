package net.digitalingot.feather.serverapi.bukkit.event.player;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.digitalingot.feather.serverapi.bukkit.event.BukkitFeatherEvent;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class FeatherPlayerQuitEvent extends BukkitFeatherEvent {
  private static final HandlerList HANDLER_LIST = new HandlerList();

  public FeatherPlayerQuitEvent(@NotNull FeatherPlayer player) {
    super(player);
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}
