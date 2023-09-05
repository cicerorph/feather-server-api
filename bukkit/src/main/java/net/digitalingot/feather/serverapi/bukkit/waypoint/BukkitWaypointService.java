package net.digitalingot.feather.serverapi.bukkit.waypoint;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.digitalingot.feather.serverapi.api.waypoint.WaypointService;
import net.digitalingot.feather.serverapi.bukkit.FeatherBukkitPlugin;
import net.digitalingot.feather.serverapi.bukkit.event.player.BukkitPlayerHelloEvent;
import net.digitalingot.feather.serverapi.bukkit.player.BukkitFeatherPlayer;
import net.digitalingot.feather.serverapi.bukkit.player.BukkitPlayerService;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CWaypointCreate;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CWaypointDestroy;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CWorldChange;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BukkitWaypointService implements WaypointService, Listener {
  public BukkitWaypointService(FeatherBukkitPlugin plugin, BukkitPlayerService playerService) {
    Bukkit.getPluginManager().registerEvents(new WorldSync(playerService), plugin);
  }

  @Override
  public UUID createWaypoint(FeatherPlayer player, int posX, int posY, int posZ, int color) {
    return createWaypoint(player, posX, posY, posZ, color, null);
  }

  @Override
  public UUID createWaypoint(
      FeatherPlayer player, int posX, int posY, int posZ, int color, String name) {
    return createWaypoint(player, posX, posY, posZ, color, name, -1);
  }

  @Override
  public UUID createWaypoint(
      FeatherPlayer player, int posX, int posY, int posZ, int color, String name, int duration) {
    BukkitFeatherPlayer featherPlayer = (BukkitFeatherPlayer) player;
    UUID worldId = featherPlayer.getPlayer().getWorld().getUID();
    return createWaypoint(player, worldId, posX, posY, posZ, color, name, duration);
  }

  @Override
  public UUID createWaypoint(FeatherPlayer player, UUID worldId, int posX, int posY, int posZ,
      int color) {
    return createWaypoint(player, worldId, posX, posY, posZ, color, null);
  }

  @Override
  public UUID createWaypoint(FeatherPlayer player, UUID worldId, int posX, int posY, int posZ,
      int color, String name) {
    return createWaypoint(player, worldId, posX, posY, posZ, color, null, -1);
  }

  @Override
  public UUID createWaypoint(FeatherPlayer player, UUID worldId, int posX, int posY, int posZ,
      int color, String name, int duration) {
    BukkitFeatherPlayer featherPlayer = (BukkitFeatherPlayer) player;
    UUID waypointId = UUID.randomUUID();
    featherPlayer.sendMessage(
        new S2CWaypointCreate(waypointId, worldId, posX, posY, posZ, color, name, duration));
    return waypointId;
  }

  @Override
  public void destroyWaypoint(FeatherPlayer player, UUID waypointId) {
    ((BukkitFeatherPlayer) player).sendMessage(new S2CWaypointDestroy(waypointId));
  }

  @Override
  public void destroyWaypoints(FeatherPlayer player, Collection<UUID> waypoints) {
    ((BukkitFeatherPlayer) player).sendMessage(new S2CWaypointDestroy(waypoints));
  }

  @Override
  public void destroyAllWaypoints(FeatherPlayer player) {
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
