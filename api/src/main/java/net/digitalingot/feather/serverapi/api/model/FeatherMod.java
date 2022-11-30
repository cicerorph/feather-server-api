package net.digitalingot.feather.serverapi.api.model;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class FeatherMod {
  @NotNull public final String name;

  public FeatherMod(@NotNull String name) {
    this.name = name;
  }

  @NotNull
  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    FeatherMod that = (FeatherMod) other;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
