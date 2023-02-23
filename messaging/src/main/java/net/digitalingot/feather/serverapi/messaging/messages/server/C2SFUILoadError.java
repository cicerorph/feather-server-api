package net.digitalingot.feather.serverapi.messaging.messages.server;

import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import net.digitalingot.feather.serverapi.messaging.ServerMessageHandler;
import org.jetbrains.annotations.NotNull;

public class C2SFUILoadError implements Message<ServerMessageHandler> {

  @NotNull private final String frame;
  @NotNull private final String errorText;

  public C2SFUILoadError(@NotNull String frame, @NotNull String errorText) {
    this.frame = frame;
    this.errorText = errorText;
  }

  public C2SFUILoadError(MessageReader reader) {
    this.frame = reader.readUtf(64);
    this.errorText = reader.readUtf(64);
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeUtf(this.frame);
    writer.writeUtf(this.errorText);
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
  public String getErrorText() {
    return this.errorText;
  }
}
