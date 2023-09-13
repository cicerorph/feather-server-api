package net.digitalingot.feather.serverapi.api.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Deprecated
public class PlatformMod {
  @NotNull private final String name;
  @NotNull private final String version;

  public PlatformMod(@NotNull String name, @NotNull String version) {
    this.name = name;
    this.version = version;
  }

  @NotNull
  public String getName() {
    return name;
  }

  @NotNull
  public String getVersion() {
    return version;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    PlatformMod that = (PlatformMod) other;
    return name.equals(that.name) && version.equals(that.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, version);
  }
}
