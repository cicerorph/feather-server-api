package net.digitalingot.feather.serverapi.api.ui;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.digitalingot.feather.serverapi.api.ui.rpc.RpcController;
import org.jetbrains.annotations.NotNull;

public interface UIService {

  UIPage registerPage(@NotNull Object plugin, @NotNull String url);

  void unregisterPage(@NotNull UIPage page);

  void createPageForPlayer(@NotNull FeatherPlayer player, @NotNull UIPage page);

  void destroyPageForPlayer(@NotNull FeatherPlayer player, @NotNull UIPage page);

  void showOverlayForPlayer(@NotNull FeatherPlayer player, @NotNull UIPage page);

  void hideOverlayFromPlayer(@NotNull FeatherPlayer player, @NotNull UIPage page);

  void openPageForPlayer(@NotNull FeatherPlayer player, @NotNull UIPage page);

  void closePageForPlayer(@NotNull FeatherPlayer player, @NotNull UIPage page);

  void sendPageMessage(
      @NotNull FeatherPlayer player, @NotNull UIPage page, @NotNull String jsonString);

  void registerCallbacks(@NotNull UIPage page, @NotNull RpcController controller);

  void unregisterCallbacksForController(@NotNull UIPage page, @NotNull RpcController controller);

  void unregisterCallbacksForPage(@NotNull UIPage page);
}
