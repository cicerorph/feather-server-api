package net.digitalingot.feather.serverapi.api.player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public interface PlayerService {

  /**
   * @param playerId
   * @return
   */
  @Nullable
  FeatherPlayer getPlayer(@NotNull UUID playerId);

  /**
   * @return
   */
  @NotNull
  Collection<FeatherPlayer> getPlayers();
}
