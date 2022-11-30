package net.digitalingot.feather.serverapi.bukkit.ui.rpc;

import net.digitalingot.feather.serverapi.api.ui.rpc.RpcRequest;
import net.digitalingot.feather.serverapi.api.ui.rpc.RpcResponse;

interface RpcHandlerExecutor {
  void invoke(RpcRequest request, RpcResponse response);
}
