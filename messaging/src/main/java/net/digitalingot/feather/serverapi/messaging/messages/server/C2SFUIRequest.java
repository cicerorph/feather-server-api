package net.digitalingot.feather.serverapi.messaging.messages.server;

import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import net.digitalingot.feather.serverapi.messaging.ServerMessageHandler;
import org.jetbrains.annotations.NotNull;

public class C2SFUIRequest implements Message<ServerMessageHandler> {

  private final int id;
  @NotNull private final String frame;
  @NotNull private final String path;
  @NotNull private final String payload;

  public C2SFUIRequest(
      int id, @NotNull String frame, @NotNull String path, @NotNull String payload) {
    this.id = id;
    this.frame = frame;
    this.path = path;
    this.payload = payload;
  }

  public C2SFUIRequest(MessageReader reader) {
    this.id = reader.readVarInt();
    this.frame = reader.readUtf(64);
    this.path = reader.readUtf(64);
    this.payload = reader.readUtf();
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeVarInt(this.id);
    writer.writeUtf(this.frame);
    writer.writeUtf(this.path);
    writer.writeUtf(this.payload);
  }

  @Override
  public void handle(ServerMessageHandler handler) {
    handler.handle(this);
  }

  @NotNull
  public String getFrame() {
    return this.frame;
  }

  public int getId() {
    return this.id;
  }

  @NotNull
  public String getPath() {
    return this.path;
  }

  @NotNull
  public String getPayload() {
    return this.payload;
  }
}
