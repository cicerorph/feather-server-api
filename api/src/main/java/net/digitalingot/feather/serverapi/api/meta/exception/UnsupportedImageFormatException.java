package net.digitalingot.feather.serverapi.api.meta.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Exception thrown when an unsupported image format is encountered.
 *
 * @since 0.0.5
 */
public class UnsupportedImageFormatException extends ServerListBackgroundException {
  @NotNull private final String formatName;

  public UnsupportedImageFormatException(@NotNull String formatName) {
    super("Unsupported image format: " + formatName);
    this.formatName = formatName;
  }

  @NotNull
  public String getFormatName() {
    return this.formatName;
  }
}
