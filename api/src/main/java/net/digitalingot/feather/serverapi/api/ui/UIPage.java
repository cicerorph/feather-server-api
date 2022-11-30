package net.digitalingot.feather.serverapi.api.ui;

import net.digitalingot.feather.serverapi.api.ui.handler.UIFocusHandler;
import net.digitalingot.feather.serverapi.api.ui.handler.UILifecycleHandler;
import net.digitalingot.feather.serverapi.api.ui.handler.UILoadHandler;
import net.digitalingot.feather.serverapi.api.ui.handler.UIVisibilityHandler;
import org.jetbrains.annotations.NotNull;

public interface UIPage {
  @NotNull
  String getPage();

  void setLifecycleHandler(@NotNull UILifecycleHandler lifecycleHandler);

  void setLoadHandler(@NotNull UILoadHandler loadHandler);

  void setVisibilityHandler(@NotNull UIVisibilityHandler visibilityHandler);

  void setFocusHandler(@NotNull UIFocusHandler focusHandler);
}
