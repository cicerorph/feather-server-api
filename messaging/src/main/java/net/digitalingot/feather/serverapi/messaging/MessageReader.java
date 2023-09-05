package net.digitalingot.feather.serverapi.messaging;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.IntFunction;

public interface MessageReader {

  int readInt();

  int readVarInt();

  String readUtf();

  String readUtf(int limit);

  byte readByte();

  short readUnsignedByte();

  byte[] readByteArray();

  byte[] readByteArray(int limit);

  boolean readBool();

  MessageReader readBytes(byte[] dst, int offset, int length);

  <T> List<T> readList(Decoder<T> decoder);

  <T, C extends Collection<T>> C readCollection(IntFunction<C> factory, Decoder<T> decoder);

  <E extends Enum<E>> E readEnum(Class<E> type);

  long readLong();

  UUID readUUID();

  <T> Optional<T> readOptional(Decoder<T> decoder);

  @FunctionalInterface
  interface Decoder<T> extends Function<MessageReader, T> {}
}
