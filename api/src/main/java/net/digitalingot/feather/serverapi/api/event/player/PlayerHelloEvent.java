package net.digitalingot.feather.serverapi.api.event.player;

import java.util.Collection;
import net.digitalingot.feather.serverapi.api.event.FeatherEvent;
import net.digitalingot.feather.serverapi.api.model.FeatherMod;
import net.digitalingot.feather.serverapi.api.model.Platform;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an event triggered when a player joins the Minecraft server with Feather. This event
 * contains information about the mod launcher used to launch Feather and which mods the player has
 * enabled.
 */
public interface PlayerHelloEvent extends FeatherEvent {

  /**
   * Retrieves the modding platform on which Feather is running.
   *
   * @return the modding platform
   */
  @NotNull
  Platform getPlatform();

  /**
   * Retrieves a collection of Feather mods that are enabled for the player.
   *
   * @return a collection of enabled Feather mods
   */
  @NotNull
  Collection<FeatherMod> getFeatherMods();
}
