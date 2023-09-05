package net.digitalingot.feather.serverapi.api.waypoint;

import java.util.Collection;
import java.util.UUID;
import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;

public interface WaypointService {
  UUID createWaypoint(FeatherPlayer player, int posX, int posY, int posZ, int color);

  UUID createWaypoint(FeatherPlayer player, int posX, int posY, int posZ, int color, String name);

  UUID createWaypoint(
      FeatherPlayer player, int posX, int posY, int posZ, int color, String name, int duration);

  UUID createWaypoint(FeatherPlayer player, UUID worldId, int posX, int posY, int posZ, int color);

  UUID createWaypoint(
      FeatherPlayer player, UUID worldId, int posX, int posY, int posZ, int color, String name);

  UUID createWaypoint(
      FeatherPlayer player,
      UUID worldId,
      int posX,
      int posY,
      int posZ,
      int color,
      String name,
      int duration);

  void destroyWaypoint(FeatherPlayer player, UUID waypointId);

  void destroyWaypoints(FeatherPlayer player, Collection<UUID> waypoints);

  void destroyAllWaypoints(FeatherPlayer player);
}
