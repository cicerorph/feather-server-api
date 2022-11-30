package net.digitalingot.feather.serverapi.api.player;

import java.util.Collection;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
