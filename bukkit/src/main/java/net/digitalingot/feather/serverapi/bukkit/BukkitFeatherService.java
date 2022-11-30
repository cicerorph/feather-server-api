package net.digitalingot.feather.serverapi.bukkit;

import net.digitalingot.feather.serverapi.api.FeatherService;
import net.digitalingot.feather.serverapi.api.event.EventService;
import net.digitalingot.feather.serverapi.api.player.PlayerService;
import net.digitalingot.feather.serverapi.api.ui.UIService;
import net.digitalingot.feather.serverapi.api.waypoint.WaypointService;
import net.digitalingot.feather.serverapi.bukkit.event.BukkitEventService;
import net.digitalingot.feather.serverapi.bukkit.player.BukkitPlayerService;
import net.digitalingot.feather.serverapi.bukkit.ui.BukkitUIService;
import org.jetbrains.annotations.NotNull;

public class BukkitFeatherService implements FeatherService {
  private final BukkitEventService eventService;
  private final BukkitPlayerService playerService;
  private final BukkitUIService uiService;

  public BukkitFeatherService(
      BukkitEventService eventService,
      BukkitPlayerService playerService,
      BukkitUIService uiService) {
    this.eventService = eventService;
    this.playerService = playerService;
    this.uiService = uiService;
  }

  @Override
  public @NotNull EventService getEventService() {
    return this.eventService;
  }

  @Override
  public @NotNull PlayerService getPlayerService() {
    return this.playerService;
  }

  @Override
  public @NotNull UIService getUIService() {
    return this.uiService;
  }

  @Override
  public @NotNull WaypointService getWaypointService() {
    throw new UnsupportedOperationException("Not implemented");
  }
}
