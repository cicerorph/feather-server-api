package net.digitalingot.feather.serverapi.messaging;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

public interface MessageWriter {
  MessageWriter writeInt(int value);

  MessageWriter writeVarInt(int value);

  MessageWriter writeUtf(String string);

  MessageWriter writeUtf(String string, int limit);

  MessageWriter writeByte(byte value);

  MessageWriter writeUnsignedByte(short value);

  MessageWriter writeByteArray(byte[] bytes);

  MessageWriter writeBool(boolean value);

  <T> MessageWriter writeCollection(Collection<T> items, Encoder<T> encoder);

  <E extends Enum<E>> MessageWriter writeEnum(Enum<E> value);

  MessageWriter writeLong(long value);

  MessageWriter writeUUID(UUID value);

  <T> MessageWriter writeOptional(T value, Encoder<T> encoder);

  @FunctionalInterface
  interface Encoder<T> extends BiConsumer<MessageWriter, T> {}
}
