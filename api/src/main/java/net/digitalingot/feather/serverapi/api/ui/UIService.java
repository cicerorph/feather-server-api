package net.digitalingot.feather.serverapi.api.ui;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.digitalingot.feather.serverapi.api.ui.rpc.RpcController;
import org.jetbrains.annotations.NotNull;

/** Service for managing UI pages and their interactions with players. */
public interface UIService {

  /**
   * Registers a new UI page with the specified plugin and URL.
   *
   * @param plugin the plugin registering the page
   * @param url the URL of the page
   * @return the registered UIPage instance
   */
  UIPage registerPage(@NotNull Object plugin, @NotNull String url);

  /**
   * Unregisters the specified UI page.
   *
   * @param page the page to unregister
   */
  void unregisterPage(@NotNull UIPage page);

  /**
   * Creates a new instance of the specified UI page for the given player.
   *
   * @param player the player to create the page for
   * @param page the page to create
   */
  void createPageForPlayer(@NotNull FeatherPlayer player, @NotNull UIPage page);

  /**
   * Destroys the instance of the specified UI page for the given player.
   *
   * @param player the player to destroy the page for
   * @param page the page to destroy
   */
  void destroyPageForPlayer(@NotNull FeatherPlayer player, @NotNull UIPage page);

  /**
   * Shows the specified UI page overlay for the given player.
   *
   * @param player the player to show the overlay for
   * @param page the page to show
   */
  void showOverlayForPlayer(@NotNull FeatherPlayer player, @NotNull UIPage page);

  /**
   * Hides the specified UI page overlay from the given player.
   *
   * @param player the player to hide the overlay from
   * @param page the page to hide
   */
  void hideOverlayFromPlayer(@NotNull FeatherPlayer player, @NotNull UIPage page);

  /**
   * Opens the specified UI page for the given player, giving it focus.
   *
   * @param player the player to open the page for
   * @param page the page to open
   */
  void openPageForPlayer(@NotNull FeatherPlayer player, @NotNull UIPage page);

  /**
   * Closes the specified UI page for the given player, removing its focus.
   *
   * @param player the player to close the page for
   * @param page the page to close
   */
  void closePageForPlayer(@NotNull FeatherPlayer player, @NotNull UIPage page);

  /**
   * Sends a JSON string message to the specified UI page for the given player.
   *
   * @param player the player to send the message to
   * @param page the page to send the message to
   * @param jsonString the JSON string message to send
   */
  void sendPageMessage(
      @NotNull FeatherPlayer player, @NotNull UIPage page, @NotNull String jsonString);

  /**
   * Registers the specified RPC controller with the given UI page to handle callbacks.
   *
   * @param page the page to register the controller for
   * @param controller the controller to register
   */
  void registerCallbacks(@NotNull UIPage page, @NotNull RpcController controller);

  /**
   * Unregisters the specified RPC controller from the given UI page.
   *
   * @param page the page to unregister the controller from
   * @param controller the controller to unregister
   */
  void unregisterCallbacksForController(@NotNull UIPage page, @NotNull RpcController controller);

  /**
   * Unregisters all RPC controllers from the specified UI page.
   *
   * @param page the page to unregister all controllers from
   */
  void unregisterCallbacksForPage(@NotNull UIPage page);
}
