package net.digitalingot.feather.serverapi.messaging.messages.server;

import java.util.Collection;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import net.digitalingot.feather.serverapi.messaging.ServerMessageHandler;
import net.digitalingot.feather.serverapi.messaging.domain.PlatformMod;
import org.jetbrains.annotations.NotNull;

public class C2SPlatformModsResponse implements Message<ServerMessageHandler> {
  @NotNull private final Collection<PlatformMod> platformMods;

  public C2SPlatformModsResponse(@NotNull Collection<PlatformMod> platformMods) {
    this.platformMods = platformMods;
  }

  public C2SPlatformModsResponse(MessageReader reader) {
    this.platformMods = reader.readList(PlatformMod.DECODER);
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeCollection(this.platformMods, PlatformMod.ENCODER);
  }

  @Override
  public void handle(ServerMessageHandler handler) {}

  @NotNull
  public Collection<PlatformMod> getPlatformMods() {
    return this.platformMods;
  }
}
