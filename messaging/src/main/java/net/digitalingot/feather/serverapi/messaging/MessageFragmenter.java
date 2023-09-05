package net.digitalingot.feather.serverapi.messaging;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public enum MessageFragmenter {
  SERVER_BOUND(MessageEncoder.SERVER_BOUND),
  CLIENT_BOUND(MessageEncoder.CLIENT_BOUND);

  private final MessageEncoder encoder;

  MessageFragmenter(MessageEncoder encoder) {
    this.encoder = encoder;
  }

  public List<byte[]> fragment(@NotNull Message<?> message) {
    byte[] data = this.encoder.encode(message);

    int fragmentSize = MessageFragmentationConstants.FRAGMENT_SIZE;
    int fragments = (int) Math.ceil((double) data.length / fragmentSize);
    if (fragments > MessageFragmentationConstants.MAX_FRAGMENTS) {
      throw new IllegalArgumentException("Exceeds size");
    }

    List<byte[]> buffers = new ArrayList<>(fragments + 1);

    SimpleMessageBuffer header = new SimpleMessageBuffer();
    header.writeUnsignedByte((short) fragments);
    header.writeVarInt(data.length);
    buffers.add(header.toByteArray());

    for (int iii = 0; iii < fragments; ++iii) {
      int offset = (iii * fragmentSize);
      int length = Math.min(fragmentSize, data.length - offset);
      byte[] buffer = new byte[length + 1];
      buffer[0] = (byte) iii;
      System.arraycopy(data, offset, buffer, 1, length);
      buffers.add(buffer);
    }

    return buffers;
  }
}
