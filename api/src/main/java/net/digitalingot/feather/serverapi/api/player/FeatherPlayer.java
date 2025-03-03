package net.digitalingot.feather.serverapi.api.player;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.digitalingot.feather.serverapi.api.model.FeatherMod;
import org.jetbrains.annotations.NotNull;

/** Represents a player using Feather */
public interface FeatherPlayer {

  /**
   * Returns the player's Minecraft UUID.
   *
   * @return The UUID of the player.
   */
  @NotNull
  UUID getUniqueId();

  /**
   * Returns the name of the player.
   *
   * @return The name of the player.
   */
  @NotNull
  String getName();

  /**
   * Blocks and disables the specified mods for the player, preventing them from being enabled.
   * Blocked mods cannot be enabled by the player or through the {@link #enableMods(Collection)}
   * method.
   *
   * @param mods The collection of {@link FeatherMod} instances to be blocked.
   */
  void blockMods(@NotNull Collection<@NotNull FeatherMod> mods);

  /**
   * Unblocks the specified mods for the player.
   *
   * @param mods The collection of mods to be unblocked.
   */
  void unblockMods(@NotNull Collection<@NotNull FeatherMod> mods);

  /**
   * Returns the collection of mods that are currently blocked for the player.
   *
   * @return A CompletableFuture that resolves to a collection of blocked mods.
   */
  CompletableFuture<@NotNull Collection<@NotNull FeatherMod>> getBlockedMods();

  /**
   * Enables the specified mods for the player.
   *
   * @param mods The collection of mods to be enabled.
   */
  void enableMods(@NotNull Collection<@NotNull FeatherMod> mods);

  /**
   * Disables the specified mods for the player.
   *
   * @param mods The collection of mods to be disabled.
   */
  void disableMods(@NotNull Collection<@NotNull FeatherMod> mods);

  /**
   * Requests a list of enabled mods from the player.
   *
   * @return A {@link CompletableFuture} that resolves to a collection of enabled mods.
   */
  @NotNull
  CompletableFuture<@NotNull Collection<@NotNull FeatherMod>> getEnabledMods();

  /**
   * Sets whether to bypass the miss penalty feature.
   *
   * <p>In vanilla Minecraft, miss penalty is enabled by default in survival mode, preventing
   * players from attacking for a short duration after missing. This setting determines whether
   * players can attack immediately after missing or must wait for the vanilla cooldown period.
   *
   * @param bypass true to bypass the miss penalty, false to use vanilla cooldown behavior
   * @since 0.0.5
   */
  void bypassMissPenalty(boolean bypass);
}
