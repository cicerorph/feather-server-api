package net.digitalingot.feather.serverapi.api.ui.rpc;

import org.jetbrains.annotations.Nullable;

/** Represent a response to an RPC request. */
@FunctionalInterface
public interface RpcResponse {
  /**
   * Responds to the RPC request with the provided JSON data.
   *
   * @param jsonResponse the JSON string response, use an empty string ("") if no data is needed
   */
  void respond(@Nullable String jsonResponse);
}
