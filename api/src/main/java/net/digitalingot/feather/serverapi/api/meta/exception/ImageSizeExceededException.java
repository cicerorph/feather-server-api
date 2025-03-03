package net.digitalingot.feather.serverapi.api.meta.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Exception thrown when an image exceeds the maximum allowed dimensions or file size.
 *
 * @since 0.0.5
 */
public class ImageSizeExceededException extends ServerListBackgroundException {
  @NotNull private final Type type;
  private final long actual;
  private final long maximum;

  public ImageSizeExceededException(@NotNull Type type, long actual, long maximum) {
    super(
        String.format(
            "Image %s exceeds maximum allowed: %d > %d", type.getMessage(), actual, maximum));
    this.type = type;
    this.actual = actual;
    this.maximum = maximum;
  }

  @NotNull
  public Type getType() {
    return this.type;
  }

  public long getActual() {
    return this.actual;
  }

  public long getMaximum() {
    return this.maximum;
  }

  public enum Type {
    WIDTH("width"),
    HEIGHT("height"),
    FILE_SIZE("file size");

    @NotNull private final String message;

    Type(@NotNull String message) {
      this.message = message;
    }

    @NotNull
    public String getMessage() {
      return this.message;
    }
  }
}
