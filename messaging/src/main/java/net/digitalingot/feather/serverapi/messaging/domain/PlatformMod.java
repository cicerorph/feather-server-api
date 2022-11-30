package net.digitalingot.feather.serverapi.messaging.domain;

import java.util.Objects;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

public class PlatformMod {
  @Internal public static final MessageReader.Decoder<PlatformMod> DECODER = new Decoder();
  @Internal public static final MessageWriter.Encoder<PlatformMod> ENCODER = new Encoder();

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

  @Internal
  private static class Decoder implements MessageReader.Decoder<PlatformMod> {

    @Override
    public PlatformMod apply(MessageReader reader) {
      String name = reader.readUtf(64);
      String version = reader.readUtf(64);
      return new PlatformMod(name, version);
    }
  }

  @Internal
  private static class Encoder implements MessageWriter.Encoder<PlatformMod> {

    @Override
    public void accept(MessageWriter writer, PlatformMod platformMod) {
      writer.writeUtf(platformMod.getName());
      writer.writeUtf(platformMod.getVersion());
    }
  }
}
