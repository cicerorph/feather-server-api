package net.digitalingot.feather.serverapi.messaging;

import net.digitalingot.feather.serverapi.messaging.exception.MessageException;
import net.digitalingot.feather.serverapi.messaging.exception.OverflowException;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.IntFunction;

public class SimpleMessageBuffer implements MessageBuffer {
  private static final int MAX_BUFFER_SIZE = 32766;
  private static final int DEFAULT_CAPACITY = 32;
  private static final float DEFAULT_EXPAND_FACTOR = 2f;
  private static final int WORST_CASE_UTF_ENCODED_SIZE = 3;
  private final float expandFactor;
  private ByteBuffer buffer;

  public SimpleMessageBuffer(ByteBuffer buffer) {
    this.buffer = buffer;
    this.expandFactor = DEFAULT_EXPAND_FACTOR;
  }

  public SimpleMessageBuffer() {
    this(DEFAULT_CAPACITY);
  }

  public SimpleMessageBuffer(int capacity) {
    this(capacity, DEFAULT_EXPAND_FACTOR);
  }

  public SimpleMessageBuffer(int capacity, float expandFactor) {
    this.buffer = ByteBuffer.allocate(capacity);
    this.expandFactor = expandFactor;
  }

  public static SimpleMessageBuffer of(byte[] data) {
    return new SimpleMessageBuffer(ByteBuffer.wrap(data));
  }

  private static IntFunction<ByteBuffer> getAllocator(ByteBuffer buffer) {
    return buffer.isDirect() ? ByteBuffer::allocateDirect : ByteBuffer::allocate;
  }

  private void ensureRemaining(int size) {
    ByteBuffer oldBuffer = this.buffer;
    if (oldBuffer.remaining() > size) {
      return;
    }
    int newCapacity = (int) (oldBuffer.capacity() * this.expandFactor);
    while (newCapacity < (oldBuffer.capacity() + size)) {
      newCapacity *= this.expandFactor;
    }
    ByteBuffer newBuffer = getAllocator(oldBuffer).apply(newCapacity);
    newBuffer.order(oldBuffer.order());
    oldBuffer.flip();
    newBuffer.put(oldBuffer);
    this.buffer = newBuffer;
  }

  public int getWorstCaseUtfEncodedSize(int length) {
    return length * WORST_CASE_UTF_ENCODED_SIZE;
  }

  public int varIntSize(int value) {
    int result = 0;
    do {
      result++;
      value >>>= 7;
    } while (value != 0);
    return result;
  }

  @Override
  public int readInt() {
    return this.buffer.getInt();
  }

  @Override
  public MessageWriter writeInt(int value) {
    ensureRemaining(4);
    this.buffer.putInt(value);
    return this;
  }

  @Override
  public int readVarInt() {
    int result = 0;
    int shift = 0;
    byte value;
    do {
      if (shift >= 32) {
        throw new OverflowException("VarInt", shift / 7, 5);
      }
      value = readByte();
      result |= (value & 0x7F) << shift;
      shift += 7;
    } while ((value & 0x80) != 0);
    return result;
  }

  @Override
  public MessageWriter writeVarInt(int value) {
    ensureRemaining(varIntSize(value));
    while (true) {
      int bits = value & 0x7F;
      value >>>= 7;
      if (value == 0) {
        writeByte((byte) bits);
        return this;
      }
      writeByte((byte) (bits | 0x80));
    }
  }

  @Override
  public String readUtf() {
    return readUtf(Short.MAX_VALUE);
  }

  @Override
  public String readUtf(int limit) {
    int encodingLimit = getWorstCaseUtfEncodedSize(limit);
    int encodedLength = readVarInt();
    if (encodedLength > encodingLimit) {
      throw new OverflowException("String", encodedLength, encodingLimit);
    } else if (encodedLength < 0) {
      throw new MessageException("Negative string length (" + encodedLength + ")");
    }
    byte[] encoded = new byte[encodedLength];
    readBytes(encoded);
    String decoded = new String(encoded, StandardCharsets.UTF_8);
    if (decoded.length() > limit) {
      throw new OverflowException("String", decoded.length(), limit);
    }
    return decoded;
  }

  @Override
  public MessageWriter writeUtf(String string) {
    return writeUtf(string, Short.MAX_VALUE);
  }

