package net.digitalingot.feather.serverapi.bukkit.ui.rpc;

import net.digitalingot.feather.serverapi.api.ui.rpc.RpcResponse;
import net.digitalingot.feather.serverapi.bukkit.FeatherBukkitPlugin;
import net.digitalingot.feather.serverapi.bukkit.player.BukkitFeatherPlayer;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CFUIResponse;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class BukkitRpcResponse implements RpcResponse {
  private final FeatherBukkitPlugin plugin;
  public final int id;
  @NotNull public final BukkitFeatherPlayer player;

  public BukkitRpcResponse(
      int id, @NotNull BukkitFeatherPlayer player, @NotNull FeatherBukkitPlugin plugin) {
    this.id = id;
    this.player = player;
    this.plugin = plugin;
  }

  @Override
  public void respond(final @NotNull String jsonResponse) {
    sendOnMainThread(
        () -> this.player.sendMessage(new S2CFUIResponse(this.id, true, jsonResponse)));
  }

  private void sendOnMainThread(Runnable task) {
    if (!Bukkit.getServer().isPrimaryThread()) {
      Bukkit.getScheduler().runTask(this.plugin, task);
    } else {
      task.run();
    }
  }
}
