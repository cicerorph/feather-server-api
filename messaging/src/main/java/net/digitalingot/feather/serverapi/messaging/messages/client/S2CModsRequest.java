package net.digitalingot.feather.serverapi.messaging.messages.client;

import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;

public class S2CModsRequest implements Message<ClientMessageHandler> {
  private final ModsType type;

  public S2CModsRequest(ModsType type) {
    this.type = type;
  }

  public S2CModsRequest(MessageReader reader) {
    this.type = reader.readEnum(ModsType.class);
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeEnum(this.type);
  }

  @Override
  public void handle(ClientMessageHandler handler) {}

  public ModsType getType() {
    return this.type;
  }

  public enum ModsType {
    PLATFORM,
    FEATHER
  }
}
