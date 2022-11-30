package net.digitalingot.feather.serverapi.messaging.messages.client;

import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import org.jetbrains.annotations.NotNull;

public class S2CDestroyFUI implements Message<ClientMessageHandler> {
  @NotNull
  private final String frame;

  public S2CDestroyFUI(@NotNull String frame) {
    this.frame = frame;
  }

  public S2CDestroyFUI(MessageReader reader) {
    this.frame = reader.readUtf();
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeUtf(this.frame);
  }

  @Override
  public void handle(ClientMessageHandler handler) {
    handler.handle(this);
  }

  @NotNull
  public String getFrame() {
    return this.frame;
  }
}
