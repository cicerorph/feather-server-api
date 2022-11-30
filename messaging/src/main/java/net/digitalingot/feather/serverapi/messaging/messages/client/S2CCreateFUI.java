package net.digitalingot.feather.serverapi.messaging.messages.client;

import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import org.jetbrains.annotations.NotNull;

public class S2CCreateFUI implements Message<ClientMessageHandler> {
  @NotNull private final String frame;
  @NotNull private final String url;

  public S2CCreateFUI(@NotNull String frame, @NotNull String url) {
    this.frame = frame;
    this.url = url;
  }

  public S2CCreateFUI(MessageReader reader) {
    this.frame = reader.readUtf();
    this.url = reader.readUtf();
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeUtf(this.frame);
    writer.writeUtf(this.url);
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
  public String getUrl() {
    return this.url;
  }
}