  @Override
  public MessageWriter writeUtf(String string, int limit) {
    if (string.length() > limit) {
      throw new OverflowException("String", string.length(), limit);
    }
    byte[] encoded = string.getBytes(StandardCharsets.UTF_8);
    int encodingLimit = getWorstCaseUtfEncodedSize(limit);
    if (encoded.length > encodingLimit) {
      throw new OverflowException("String", encoded.length, encodingLimit);
    }
    ensureRemaining(varIntSize(encoded.length) + encoded.length);
    writeVarInt(encoded.length);
    writeBytes(encoded);
    return this;
  }

  public List<String> readUtfList() {
    return readList(MessageReader::readUtf);
  }

  public <T> List<T> readList(Decoder<T> decoder) {
    return readCollection(ArrayList::new, decoder);
  }

  public <T, C extends Collection<T>> C readCollection(IntFunction<C> factory, Decoder<T> decoder) {
    int size = readVarInt();
    C collection = factory.apply(size);
    for (int iii = 0; iii < size; iii++) {
      collection.add(decoder.apply(this));
    }
    return collection;
  }

  @Override
  public <E extends Enum<E>> E readEnum(Class<E> type) {
    return type.getEnumConstants()[readVarInt()];
  }

  @Override
  public long readLong() {
    return this.buffer.getLong();
  }

  @Override
  public MessageWriter writeLong(long value) {
    ensureRemaining(8);
    this.buffer.putLong(value);
    return this;
  }

  @Override
  public UUID readUUID() {
    return new UUID(this.readLong(), this.readLong());
  }

  public MessageWriter writeUUID(UUID value) {
    this.writeLong(value.getMostSignificantBits());
    this.writeLong(value.getLeastSignificantBits());
    return this;
  }

  @Override
  public <T> Optional<T> readOptional(Decoder<T> decoder) {
    return readBool() ? Optional.of(decoder.apply(this)) : Optional.empty();
  }

  @Override
  public <T> MessageWriter writeOptional(@Nullable T value, Encoder<T> encoder) {
    if (value != null) {
      writeBool(true);
      encoder.accept(this, value);
    } else {
      writeBool(false);
    }
    return this;
  }

  @Override
  public <E extends Enum<E>> MessageWriter writeEnum(Enum<E> value) {
    writeVarInt(value.ordinal());
    return this;
  }

  @Override
  public MessageWriter writeBool(boolean value) {
    writeByte((byte) (value ? 1 : 0));
    return this;
  }

  @Override
  public boolean readBool() {
    return readByte() != 0;
  }

  @Override
  public byte readByte() {
    return this.buffer.get();
  }

  @Override
  public short readUnsignedByte() {
    return (short) (readByte() & 0xFF);
  }

  @Override
  public MessageWriter writeUnsignedByte(short value) {
    writeByte((byte) value);
    return this;
  }

  @Override
  public byte[] readByteArray() {
    return readByteArray(this.buffer.limit());
  }

  @Override
  public byte[] readByteArray(int limit) {
    int count = readVarInt();
    if (count > limit) {
      throw new OverflowException("Byte Array", count, limit);
    }
    return readBytes(count);
  }

  @Override
  public MessageReader readBytes(byte[] dst, int offset, int length) {
    this.buffer.get(dst, offset, length);
    return this;
  }

  public byte[] readBytes(int size) {
    byte[] buffer = new byte[size];
    readBytes(buffer);
    return buffer;
  }

  public void readBytes(byte[] bytes) {
    this.buffer.get(bytes);
  }

  @Override
  public MessageWriter writeByte(byte value) {
    ensureRemaining(1);
    this.buffer.put(value);
    return this;
  }

  @Override
  public MessageWriter writeByteArray(byte[] bytes) {
    ensureRemaining(varIntSize(bytes.length) + bytes.length);
    writeVarInt(bytes.length);
    writeBytes(bytes);
    return this;
  }

  @Override
  public <T> MessageWriter writeCollection(Collection<T> items, Encoder<T> encoder) {
    writeVarInt(items.size());
    items.forEach(item -> encoder.accept(this, item));
    return this;
  }

  public MessageBuffer writeBytes(byte[] bytes) {
    ensureRemaining(bytes.length);
    this.buffer.put(bytes);
    return this;
  }

  @Internal
  int remaining() {
    return this.buffer.remaining();
  }

  @Internal
  byte[] toByteArray() {
    this.buffer.flip();
    return readBytes(this.buffer.limit());
  }
}
