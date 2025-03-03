package net.digitalingot.feather.serverapi.common.waypoints;

import java.util.UUID;
import net.digitalingot.feather.serverapi.api.waypoint.WaypointBuilder;
import net.digitalingot.feather.serverapi.api.waypoint.WaypointColor;
import net.digitalingot.feather.serverapi.api.waypoint.WaypointDuration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DefaultWaypointBuilder implements WaypointBuilder {

  private final int posX;
  private final int posY;
  private final int posZ;
  private @Nullable UUID worldId;
  private @NotNull WaypointColor color = new WaypointColor(1.0f, 1.0f, 1.0f, 1.0f);
  private @Nullable String name;
  private @NotNull WaypointDuration duration = WaypointDuration.NONE;

  public DefaultWaypointBuilder(int posX, int posY, int posZ) {
    this.posX = posX;
    this.posY = posY;
    this.posZ = posZ;
  }

  @Override
  public WaypointBuilder withWorldId(@Nullable UUID worldId) {
    this.worldId = worldId;
    return this;
  }

  @Override
  public WaypointBuilder withColor(@Nullable WaypointColor color) {
    this.color = color;
    return this;
  }

  @Override
  public WaypointBuilder withName(@Nullable String name) {
    this.name = name;
    return this;
  }

  @Override
  public WaypointBuilder withDuration(@NotNull WaypointDuration duration) {
    this.duration = duration;
    return this;
  }

  public int getPosX() {
    return this.posX;
  }

  public int getPosY() {
    return this.posY;
  }

  public int getPosZ() {
    return this.posZ;
  }

  public @Nullable UUID getWorldId() {
    return this.worldId;
  }

  public @NotNull WaypointColor getColor() {
    return this.color;
  }

  public @Nullable String getName() {
    return this.name;
  }

  public @NotNull WaypointDuration getDuration() {
    return this.duration;
  }
}
