package net.digitalingot.feather.serverapi.api.event;

import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a subscription to a FeatherEvent.
 *
 * @param <T> the type of FeatherEvent being subscribed to
 */
public interface EventSubscription<T extends FeatherEvent> {

  /**
   * Returns the class representing the type of FeatherEvent being subscribed to.
   *
   * @return the class representing the type of FeatherEvent
   */
  @NotNull
  Class<T> getEvent();

  /**
   * Returns the handler that will be called when the subscribed event occurs.
   *
   * @return the handler for the subscribed event
   */
  @NotNull
  Consumer<? super T> getHandler();

  /** Unsubscribes from the event, effectively canceling the subscription. */
  void unsubscribe();
}
