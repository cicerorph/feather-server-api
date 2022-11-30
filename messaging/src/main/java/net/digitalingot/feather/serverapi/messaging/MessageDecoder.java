package net.digitalingot.feather.serverapi.messaging;

public class MessageDecoder<T extends MessageHandler> {
  public static final MessageDecoder<ServerMessageHandler> SERVER_BOUND =
      new MessageDecoder<>(Messages.SERVER_BOUND);
  public static final MessageDecoder<ClientMessageHandler> CLIENT_BOUND =
      new MessageDecoder<>(Messages.CLIENT_BOUND);

  private final Messages messages;

  private MessageDecoder(Messages messages) {
    this.messages = messages;
  }

  public Message<T> decode(byte[] message) {
    SimpleMessageBuffer buffer = SimpleMessageBuffer.of(message);
    int messageId = buffer.readVarInt();
    // TODO: error handling
    return (Message<T>) messages.createMessage(messageId, buffer);
  }
}
