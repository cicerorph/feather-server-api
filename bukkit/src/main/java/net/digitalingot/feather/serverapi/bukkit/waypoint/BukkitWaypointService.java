package net.digitalingot.feather.serverapi.bukkit.waypoint;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.digitalingot.feather.serverapi.api.waypoint.WaypointBuilder;
import net.digitalingot.feather.serverapi.api.waypoint.WaypointColor;
import net.digitalingot.feather.serverapi.api.waypoint.WaypointDuration;
import net.digitalingot.feather.serverapi.api.waypoint.WaypointService;
import net.digitalingot.feather.serverapi.bukkit.FeatherBukkitPlugin;
import net.digitalingot.feather.serverapi.bukkit.event.player.BukkitPlayerHelloEvent;
import net.digitalingot.feather.serverapi.bukkit.player.BukkitFeatherPlayer;
import net.digitalingot.feather.serverapi.bukkit.player.BukkitPlayerService;
import net.digitalingot.feather.serverapi.common.waypoints.DefaultWaypointBuilder;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CWaypointCreate;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CWaypointDestroy;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CWorldChange;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitWaypointService implements WaypointService, Listener {
  public BukkitWaypointService(FeatherBukkitPlugin plugin, BukkitPlayerService playerService) {
    Bukkit.getPluginManager().registerEvents(new WorldSync(playerService), plugin);
  }

  @Override
  public UUID createWaypoint(
      @NotNull FeatherPlayer player,
      int posX,
      int posY,
      int posZ,
      @NotNull WaypointColor color,
      @Nullable String name,
      @NotNull WaypointDuration duration) {
    BukkitFeatherPlayer featherPlayer = (BukkitFeatherPlayer) player;
    UUID worldId = featherPlayer.getPlayer().getWorld().getUID();
    return createWaypoint(player, worldId, posX, posY, posZ, color, name, duration);
  }

  @Override
  public UUID createWaypoint(
      FeatherPlayer player,
      UUID worldId,
      int posX,
      int posY,
      int posZ,
      @NotNull WaypointColor color,
      @Nullable String name,
      @NotNull WaypointDuration duration) {
    BukkitFeatherPlayer featherPlayer = (BukkitFeatherPlayer) player;
    UUID waypointId = UUID.randomUUID();
    featherPlayer.sendMessage(
        new S2CWaypointCreate(
            waypointId,
            worldId,
            posX,
            posY,
            posZ,
            color.isChroma(),
            color.getRgba(),
            name,
            duration.getDurationInSeconds()));
    return waypointId;
  }

  @Override
  public @NotNull WaypointBuilder createWaypointBuilder(int posX, int posY, int posZ) {
    return new DefaultWaypointBuilder(posX, posY, posZ);
  }

  @Override
  public @NotNull UUID createWaypoint(
      @NotNull FeatherPlayer player, @NotNull WaypointBuilder builder) {
    DefaultWaypointBuilder defaultWaypointBuilder = (DefaultWaypointBuilder) builder;
    return createWaypoint(
        player,
        defaultWaypointBuilder.getPosX(),
        defaultWaypointBuilder.getPosY(),
        defaultWaypointBuilder.getPosZ(),
        defaultWaypointBuilder.getColor(),
        defaultWaypointBuilder.getName(),
        defaultWaypointBuilder.getDuration());
  }

  @Override
  public void destroyWaypoint(@NotNull FeatherPlayer player, @NotNull UUID waypointId) {
    ((BukkitFeatherPlayer) player).sendMessage(new S2CWaypointDestroy(waypointId));
  }

  @Override
  public void destroyWaypoints(
      @NotNull FeatherPlayer player, @NotNull Collection<@NotNull UUID> waypoints) {
    ((BukkitFeatherPlayer) player).sendMessage(new S2CWaypointDestroy(waypoints));
  }

  @Override
  public void destroyAllWaypoints(@NotNull FeatherPlayer player) {
    destroyWaypoints(player, Collections.emptyList());
  }

  private static class WorldSync implements Listener {
    private final BukkitPlayerService playerService;

    public WorldSync(BukkitPlayerService playerService) {
      this.playerService = playerService;
    }

    @EventHandler
    public void onFeatherPlayerHello(BukkitPlayerHelloEvent event) {
      BukkitFeatherPlayer featherPlayer = (BukkitFeatherPlayer) event.getPlayer();
      sendWorldChange(featherPlayer, featherPlayer.getPlayer().getWorld());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
      if (!event.getFrom().getWorld().equals(event.getTo().getWorld())) {
        BukkitFeatherPlayer player = this.playerService.getPlayer(event.getPlayer().getUniqueId());
        if (player != null) {
          sendWorldChange(player, event.getTo().getWorld());
        }
      }
    }

    private static void sendWorldChange(BukkitFeatherPlayer player, World world) {
      player.sendMessage(new S2CWorldChange(world.getUID()));
    }
  }
}
