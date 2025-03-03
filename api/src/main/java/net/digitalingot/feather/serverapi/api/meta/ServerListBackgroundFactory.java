package net.digitalingot.feather.serverapi.api.meta;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import net.digitalingot.feather.serverapi.api.meta.exception.ImageSizeExceededException;
import net.digitalingot.feather.serverapi.api.meta.exception.InvalidImageException;
import net.digitalingot.feather.serverapi.api.meta.exception.UnsupportedImageFormatException;
import net.digitalingot.feather.serverapi.api.meta.format.ImageFormat;
import org.jetbrains.annotations.NotNull;

/** A factory for creating {@link ServerListBackground}. */
public interface ServerListBackgroundFactory {

  /**
   * Creates a {@link ServerListBackground} instance from the image file located at the specified
   * path. The image must meet the following requirements:
   *
   * <ul>
   *   <li>Format must be one of {@link ImageFormat}
   *   <li>Maximum width: 1009 pixels
   *   <li>Maximum height: 202 pixels
   *   <li>Maximum file size: 512KB
   * </ul>
   *
   * <p>Note: This method performs file I/O operations and should be called off the main thread to
   * avoid blocking.
   *
   * @param path the path to the image file
   * @return a {@link ServerListBackground} instance representing the image
   * @throws IOException if an I/O error occurs while reading the image file
   * @throws UnsupportedImageFormatException if the image format is not supported (see {@link
   *     ImageFormat})
   * @throws ImageSizeExceededException if the image exceeds the maximum allowed dimensions or file
   *     size
   * @throws InvalidImageException if the image file cannot be loaded or is corrupted
   */
  @NotNull
  ServerListBackground byPath(@NotNull Path path)
      throws IOException,
          UnsupportedImageFormatException,
          ImageSizeExceededException,
          InvalidImageException;

  /**
   * Creates a {@link ServerListBackground} instance from the provided image bytes with explicit
   * format. The image must meet the following requirements:
   *
   * <ul>
   *   <li>Maximum width: 1009 pixels
   *   <li>Maximum height: 202 pixels
   *   <li>Maximum file size: 512KB
   * </ul>
   *
   * @param bytes the image data as a byte array
   * @param format the format of the image data
   * @return a {@link ServerListBackground} instance representing the image
   * @throws UnsupportedImageFormatException if the specified format is not supported
   * @throws ImageSizeExceededException if the image exceeds the maximum allowed dimensions or file
   *     size
   * @throws InvalidImageException if the image data cannot be loaded or is corrupted
   * @since 0.0.5
   */
  @NotNull
  ServerListBackground fromBytes(byte[] bytes, @NotNull ImageFormat format)
      throws UnsupportedImageFormatException, ImageSizeExceededException, InvalidImageException;

  /**
   * Creates a {@link ServerListBackground} instance from the provided image bytes, automatically
   * detecting the image format. The image must meet the following requirements:
   *
   * <ul>
   *   <li>Format must be one of {@link ImageFormat}
   *   <li>Maximum width: 1009 pixels
   *   <li>Maximum height: 202 pixels
   *   <li>Maximum file size: 512KB
   * </ul>
   *
   * @param bytes the image data as a byte array
   * @return a {@link ServerListBackground} instance representing the image
   * @throws UnsupportedImageFormatException if the detected format is not supported (see {@link
   *     ImageFormat})
   * @throws ImageSizeExceededException if the image exceeds the maximum allowed dimensions or file
   *     size
   * @throws InvalidImageException if the image data cannot be loaded or is corrupted
   * @since 0.0.5
   */
  @NotNull
  default ServerListBackground fromBytes(byte[] bytes)
      throws UnsupportedImageFormatException, ImageSizeExceededException, InvalidImageException {
    throw new UnsupportedOperationException("Format detection not implemented");
  }

  /**
   * Returns a set of supported image formats.
   *
   * @return a {@link Set} containing all supported {@link ImageFormat}s
   * @since 0.0.5
   */
  @NotNull
  Set<ImageFormat> getSupportedFormats();
}
