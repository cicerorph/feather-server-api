package net.digitalingot.feather.serverapi.bukkit.meta;

import com.google.common.hash.HashCode;
import net.digitalingot.feather.serverapi.api.meta.ServerListBackground;

class SimpleServerListBackground implements ServerListBackground {
  private final byte[] image;
  private final byte[] hash;

  public SimpleServerListBackground(byte[] image, HashCode hashCode) {
    this.image = image;
    this.hash = hashCode.asBytes();
  }

  @Override
  public byte[] getImage() {
    return this.image;
  }

  @Override
  public byte[] getHash() {
    return this.hash;
  }
}
