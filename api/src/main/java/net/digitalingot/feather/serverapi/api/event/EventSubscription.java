package net.digitalingot.feather.serverapi.api.event;

import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

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
