package net.digitalingot.feather.serverapi.bukkit.ui.rpc;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.digitalingot.feather.serverapi.api.ui.rpc.RpcRequest;
import net.digitalingot.feather.serverapi.bukkit.player.BukkitFeatherPlayer;
import org.jetbrains.annotations.NotNull;

public class BukkitRpcRequest implements RpcRequest {
  @NotNull private final BukkitFeatherPlayer player;
  @NotNull private final String body;

  public BukkitRpcRequest(@NotNull BukkitFeatherPlayer player, @NotNull String body) {
    this.player = player;
    this.body = body;
  }

  @Override
  public @NotNull FeatherPlayer getSource() {
    return this.player;
  }

  @Override
  public @NotNull String getBody() {
    return this.body;
  }
}
