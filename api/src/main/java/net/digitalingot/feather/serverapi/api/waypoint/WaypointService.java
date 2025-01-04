package net.digitalingot.feather.serverapi.api.waypoint;

import java.util.Collection;
import java.util.UUID;
import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface WaypointService {
  /**
   * Creates a new WaypointBuilder instance.
   *
   * @param posX the X coordinate of the waypoint
   * @param posY the Y coordinate of the waypoint
   * @param posZ the Z coordinate of the waypoint
   * @return a new WaypointBuilder instance
   */
  @NotNull
  WaypointBuilder createWaypointBuilder(int posX, int posY, int posZ);

  /**
   * Creates a waypoint for the given player using the provided WaypointBuilder.
   *
   * @param player the player for whom the waypoint will be created
   * @param builder the WaypointBuilder instance containing the waypoint details
   * @return the UUID of the created waypoint
   */
  @NotNull
  UUID createWaypoint(@NotNull FeatherPlayer player, @NotNull WaypointBuilder builder);

  /**
   * Destroys a waypoint for the given player.
   *
   * @param player the player for whom the waypoint will be destroyed
   * @param waypointId the UUID of the waypoint to be destroyed
   */
  void destroyWaypoint(@NotNull FeatherPlayer player, @NotNull UUID waypointId);

  /**
   * Destroys multiple waypoints for the given player.
   *
   * @param player the player for whom the waypoints will be destroyed
   * @param waypointIds the collection of waypoint IDs to be destroyed
   */
  void destroyWaypoints(@NotNull FeatherPlayer player, @NotNull Collection<UUID> waypointIds);

  /**
   * Destroys all waypoints for the given player.
   *
   * @param player the player for whom all waypoints will be destroyed
   */
  void destroyAllWaypoints(@NotNull FeatherPlayer player);

  /**
   * @deprecated Use {@link #createWaypointBuilder(int, int, int)} instead.
   */
  @Deprecated
  default UUID createWaypoint(
      @NotNull FeatherPlayer player, int posX, int posY, int posZ, @NotNull WaypointColor color) {
    return createWaypoint(player, posX, posY, posZ, color, null);
  }

  /**
   * @deprecated Use {@link #createWaypointBuilder(int, int, int)} instead.
   */
  @Deprecated
  default UUID createWaypoint(
      @NotNull FeatherPlayer player,
      int posX,
      int posY,
      int posZ,
      @NotNull WaypointColor color,
      @Nullable String name) {
    return createWaypoint(player, posX, posY, posZ, color, name, WaypointDuration.none());
  }

  /**
   * @deprecated Use {@link #createWaypointBuilder(int, int, int)} instead.
   */
  @Deprecated
  UUID createWaypoint(
      @NotNull FeatherPlayer player,
      int posX,
      int posY,
      int posZ,
      @NotNull WaypointColor color,
      @Nullable String name,
      @NotNull WaypointDuration duration);

  /**
   * @deprecated Use {@link #createWaypointBuilder(int, int, int)} instead.
   */
  @Deprecated
  default UUID createWaypoint(
      @NotNull FeatherPlayer player,
      @NotNull UUID worldId,
      int posX,
      int posY,
      int posZ,
      @NotNull WaypointColor color) {
    return createWaypoint(player, worldId, posX, posY, posZ, color, null);
  }

  /**
   * @deprecated Use {@link #createWaypointBuilder(int, int, int)} instead.
   */
  @Deprecated
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

  /**
   * @deprecated Use {@link #createWaypointBuilder(int, int, int)} instead.
   */
  @Deprecated
  UUID createWaypoint(
      FeatherPlayer player,
      UUID worldId,
      int posX,
      int posY,
      int posZ,
      @NotNull WaypointColor color,
      @Nullable String name,
      @NotNull WaypointDuration duration);
}
