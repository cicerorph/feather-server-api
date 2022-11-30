package net.digitalingot.feather.serverapi.messaging.messages.client;

import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;

public class S2CFUIResponse implements Message<ClientMessageHandler> {
  private final int id;
  private final boolean found;

  // input
  private String payload;
  // output
  private byte[] payloadAsBytes;

  public S2CFUIResponse(int id, boolean found, String payload) {
    this.id = id;
    this.found = found;
    this.payload = payload;
  }

  public S2CFUIResponse(MessageReader reader) {
    this.id = reader.readVarInt();
    this.found = reader.readBool();
    this.payloadAsBytes = reader.readByteArray();
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeVarInt(this.id);
    writer.writeBool(this.found);
    writer.writeUtf(this.payload);
  }

  @Override
  public void handle(ClientMessageHandler handler) {
    handler.handle(this);
  }

  public int getId() {
    return this.id;
  }

  public boolean isFound() {
    return this.found;
  }

  public byte[] getPayload() {
    return this.payloadAsBytes;
  }
}
