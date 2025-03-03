package net.digitalingot.feather.serverapi.api.meta;

import net.digitalingot.feather.serverapi.api.meta.exception.ImageSizeExceededException;
import net.digitalingot.feather.serverapi.api.meta.exception.InvalidImageException;
import net.digitalingot.feather.serverapi.api.meta.exception.UnsupportedImageFormatException;
import net.digitalingot.feather.serverapi.api.meta.format.ImageFormat;
import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Services for manager meta related operations, such as server list backgrounds and Discord
 * activities.
 */
public interface MetaService {
  /**
   * Returns a factory for creating instances of {@link ServerListBackground}. It is recommended to
   * use this factory to create backgrounds as it ensures proper validation of image requirements.
   *
   * @return a {@link ServerListBackgroundFactory} instance
   */
  @NotNull
  ServerListBackgroundFactory getServerListBackgroundFactory();

  /**
   * Sets the server list background for the current server.
   *
   * <p>It is strongly recommended to create backgrounds using {@link
   * #getServerListBackgroundFactory()} to ensure proper validation of image requirements:
   *
   * <ul>
   *   <li>Format must be one of {@link ImageFormat}
   *   <li>Maximum width: 1009 pixels
   *   <li>Maximum height: 202 pixels
   *   <li>Maximum file size: 512KB
   * </ul>
   *
   * <p>These requirements will be validated when setting the background.
   *
   * <p>Note: This method performs I/O operations during image validation and should be called off
   * the main thread to avoid blocking.
   *
   * @param serverListBackground the {@link ServerListBackground} to be set
   * @throws UnsupportedImageFormatException if the image format is not supported
   * @throws ImageSizeExceededException if the image exceeds the maximum allowed dimensions or file
   *     size
   * @throws InvalidImageException if the image is invalid or corrupted
   */
  void setServerListBackground(@NotNull ServerListBackground serverListBackground)
      throws UnsupportedImageFormatException, ImageSizeExceededException, InvalidImageException;

  /**
   * Returns the current server list background, or null if none is set. e
   *
   * @return the current {@link ServerListBackground}, or null if none is set
   */
  @Nullable
  ServerListBackground getServerListBackground();

  /**
   * Updates the Discord Activity for a specific player.
   *
   * @param player The player for whom the Discord Activity is to be updated.
   * @param discordActivity The new Discord Activity to be set for the player.
   */
  void updateDiscordActivity(
      @NotNull FeatherPlayer player, @NotNull DiscordActivity discordActivity);

  /**
   * Clears the Discord Activity for a specific player.
   *
   * @param player The player for whom the Discord Activity is to be cleared.
   */
  void clearDiscordActivity(@NotNull FeatherPlayer player);
}
