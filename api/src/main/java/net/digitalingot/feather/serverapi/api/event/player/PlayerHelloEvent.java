package net.digitalingot.feather.serverapi.api.event.player;

import net.digitalingot.feather.serverapi.api.event.FeatherEvent;
import net.digitalingot.feather.serverapi.api.model.FeatherMod;
import net.digitalingot.feather.serverapi.api.model.Platform;
import net.digitalingot.feather.serverapi.api.model.PlatformMod;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface PlayerHelloEvent extends FeatherEvent {
  @NotNull
  Platform getPlatform();

  @NotNull
  Collection<PlatformMod> getPlatformMods();

  @NotNull
  Collection<FeatherMod> getFeatherMods();
}
