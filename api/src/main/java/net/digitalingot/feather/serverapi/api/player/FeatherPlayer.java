package net.digitalingot.feather.serverapi.api.player;

import net.digitalingot.feather.serverapi.api.model.FeatherMod;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface FeatherPlayer {

  /**
   * @return
   */
  @NotNull
  UUID getUniqueId();

  /**
   * @return
   */
  @NotNull
  String getName();

  /**
   * @param mods
   */
  void blockMods(@NotNull Collection<@NotNull FeatherMod> mods);

  /**
   * @param mods
   */
  void unblockMods(@NotNull Collection<@NotNull FeatherMod> mods);

  /**
   * @return
   */
  CompletableFuture<@NotNull Collection<@NotNull FeatherMod>> getBlockedMods();

  /**
   * @param mods
   */
  void enableMods(@NotNull Collection<@NotNull FeatherMod> mods);

  /**
   * @param mods
   */
  void disableMods(@NotNull Collection<@NotNull FeatherMod> mods);

  /**
   * @return
   */
  @NotNull
  CompletableFuture<@NotNull Collection<@NotNull FeatherMod>> getEnabledMods();
}
