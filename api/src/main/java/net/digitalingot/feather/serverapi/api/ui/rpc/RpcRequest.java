package net.digitalingot.feather.serverapi.api.ui.rpc;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

/** Represents an RPC request. */
public interface RpcRequest {
  /**
   * Returns the source player who initiated the RPC request.
   *
   * @return the source player
   */
  @NotNull
  FeatherPlayer getSource();

  /**
   * Returns the body of the RPC request.
   *
   * @return the request body
   */
  @NotNull
  String getBody();
}
