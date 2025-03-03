package net.digitalingot.feather.serverapi.messaging.messages.client;

import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;

public class S2CServerBackground implements Message<ClientMessageHandler> {

  private final Action action;
  private final byte[] data;

  public S2CServerBackground(Action action, byte[] data) {
    this.action = action;
    this.data = data;
  }

  public S2CServerBackground(MessageReader reader) {
    this.action = reader.readEnum(Action.class);
    this.data = reader.readByteArray();
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeEnum(this.action);
    writer.writeByteArray(this.data);
  }

  @Override
  public void handle(ClientMessageHandler handler) {
    handler.handle(this);
  }

  public Action getAction() {
    return this.action;
  }

  public byte[] getData() {
    return this.data;
  }

  public enum Action {
    HASH,
    DATA
  }
}
