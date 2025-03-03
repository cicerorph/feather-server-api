package net.digitalingot.feather.serverapi.api.ui;

import net.digitalingot.feather.serverapi.api.ui.handler.UIFocusHandler;
import net.digitalingot.feather.serverapi.api.ui.handler.UILifecycleHandler;
import net.digitalingot.feather.serverapi.api.ui.handler.UILoadHandler;
import net.digitalingot.feather.serverapi.api.ui.handler.UIVisibilityHandler;
import org.jetbrains.annotations.NotNull;

/** Represents a UI page that can be displayed as an overlay. */
public interface UIPage {

  /**
   * Returns the URL of the page.
   *
   * @return the URL of the page
   */
  @NotNull
  String getPage();

  /**
   * Sets the lifecycle handler for this page.
   *
   * @param lifecycleHandler the lifecycle handler to set
   */
  void setLifecycleHandler(@NotNull UILifecycleHandler lifecycleHandler);

  /**
   * Sets the load handler for this page.
   *
   * @param loadHandler the load handler to set
   */
  void setLoadHandler(@NotNull UILoadHandler loadHandler);

  /**
   * Sets the visibility handler for this page.
   *
   * @param visibilityHandler the visibility handler to set
   */
  void setVisibilityHandler(@NotNull UIVisibilityHandler visibilityHandler);

  /**
   * Sets the focus handler for this page.
   *
   * @param focusHandler the focus handler to set
   */
  void setFocusHandler(@NotNull UIFocusHandler focusHandler);
}
