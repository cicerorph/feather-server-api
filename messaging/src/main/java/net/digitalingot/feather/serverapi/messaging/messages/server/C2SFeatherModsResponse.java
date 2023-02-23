package net.digitalingot.feather.serverapi.messaging.messages.server;

import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import net.digitalingot.feather.serverapi.messaging.ServerMessageHandler;
import net.digitalingot.feather.serverapi.messaging.domain.FeatherMod;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class C2SFeatherModsResponse implements Message<ServerMessageHandler> {
  @NotNull private final Collection<FeatherMod> featherMods;

  public C2SFeatherModsResponse(@NotNull Collection<FeatherMod> featherMods) {
    this.featherMods = featherMods;
  }

  public C2SFeatherModsResponse(MessageReader reader) {
    this.featherMods = reader.readList(FeatherMod.DECODER);
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeCollection(this.featherMods, FeatherMod.ENCODER);
  }

  @Override
  public void handle(ServerMessageHandler handler) {}

  @NotNull
  public Collection<FeatherMod> getFeatherMods() {
    return this.featherMods;
  }
}
