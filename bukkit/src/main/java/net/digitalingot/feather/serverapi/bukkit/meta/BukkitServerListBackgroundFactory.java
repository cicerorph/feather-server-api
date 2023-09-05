package net.digitalingot.feather.serverapi.bukkit.meta;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.digitalingot.feather.serverapi.api.meta.ServerListBackground;
import net.digitalingot.feather.serverapi.api.meta.ServerListBackgroundFactory;

class BukkitServerListBackgroundFactory implements ServerListBackgroundFactory {

  @Override
  public ServerListBackground byPath(Path path) throws IOException {
    // TODO: add constraints; check if valid image, size constraints
    byte[] imagesBytes = Files.readAllBytes(path);
    HashCode imageHash = Hashing.sha1().hashBytes(imagesBytes);
    return new SimpleServerListBackground(imagesBytes, imageHash);
  }
}
