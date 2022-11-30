package net.digitalingot.feather.serverapi.api.ui.handler;

import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import org.jetbrains.annotations.NotNull;

public interface UILifecycleHandler {
    void onCreated(@NotNull FeatherPlayer player);

    void onDestroyed(@NotNull FeatherPlayer player);
}
