package net.digitalingot.feather.serverapi.messaging;

public enum MessageEncoder {
  SERVER_BOUND(Messages.SERVER_BOUND),
  CLIENT_BOUND(Messages.CLIENT_BOUND);

  private final Messages messages;

  MessageEncoder(Messages messages) {
    this.messages = messages;
  }

  public byte[] encode(Message<?> message) {
    // TODO: error handling
    int id = messages.getId(message.getClass());
    SimpleMessageBuffer buffer = new SimpleMessageBuffer();
    buffer.writeVarInt(id);
    message.write(buffer);
    return buffer.toByteArray();
  }
}
