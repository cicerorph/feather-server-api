package net.digitalingot.feather.serverapi.api.waypoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/** Builder for creating waypoints. */
public interface WaypointBuilder {
  /**
   * Sets the world ID of the waypoint. If no world ID is provided, the current world of the player
   * the waypoint will be used.
   *
   * @param worldId the UUID of the world where the waypoint should be located
   * @return the WaypointBuilder instance
   */
  WaypointBuilder withWorldId(@Nullable UUID worldId);

  /**
   * Sets the color of the waypoint. If no color is provided, the color will default to white.
   *
   * @param color the color of the waypoint
   * @return the WaypointBuilder instance
   */
  WaypointBuilder withColor(@Nullable WaypointColor color);

  /**
   * Sets the name of the waypoint. If no name is provided, the waypoint will have no name.
   *
   * @param name the name of the waypoint
   * @return the WaypointBuilder instance
   */
  WaypointBuilder withName(@Nullable String name);

  /**
   * Sets the duration of the waypoint. The duration cannot be null.
   *
   * @param duration the duration of the waypoint
   * @return the WaypointBuilder instance
   */
  WaypointBuilder withDuration(@NotNull WaypointDuration duration);
}
