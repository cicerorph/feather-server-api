package net.digitalingot.feather.serverapi.api.meta;

import net.digitalingot.feather.serverapi.api.meta.format.ImageFormat;
import org.jetbrains.annotations.NotNull;

/** Represents a background image for the server listing in Feather's server list. */
public interface ServerListBackground {
  /**
   * Returns the image data for the server list background.
   *
   * @return the image data as a byte array
   */
  byte[] getImage();

  /**
   * Returns the SHA-1 hash of the image data for the server list background.
   *
   * @return the SHA-1 hash of the image data as a byte array
   */
  byte[] getHash();

  /**
   * Returns the format of the image.
   *
   * @return the image format
   */
  @NotNull
  ImageFormat getImageFormat();
}
