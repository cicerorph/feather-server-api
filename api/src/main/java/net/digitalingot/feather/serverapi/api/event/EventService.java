package net.digitalingot.feather.serverapi.api.event;

import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public interface EventService {

  /**
   * @param event
   * @param handler
   * @return
   * @param <T>
   */
  <T extends FeatherEvent> @NotNull EventSubscription<T> subscribe(
      @NotNull Class<T> event, @NotNull Consumer<? super T> handler);

  /**
   * @param plugin
   * @param event
   * @param handler
   * @return
   * @param <T>
   */
  <T extends FeatherEvent> @NotNull EventSubscription<T> subscribe(
      @NotNull Class<T> event, @NotNull Consumer<? super T> handler, @NotNull Object plugin);
}
