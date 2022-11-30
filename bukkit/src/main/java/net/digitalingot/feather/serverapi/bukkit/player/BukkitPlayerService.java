package net.digitalingot.feather.serverapi.bukkit.player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.digitalingot.feather.serverapi.api.player.PlayerService;
import net.digitalingot.feather.serverapi.bukkit.FeatherBukkitPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitPlayerService implements PlayerService, Listener {
  private final Map<UUID, BukkitFeatherPlayer> players = new HashMap<>();

  public BukkitPlayerService(FeatherBukkitPlugin plugin) {
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  public void register(BukkitFeatherPlayer player) {
    this.players.put(player.getId(), player);
  }

  @Override
  public @Nullable BukkitFeatherPlayer getPlayer(@NotNull UUID playerId) {
    return this.players.get(playerId);
  }

  @Override
  public @NotNull Collection<FeatherPlayer> getPlayers() {
    return Collections.unmodifiableCollection(this.players.values());
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerQuit(PlayerQuitEvent event) {
    this.players.remove(event.getPlayer().getUniqueId());
  }
}
