package net.digitalingot.feather.serverapi.api.waypoint;

import java.util.Collection;
import java.util.UUID;
import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface WaypointService {
  default UUID createWaypoint(
      @NotNull FeatherPlayer player, int posX, int posY, int posZ, @NotNull WaypointColor color) {
    return createWaypoint(player, posX, posY, posZ, color, null);
  }

  default UUID createWaypoint(
      @NotNull FeatherPlayer player,
      int posX,
      int posY,
      int posZ,
      @NotNull WaypointColor color,
      @Nullable String name) {
    return createWaypoint(player, posX, posY, posZ, color, name, WaypointDuration.none());
  }

  UUID createWaypoint(
      @NotNull FeatherPlayer player,
      int posX,
      int posY,
      int posZ,
      @NotNull WaypointColor color,
      @Nullable String name,
      @NotNull WaypointDuration duration);

  default UUID createWaypoint(
      @NotNull FeatherPlayer player,
      @NotNull UUID worldId,
      int posX,
      int posY,
      int posZ,
      @NotNull WaypointColor color) {
    return createWaypoint(player, worldId, posX, posY, posZ, color, null);
  }

  default UUID createWaypoint(
      @NotNull FeatherPlayer player,
      @NotNull UUID worldId,
      int posX,
      int posY,
      int posZ,
      @NotNull WaypointColor color,
      @Nullable String name) {
    return createWaypoint(player, worldId, posX, posY, posZ, color, name, WaypointDuration.none());
  }

  UUID createWaypoint(
      FeatherPlayer player,
      UUID worldId,
      int posX,
      int posY,
      int posZ,
      @NotNull WaypointColor color,
      @Nullable String name,
      @NotNull WaypointDuration duration);

  void destroyWaypoint(@NotNull FeatherPlayer player, @NotNull UUID waypointId);

  void destroyWaypoints(
      @NotNull FeatherPlayer player, @NotNull Collection<@NotNull UUID> waypoints);

  void destroyAllWaypoints(@NotNull FeatherPlayer player);
}
