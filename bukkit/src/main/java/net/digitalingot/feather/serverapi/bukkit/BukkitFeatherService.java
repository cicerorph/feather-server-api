package net.digitalingot.feather.serverapi.bukkit;

import net.digitalingot.feather.serverapi.api.FeatherService;
import net.digitalingot.feather.serverapi.api.event.EventService;
import net.digitalingot.feather.serverapi.api.meta.MetaService;
import net.digitalingot.feather.serverapi.api.player.PlayerService;
import net.digitalingot.feather.serverapi.api.ui.UIService;
import net.digitalingot.feather.serverapi.api.waypoint.WaypointService;
import net.digitalingot.feather.serverapi.bukkit.event.BukkitEventService;
import net.digitalingot.feather.serverapi.bukkit.meta.BukkitMetaService;
import net.digitalingot.feather.serverapi.bukkit.player.BukkitPlayerService;
import net.digitalingot.feather.serverapi.bukkit.ui.BukkitUIService;
import net.digitalingot.feather.serverapi.bukkit.waypoint.BukkitWaypointService;
import org.jetbrains.annotations.NotNull;

public class BukkitFeatherService implements FeatherService {
  @NotNull
  private final BukkitEventService eventService;
  @NotNull
  private final BukkitPlayerService playerService;
  @NotNull
  private final BukkitUIService uiService;
  @NotNull
  private final BukkitWaypointService waypointService;
  @NotNull
  private final BukkitMetaService metaService;

  public BukkitFeatherService(
      @NotNull BukkitEventService eventService,
      @NotNull BukkitPlayerService playerService,
      @NotNull BukkitUIService uiService,
      @NotNull BukkitWaypointService waypointService,
      @NotNull BukkitMetaService metaService) {
    this.eventService = eventService;
    this.playerService = playerService;
    this.uiService = uiService;
    this.waypointService = waypointService;
    this.metaService = metaService;
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
    return this.waypointService;
  }

  @Override
  public @NotNull MetaService getMetaService() {
    return this.metaService;
  }
}
