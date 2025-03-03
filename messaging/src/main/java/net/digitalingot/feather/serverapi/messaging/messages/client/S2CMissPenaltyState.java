package net.digitalingot.feather.serverapi.messaging.messages.client;

import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;

public class S2CMissPenaltyState implements Message<ClientMessageHandler> {
  private final boolean missPenalty;

  public S2CMissPenaltyState(boolean missPenalty) {
    this.missPenalty = missPenalty;
  }

  public S2CMissPenaltyState(MessageReader reader) {
    this.missPenalty = reader.readBool();
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeBool(this.missPenalty);
  }

  @Override
  public void handle(ClientMessageHandler handler) {
    handler.handle(this);
  }

  public boolean getMissPenalty() {
    return this.missPenalty;
  }
}
