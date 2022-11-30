package net.digitalingot.feather.serverapi.messaging.messages.client;

import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import org.jetbrains.annotations.NotNull;

public class S2CSetFUIState implements Message<ClientMessageHandler> {
  @NotNull private final String frame;
  @NotNull private final Action action;
  private final boolean state;

  public S2CSetFUIState(@NotNull String frame, @NotNull S2CSetFUIState.Action action, boolean state) {
    this.frame = frame;
    this.action = action;
    this.state = state;
  }

  public S2CSetFUIState(MessageReader reader) {
    this.frame = reader.readUtf();
    this.action = reader.readEnum(Action.class);
    this.state = reader.readBool();
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeUtf(this.frame);
    writer.writeEnum(this.action);
    writer.writeBool(this.state);
  }

  @Override
  public void handle(ClientMessageHandler handler) {
    handler.handle(this);
  }

  @NotNull
  public String getFrame() {
    return this.frame;
  }

  @NotNull
  public S2CSetFUIState.Action getAction() {
    return this.action;
  }

  public boolean isState() {
    return this.state;
  }

  public enum Action {
    FOCUS,
    VISIBILITY
  }
}
