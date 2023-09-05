package net.digitalingot.feather.serverapi.messaging.messages.client;

import java.util.Optional;
import java.util.UUID;
import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import org.jetbrains.annotations.Nullable;

public class S2CWaypointCreate implements Message<ClientMessageHandler> {

  private final UUID id;
  private final UUID worldId;
  private final int posX;
  private final int posY;
  private final int posZ;
  private final int color;
  @Nullable private final String name;
  @Nullable private final Integer duration;

  public S2CWaypointCreate(UUID id, UUID worldId, int posX, int posY, int posZ, int color) {
    this(id, worldId, posX, posY, posZ, color, null);
  }

  public S2CWaypointCreate(
      UUID id, UUID worldId, int posX, int posY, int posZ, int color, String name) {
    this(id, worldId, posX, posY, posZ, color, name, null);
  }

  public S2CWaypointCreate(
      UUID id,
      UUID worldId,
      int posX,
      int posY,
      int posZ,
      int color,
      @Nullable String name,
      @Nullable Integer duration) {
    this.id = id;
    this.worldId = worldId;
    this.posX = posX;
    this.posY = posY;
    this.posZ = posZ;
    this.color = color;
    this.name = name;
    this.duration = duration != null && duration > 0 ? duration : null;
  }

  public S2CWaypointCreate(MessageReader reader) {
    this.id = reader.readUUID();
    this.worldId = reader.readUUID();
    this.posX = reader.readVarInt();
    this.posY = reader.readVarInt();
    this.posZ = reader.readVarInt();
    this.color = reader.readInt();
    this.name = reader.readOptional(MessageReader::readUtf).orElse(null);
    this.duration = reader.readOptional(MessageReader::readVarInt).orElse(null);
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeUUID(this.id);
    writer.writeUUID(this.worldId);
    writer.writeVarInt(this.posX);
    writer.writeVarInt(this.posY);
    writer.writeVarInt(this.posZ);
    writer.writeInt(this.color);
    writer.writeOptional(this.name, MessageWriter::writeUtf);
    writer.writeOptional(this.duration, MessageWriter::writeVarInt);
  }

  @Override
  public void handle(ClientMessageHandler handler) {
    handler.handle(this);
  }

  public UUID getId() {
    return this.id;
  }

  public UUID getWorldId() {
    return this.worldId;
  }

  public int getPosX() {
    return this.posX;
  }

  public int getPosY() {
    return this.posY;
  }

  public int getPosZ() {
    return this.posZ;
  }

  public int getColor() {
    return this.color;
  }

  public Optional<String> getName() {
    return Optional.ofNullable(this.name);
  }

  public Optional<Integer> getDuration() {
    return Optional.ofNullable(this.duration);
  }
}
