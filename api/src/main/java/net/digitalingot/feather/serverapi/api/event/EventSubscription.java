package net.digitalingot.feather.serverapi.api.event;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface EventSubscription<T extends FeatherEvent> {

  /**
   * @return
   */
  @NotNull
  Class<T> getEvent();

  /**
   * @return
   */
  @NotNull
  Consumer<? super T> getHandler();

  /** */
  void unsubscribe();
}
