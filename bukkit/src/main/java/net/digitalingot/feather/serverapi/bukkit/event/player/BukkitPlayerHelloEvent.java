package net.digitalingot.feather.serverapi.bukkit.event.player;

import java.util.Collection;
import java.util.Collections;
import net.digitalingot.feather.serverapi.api.event.player.PlayerHelloEvent;
import net.digitalingot.feather.serverapi.api.model.FeatherMod;
import net.digitalingot.feather.serverapi.api.model.Platform;
import net.digitalingot.feather.serverapi.api.model.PlatformMod;
import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.digitalingot.feather.serverapi.bukkit.event.BukkitFeatherEvent;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BukkitPlayerHelloEvent extends BukkitFeatherEvent implements PlayerHelloEvent {
  private static final HandlerList HANDLER_LIST = new HandlerList();

  @NotNull private final Platform platform;
  @NotNull private final Collection<PlatformMod> platformMods;
  @NotNull private final Collection<FeatherMod> featherMods;

  public BukkitPlayerHelloEvent(
      @NotNull FeatherPlayer player,
      @NotNull Platform platform,
      @NotNull Collection<PlatformMod> platformMods,
      @NotNull Collection<FeatherMod> featherMods) {
    super(player);
    this.platform = platform;
    this.platformMods = platformMods;
    this.featherMods = featherMods;
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  @NotNull
  @Override
  public Platform getPlatform() {
    return this.platform;
  }

  @NotNull
  @Override
  public Collection<PlatformMod> getPlatformMods() {
    return Collections.unmodifiableCollection(this.platformMods);
  }

  @NotNull
  @Override
  public Collection<FeatherMod> getFeatherMods() {
    return Collections.unmodifiableCollection(this.featherMods);
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}
