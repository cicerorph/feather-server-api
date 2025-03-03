package net.digitalingot.feather.serverapi.api.meta.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base exception class for all server list background related exceptions.
 *
 * @since 0.0.5
 */
public abstract class ServerListBackgroundException extends Exception {
  protected ServerListBackgroundException(@NotNull String message) {
    super(message);
  }

  protected ServerListBackgroundException(@NotNull String message, @Nullable Throwable cause) {
    super(message, cause);
  }
}
