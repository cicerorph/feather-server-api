package net.digitalingot.feather.serverapi.messaging.messages.client;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;

public class S2CWaypointDestroy implements Message<ClientMessageHandler> {
  private final Collection<UUID> ids;

  public S2CWaypointDestroy(UUID id) {
    this(Collections.singletonList(id));
  }

  public S2CWaypointDestroy(Collection<UUID> ids) {
    this.ids = ids;
  }

  public S2CWaypointDestroy(MessageReader reader) {
    this.ids = reader.readList(MessageReader::readUUID);
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeCollection(ids, MessageWriter::writeUUID);
  }

  @Override
  public void handle(ClientMessageHandler handler) {
    handler.handle(this);
  }

  public Collection<UUID> getIds() {
    return this.ids;
  }
}
