package net.digitalingot.feather.serverapi.bukkit.ui;

import java.util.Objects;
import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.digitalingot.feather.serverapi.api.ui.UIPage;
import net.digitalingot.feather.serverapi.api.ui.handler.UIFocusHandler;
import net.digitalingot.feather.serverapi.api.ui.handler.UILifecycleHandler;
import net.digitalingot.feather.serverapi.api.ui.handler.UILoadHandler;
import net.digitalingot.feather.serverapi.api.ui.handler.UIVisibilityHandler;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitUIPage
    implements UIPage, UILifecycleHandler, UILoadHandler, UIFocusHandler, UIVisibilityHandler {
  @NotNull private final Plugin owner;
  @NotNull private final String url;

  @Nullable private UILifecycleHandler lifecycleHandler;
  @Nullable private UILoadHandler loadHandler;
  @Nullable private UIFocusHandler focusHandler;
  @Nullable private UIVisibilityHandler visibilityHandler;

  public BukkitUIPage(@NotNull Plugin owner, @NotNull String url) {
    this.owner = owner;
    this.url = url;
  }

  @NotNull
  public Plugin getOwner() {
    return owner;
  }

  @NotNull
  public String getRpcHostname() {
    return this.owner.getName();
  }

  @NotNull
  public String getPage() {
    return this.url;
  }

  @Override
  public void setLifecycleHandler(@NotNull UILifecycleHandler lifecycleHandler) {
    Objects.requireNonNull(lifecycleHandler);
    this.lifecycleHandler = lifecycleHandler;
  }

  @Override
  public void setLoadHandler(@NotNull UILoadHandler loadHandler) {
    Objects.requireNonNull(loadHandler);
    this.loadHandler = loadHandler;
  }

  @Override
  public void setVisibilityHandler(@NotNull UIVisibilityHandler visibilityHandler) {
    Objects.requireNonNull(visibilityHandler);
    this.visibilityHandler = visibilityHandler;
  }

  @Override
  public void setFocusHandler(@NotNull UIFocusHandler focusHandler) {
    Objects.requireNonNull(focusHandler);
    this.focusHandler = focusHandler;
  }

  @Override
  public void onCreated(@NotNull FeatherPlayer player) {
    if (this.lifecycleHandler != null) {
      this.lifecycleHandler.onCreated(player);
    }
  }

  @Override
  public void onDestroyed(@NotNull FeatherPlayer player) {
    if (this.lifecycleHandler != null) {
      this.lifecycleHandler.onDestroyed(player);
    }
  }

  @Override
  public void onLoadError(@NotNull FeatherPlayer player, @NotNull String errorText) {
    if (this.loadHandler != null) {
      this.loadHandler.onLoadError(player, errorText);
    }
  }

  @Override
  public void onFocusGained(@NotNull FeatherPlayer player) {
    if (this.focusHandler != null) {
      this.focusHandler.onFocusGained(player);
    }
  }

  @Override
  public void onFocusLost(@NotNull FeatherPlayer player) {
    if (this.focusHandler != null) {
      this.focusHandler.onFocusLost(player);
    }
  }

  @Override
  public void onShow(@NotNull FeatherPlayer player) {
    if (this.visibilityHandler != null) {
      this.visibilityHandler.onShow(player);
    }
  }

  @Override
  public void onHide(@NotNull FeatherPlayer player) {
    if (this.visibilityHandler != null) {
      this.visibilityHandler.onHide(player);
    }
  }
}
