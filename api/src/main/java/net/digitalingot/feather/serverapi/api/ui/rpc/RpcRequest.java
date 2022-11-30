package net.digitalingot.feather.serverapi.api.ui.rpc;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

public interface RpcRequest {
  @NotNull
  FeatherPlayer getSource();

  @NotNull
  String getBody();
}
