package net.digitalingot.feather.serverapi.api.ui.rpc;

import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface RpcResponse {
  void respond(@Nullable String jsonResponse);
}
