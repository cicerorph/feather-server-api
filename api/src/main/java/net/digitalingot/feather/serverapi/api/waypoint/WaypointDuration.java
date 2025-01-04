package net.digitalingot.feather.serverapi.api.waypoint;

import java.util.Objects;

/** Represents the duration of a waypoint */
public class WaypointDuration {
  /** A constant representing a waypoint with no duration. */
  private static final int NO_DURATION = 0;

  /**
   * A constant representing a waypoint with no duration. Use this when you need to represent a
   * waypoint that should live indefinitely.
   */
  public static final WaypointDuration NONE = new WaypointDuration(NO_DURATION);

  /** The duration of the waypoint in seconds */
  private final int durationInSeconds;

  /**
   * Creates a new WaypointDuration with the specified duration in seconds.
   *
   * @param durationInSeconds the duration of the waypoint in seconds
   */
  public WaypointDuration(int durationInSeconds) {
    if (durationInSeconds < 0) {
      throw new IllegalArgumentException("Duration must be non-negative");
    }
    this.durationInSeconds = durationInSeconds;
  }

  /**
   * @deprecated Use {@link #getDurationInSeconds} instead.
   */
  @Deprecated
  public int getDuration() {
    return this.durationInSeconds;
  }

  /**
   * Returns the duration of the waypoint in seconds.
   *
   * @return the duration of the waypoint in seconds
   */
  public int getDurationInSeconds() {
    return this.durationInSeconds;
  }

  /**
   * Checks if the waypoint has no duration.
   *
   * @return true if the waypoint has no duration, false otherwise
   */
  public boolean isNone() {
    return this.durationInSeconds == NO_DURATION;
  }

  /**
   * Creates a new WaypointDuration with the specified duration in seconds.
   *
   * @param durationInSeconds the duration of the waypoint in seconds
   * @return a new WaypointDuration
   */
  public static WaypointDuration of(int durationInSeconds) {
    return new WaypointDuration(durationInSeconds);
  }

  /**
   * Returns a WaypointDuration representing no duration. Use this method when you need to represent
   * a waypoint with no duration
   *
   * @return a WaypointDuration representing no duration
   */
  public static WaypointDuration none() {
    return NONE;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (!(other instanceof WaypointDuration)) return false;
    WaypointDuration that = (WaypointDuration) other;
    return this.durationInSeconds == that.durationInSeconds;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.durationInSeconds);
  }

  @Override
  public String toString() {
    return "WaypointDuration{" + "durationInSeconds=" + this.durationInSeconds + '}';
  }
}
