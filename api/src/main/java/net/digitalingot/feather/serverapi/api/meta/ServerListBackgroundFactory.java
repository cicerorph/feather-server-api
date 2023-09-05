package net.digitalingot.feather.serverapi.api.meta;

import java.io.IOException;
import java.nio.file.Path;

public interface ServerListBackgroundFactory {
  ServerListBackground byPath(Path path) throws IOException;
}
