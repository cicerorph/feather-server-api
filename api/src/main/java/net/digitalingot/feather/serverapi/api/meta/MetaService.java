package net.digitalingot.feather.serverapi.api.meta;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MetaService {
  @NotNull
  ServerListBackgroundFactory getServerListBackgroundFactory();

  void setServerListBackground(@NotNull ServerListBackground serverListBackground);

  @Nullable
  ServerListBackground getServerListBackground();

  /**
   * Updates the Discord Activity for a specific player.
   *
   * @param player The player for whom the Discord Activity is to be updated.
   * @param discordActivity The new Discord Activity to be set for the player.
   */
  void updateDiscordActivity(@NotNull FeatherPlayer player, @NotNull DiscordActivity discordActivity);

  /**
   * Clears the Discord Activity for a specific player.
   *
   * @param player The player for whom the Discord Activity is to be cleared.
   */
  void clearDiscordActivity(@NotNull FeatherPlayer player);
}
