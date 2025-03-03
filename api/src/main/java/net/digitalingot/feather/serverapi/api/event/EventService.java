package net.digitalingot.feather.serverapi.api.event;

import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public interface EventService {

  /**
   * Subscribes to the specified event type with the provided event handler.
   *
   * @param event the class representing the type of event to subscribe to
   * @param handler the handler that will be invoked when the subscribed event occurs
   * @param <T> the type of FeatherEvent being subscribed to
   * @return an EventSubscription instance representing the subscription
   */
  <T extends FeatherEvent> @NotNull EventSubscription<T> subscribe(
      @NotNull Class<T> event, @NotNull Consumer<? super T> handler);

  /**
   * Subscribes to the specified event type with the provided event handler, associated with a
   * specific plugin.
   *
   * @param event the class representing the type of event to subscribe to
   * @param handler the handler that will be invoked when the subscribed event occurs
   * @param plugin the plugin associated with this event subscription
   * @param <T> the type of FeatherEvent being subscribed to
   * @return an EventSubscription instance representing the subscription
   */
  <T extends FeatherEvent> @NotNull EventSubscription<T> subscribe(
      @NotNull Class<T> event, @NotNull Consumer<? super T> handler, @NotNull Object plugin);
}
