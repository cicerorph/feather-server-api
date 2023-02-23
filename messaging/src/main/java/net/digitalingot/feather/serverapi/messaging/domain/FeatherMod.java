package net.digitalingot.feather.serverapi.messaging.domain;

import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FeatherMod {
  @Internal public static final MessageReader.Decoder<FeatherMod> DECODER = new Decoder();
  @Internal public static final MessageWriter.Encoder<FeatherMod> ENCODER = new Encoder();

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

  @Internal
  private static class Decoder implements MessageReader.Decoder<FeatherMod> {

    @Override
    public FeatherMod apply(MessageReader reader) {
      String name = reader.readUtf(64);
      return new FeatherMod(name);
    }
  }

  @Internal
  private static class Encoder implements MessageWriter.Encoder<FeatherMod> {

    @Override
    public void accept(MessageWriter writer, FeatherMod featherMod) {
      writer.writeUtf(featherMod.getName());
    }
  }
}
