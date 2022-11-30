package net.digitalingot.feather.serverapi.messaging.messages.server;

import java.util.Collection;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import net.digitalingot.feather.serverapi.messaging.ServerMessageHandler;
import net.digitalingot.feather.serverapi.messaging.domain.FeatherMod;
import net.digitalingot.feather.serverapi.messaging.domain.Platform;
import net.digitalingot.feather.serverapi.messaging.domain.PlatformMod;

public class C2SClientHello implements Message<ServerMessageHandler> {
  private final Platform platform;
  private final Collection<PlatformMod> platformMods;
  private final Collection<FeatherMod> featherMods;

  public C2SClientHello(
      Platform platform, Collection<PlatformMod> platformMods, Collection<FeatherMod> featherMods) {
    this.platform = platform;
    this.platformMods = platformMods;
    this.featherMods = featherMods;
  }

  public C2SClientHello(MessageReader reader) {
    this.platform = reader.readEnum(Platform.class);
    this.platformMods = reader.readList(PlatformMod.DECODER);
    this.featherMods = reader.readList(FeatherMod.DECODER);
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeEnum(this.platform);
    writer.writeCollection(this.platformMods, PlatformMod.ENCODER);
    writer.writeCollection(this.featherMods, FeatherMod.ENCODER);
  }

  @Override
  public void handle(ServerMessageHandler handler) {}

  public Platform getPlatform() {
    return this.platform;
  }

  public Collection<PlatformMod> getPlatformMods() {
    return this.platformMods;
  }

  public Collection<FeatherMod> getFeatherMods() {
    return this.featherMods;
  }
}
