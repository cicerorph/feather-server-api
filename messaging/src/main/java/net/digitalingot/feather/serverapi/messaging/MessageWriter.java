package net.digitalingot.feather.serverapi.messaging;

import java.util.Collection;
import java.util.UUID;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;

public interface MessageWriter {
  MessageWriter writeInt(int value);

  MessageWriter writeVarInt(int value);

  MessageWriter writeUtf(@NotNull String string);

  MessageWriter writeUtf(@NotNull String string, int limit);

  MessageWriter writeByte(byte value);

  MessageWriter writeUnsignedByte(short value);

  MessageWriter writeByteArray(byte[] bytes);

  MessageWriter writeBool(boolean value);

  <T> MessageWriter writeCollection(@NotNull Collection<T> items, @NotNull Encoder<T> encoder);

  <E extends Enum<E>> MessageWriter writeEnum(@NotNull Enum<E> value);

  MessageWriter writeLong(long value);

  MessageWriter writeUUID(@NotNull UUID value);

  <T> MessageWriter writeOptional(@NotNull T value, @NotNull Encoder<T> encoder);

  @FunctionalInterface
  interface Encoder<T> extends BiConsumer<MessageWriter, T> {}
}
