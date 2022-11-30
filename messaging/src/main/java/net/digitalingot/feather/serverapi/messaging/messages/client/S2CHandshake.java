package net.digitalingot.feather.serverapi.messaging.messages.client;

import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageConstants;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;

public class S2CHandshake implements Message<ClientMessageHandler> {
  private final int protocolVersion;

  public S2CHandshake() {
    this.protocolVersion = MessageConstants.VERSION;
  }

  public S2CHandshake(MessageReader reader) {
    this.protocolVersion = reader.readVarInt();
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeVarInt(this.protocolVersion);
  }

  @Override
  public void handle(ClientMessageHandler handler) {}

  public int getProtocolVersion() {
    return protocolVersion;
  }
}
