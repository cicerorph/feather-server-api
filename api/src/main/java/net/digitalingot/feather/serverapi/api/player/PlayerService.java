package net.digitalingot.feather.serverapi.api.player;

import java.util.Collection;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PlayerService {

  /**
   * Get an online Feather player by the player's UUID
   *
   * @param playerId The UUID of the player to retrieve.
   * @return The Feather player associated with the UUID, or null if no player is found.
   */
  @Nullable
  FeatherPlayer getPlayer(@NotNull UUID playerId);

  /**
   * Returns a collection of all online Feather players.
   *
   * @return A collection of all online FeatherPlayer's.
   */
  @NotNull
  Collection<FeatherPlayer> getPlayers();
}
