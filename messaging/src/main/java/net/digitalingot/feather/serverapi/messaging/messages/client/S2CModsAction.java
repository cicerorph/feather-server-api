package net.digitalingot.feather.serverapi.messaging.messages.client;

import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import net.digitalingot.feather.serverapi.messaging.domain.FeatherMod;

import java.util.Collection;

public class S2CModsAction implements Message<ClientMessageHandler> {
  private final Action action;
  private final Collection<FeatherMod> mods;

  public S2CModsAction(Action action, Collection<FeatherMod> mods) {
    this.action = action;
    this.mods = mods;
  }

  public S2CModsAction(MessageReader reader) {
    this.action = reader.readEnum(Action.class);
    this.mods = reader.readList(FeatherMod.DECODER);
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeEnum(this.action);
    writer.writeCollection(this.mods, FeatherMod.ENCODER);
  }

  @Override
  public void handle(ClientMessageHandler handler) {
    handler.handle(this);
  }

  public Action getAction() {
    return this.action;
  }

  public Collection<FeatherMod> getMods() {
    return this.mods;
  }

  public enum Action {
    BLOCK,
    UNBLOCK,
    ENABLE,
    DISABLE,
  }
}
