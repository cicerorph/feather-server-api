package net.digitalingot.feather.serverapi.messaging;

public interface Message<T extends MessageHandler> {
  void write(MessageWriter writer);

  void handle(T handler);
}
