package net.digitalingot.feather.serverapi.messaging.messages.server;

import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import net.digitalingot.feather.serverapi.messaging.ServerMessageHandler;
import org.jetbrains.annotations.NotNull;

public class C2SFUIStateChange implements Message<ServerMessageHandler> {
  @NotNull
  private final String frame;
  @NotNull
  private final StateType type;

  public C2SFUIStateChange(@NotNull String frame, @NotNull StateType type) {
    this.frame = frame;
    this.type = type;
  }

  public C2SFUIStateChange(MessageReader reader) {
    this.frame = reader.readUtf(64);
    this.type = reader.readEnum(StateType.class);
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeUtf(this.frame);
    writer.writeEnum(this.type);
  }

  @Override
  public void handle(ServerMessageHandler handler) {
    handler.handle(this);
  }

  @NotNull
  public String getFrame() {
    return this.frame;
  }

  @NotNull
  public StateType getType() {
    return this.type;
  }

  public enum StateType {
    CREATED,
    DESTROYED,
    FOCUS_GAINED,
    FOCUS_LOST,
    VISIBLE,
    INVISIBLE,
  }
}
