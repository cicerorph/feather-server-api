package net.digitalingot.feather.serverapi.api.meta;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MetaService {
  @NotNull
  ServerListBackgroundFactory getServerListBackgroundFactory();

  void setServerListBackground(@NotNull ServerListBackground serverListBackground);

  @Nullable
  ServerListBackground getServerListBackground();
}
