package net.digitalingot.feather.serverapi.bukkit.event;

import java.util.function.Consumer;
import net.digitalingot.feather.serverapi.api.event.EventSubscription;
import net.digitalingot.feather.serverapi.api.event.FeatherEvent;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class BukkitEventSubscription<T extends FeatherEvent>
    implements EventSubscription<T>, Listener {
  private final Class<T> event;
  private final Consumer<? super T> handler;

  public BukkitEventSubscription(Class<T> event, Consumer<? super T> handler) {
    this.event = event;
    this.handler = handler;
  }

  @Override
  public @NotNull Class<T> getEvent() {
    return event;
  }

  @Override
  public @NotNull Consumer<? super T> getHandler() {
    return handler;
  }

  @Override
  public void unsubscribe() {
    HandlerList.unregisterAll(this);
  }
}
