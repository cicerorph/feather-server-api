package net.digitalingot.feather.serverapi.messaging.messages.client;

import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import org.jetbrains.annotations.NotNull;

public class S2CFUIMessage implements Message<ClientMessageHandler> {
  @NotNull private final String frame;
  @NotNull private final String payload;

  public S2CFUIMessage(@NotNull String frame, @NotNull String payload) {
    this.frame = frame;
    this.payload = payload;
  }

  public S2CFUIMessage(MessageReader reader) {
    this.frame = reader.readUtf();
    this.payload = reader.readUtf();
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeUtf(this.frame);
    writer.writeUtf(this.payload);
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
  public String getPayload() {
    return this.payload;
  }
}
