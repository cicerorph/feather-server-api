package net.digitalingot.feather.serverapi.bukkit.meta;

import net.digitalingot.feather.serverapi.api.meta.DiscordActivity;
import net.digitalingot.feather.serverapi.api.meta.MetaService;
import net.digitalingot.feather.serverapi.api.meta.ServerListBackground;
import net.digitalingot.feather.serverapi.api.meta.ServerListBackgroundFactory;
import net.digitalingot.feather.serverapi.api.meta.exception.ImageSizeExceededException;
import net.digitalingot.feather.serverapi.api.meta.exception.InvalidImageException;
import net.digitalingot.feather.serverapi.api.meta.exception.UnsupportedImageFormatException;
import net.digitalingot.feather.serverapi.api.player.FeatherPlayer;
import net.digitalingot.feather.serverapi.bukkit.FeatherBukkitPlugin;
import net.digitalingot.feather.serverapi.bukkit.event.player.BukkitPlayerHelloEvent;
import net.digitalingot.feather.serverapi.bukkit.player.BukkitFeatherPlayer;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CClearDiscordActivity;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CServerBackground;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CServerBackground.Action;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CSetDiscordActivity;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitMetaService implements MetaService, Listener {
  private final ServerListBackgroundFactory serverListBackgroundFactory =
      new BukkitServerListBackgroundFactory();
  @Nullable private volatile ServerListBackground serverListBackground = null;

  public BukkitMetaService(@NotNull FeatherBukkitPlugin plugin) {
    Bukkit.getPluginManager().registerEvents(this, plugin);
  }

  @Override
  public synchronized void setServerListBackground(
      @NotNull ServerListBackground serverListBackground)
      throws UnsupportedImageFormatException, ImageSizeExceededException, InvalidImageException {
    ServerListBackgroundValidator.validate(serverListBackground);
    this.serverListBackground = serverListBackground;
  }

  @Override
  public synchronized @Nullable ServerListBackground getServerListBackground() {
    return this.serverListBackground;
  }

  @Override
  public void updateDiscordActivity(
      @NotNull FeatherPlayer player, @NotNull DiscordActivity discordActivity) {
    ((BukkitFeatherPlayer) player)
        .sendMessage(
            new S2CSetDiscordActivity(
                discordActivity.getImage().orElse(null),
                discordActivity.getImageText().orElse(null),
                discordActivity.getState().orElse(null),
                discordActivity.getDetails().orElse(null),
                discordActivity.getPartySize().orElse(null),
                discordActivity.getPartyMax().orElse(null),
                discordActivity.getStartTimestamp().orElse(null),
                discordActivity.getEndTimestamp().orElse(null)));
  }

  @Override
  public void clearDiscordActivity(@NotNull FeatherPlayer player) {
    ((BukkitFeatherPlayer) player).sendMessage(new S2CClearDiscordActivity());
  }

  @Override
  public @NotNull ServerListBackgroundFactory getServerListBackgroundFactory() {
    return this.serverListBackgroundFactory;
  }

  @EventHandler
  public void onFeatherPlayerHello(BukkitPlayerHelloEvent event) {
    ServerListBackground background = getServerListBackground();
    if (background != null) {
      ((BukkitFeatherPlayer) event.getPlayer())
          .sendMessage(new S2CServerBackground(Action.HASH, background.getHash()));
    }
  }
}
