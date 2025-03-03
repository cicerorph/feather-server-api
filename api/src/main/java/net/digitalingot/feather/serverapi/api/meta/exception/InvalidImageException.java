package net.digitalingot.feather.serverapi.api.meta.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Exception thrown when an image file cannot be loaded or is corrupted.
 *
 * @since 0.0.5
 */
public class InvalidImageException extends ServerListBackgroundException {
  public InvalidImageException(@NotNull String message) {
    super(message);
  }

  public InvalidImageException(@NotNull String message, @Nullable Throwable cause) {
    super(message, cause);
  }
}
