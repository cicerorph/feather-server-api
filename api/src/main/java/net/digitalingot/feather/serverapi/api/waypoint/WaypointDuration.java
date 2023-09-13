package net.digitalingot.feather.serverapi.api.waypoint;

import java.util.Objects;

public class WaypointDuration {
  public static final WaypointDuration NONE = new WaypointDuration(-1);

  private final int duration;

  public WaypointDuration(int duration) {
    this.duration = duration;
  }

  public int getDuration() {
    return this.duration;
  }

  public boolean isNone() {
    return this.equals(NONE);
  }

  public static WaypointDuration of(int duration) {
    return new WaypointDuration(duration);
  }

  public static WaypointDuration none() {
    return NONE;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (!(other instanceof WaypointDuration)) return false;
    WaypointDuration that = (WaypointDuration) other;
    return this.duration == that.duration;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.duration);
  }

  @Override
  public String toString() {
    return "WaypointDuration{" + "duration=" + this.duration + '}';
  }
}
