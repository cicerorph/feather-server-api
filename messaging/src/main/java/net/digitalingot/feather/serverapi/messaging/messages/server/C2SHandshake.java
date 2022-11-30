package net.digitalingot.feather.serverapi.messaging.messages.server;

import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageConstants;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import net.digitalingot.feather.serverapi.messaging.ServerMessageHandler;

public class C2SHandshake implements Message<ServerMessageHandler> {
  private final int protocolVersion;

  public C2SHandshake() {
    this.protocolVersion = MessageConstants.VERSION;
  }

  public C2SHandshake(MessageReader reader) {
    this.protocolVersion = reader.readVarInt();
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeVarInt(this.protocolVersion);
  }

  @Override
  public void handle(ServerMessageHandler handler) {}

  public int getProtocolVersion() {
    return protocolVersion;
  }
}
