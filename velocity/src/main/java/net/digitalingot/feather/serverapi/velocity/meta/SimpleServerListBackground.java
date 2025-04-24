package net.digitalingot.feather.serverapi.velocity.meta;

import net.digitalingot.feather.serverapi.api.meta.ServerListBackground;
import net.digitalingot.feather.serverapi.api.meta.format.ImageFormat;
import org.jetbrains.annotations.NotNull;

record SimpleServerListBackground(byte[] image, byte[] hash, ImageFormat imageFormat) implements
    ServerListBackground {

  @NotNull
  @Override
  public ImageFormat imageFormat() {
    return this.imageFormat;
  }
}
