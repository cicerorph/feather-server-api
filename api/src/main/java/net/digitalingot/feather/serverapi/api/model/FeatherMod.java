package net.digitalingot.feather.serverapi.api.model;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/** Represents a built-in mod in Feather. */
public class FeatherMod {

  /** The name of the mod. */
  @NotNull public final String name;

  public FeatherMod(@NotNull String name) {
    this.name = name;
  }

  /**
   * Returns the name of the mod.
   *
   * @return the name of the mod
   */
  @NotNull
  public String getName() {
    return this.name;
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
    return this.name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name);
  }
}
