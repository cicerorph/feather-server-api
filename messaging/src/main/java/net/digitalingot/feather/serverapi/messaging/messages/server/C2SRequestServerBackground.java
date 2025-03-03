package net.digitalingot.feather.serverapi.messaging.messages.server;

import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import net.digitalingot.feather.serverapi.messaging.ServerMessageHandler;

public class C2SRequestServerBackground implements Message<ServerMessageHandler> {

  public C2SRequestServerBackground() {}

  public C2SRequestServerBackground(MessageReader reader) {}

  @Override
  public void write(MessageWriter writer) {}

  @Override
  public void handle(ServerMessageHandler handler) {
    handler.handle(this);
  }
}
