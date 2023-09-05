package net.digitalingot.feather.serverapi.messaging.messages.client;

import java.util.UUID;
import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;

public class S2CWorldChange implements Message<ClientMessageHandler> {

  private final UUID worldId;

  public S2CWorldChange(UUID worldId) {
    this.worldId = worldId;
  }

  public S2CWorldChange(MessageReader reader) {
    this.worldId = reader.readUUID();
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeUUID(this.worldId);
  }

  @Override
  public void handle(ClientMessageHandler handler) {
    handler.handle(this);
  }

  public UUID getWorldId() {
    return this.worldId;
  }
}
