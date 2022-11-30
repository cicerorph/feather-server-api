package net.digitalingot.feather.serverapi.messaging;

import java.util.Collection;
import java.util.function.BiConsumer;

public interface MessageWriter {
  MessageWriter writeVarInt(int value);

  MessageWriter writeUtf(String string);

  MessageWriter writeUtf(String string, int limit);

  MessageWriter writeByte(byte value);

  MessageWriter writeBool(boolean value);

  <T> MessageWriter writeCollection(Collection<T> items, Encoder<T> encoder);

  <E extends Enum<E>> MessageWriter writeEnum(Enum<E> value);

  @FunctionalInterface
  interface Encoder<T> extends BiConsumer<MessageWriter, T> {}
}
