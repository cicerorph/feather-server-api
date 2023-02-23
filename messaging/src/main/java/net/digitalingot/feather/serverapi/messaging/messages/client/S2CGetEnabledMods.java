package net.digitalingot.feather.serverapi.messaging.messages.client;

import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;

public class S2CGetEnabledMods implements Message<ClientMessageHandler> {
  private final int id;

  public S2CGetEnabledMods(int id) {
    this.id = id;
  }

  public S2CGetEnabledMods(MessageReader reader) {
    this.id = reader.readVarInt();
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeVarInt(this.id);
  }

  @Override
  public void handle(ClientMessageHandler handler) {
    handler.handle(this);
  }

  public int getId() {
    return this.id;
  }
}
