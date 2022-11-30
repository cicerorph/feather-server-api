package net.digitalingot.feather.serverapi.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.proxy.ProxyServer;
import java.util.logging.Logger;

public class FeatherVelocityPlugin {
  private final ProxyServer server;
  private final Logger logger;

  @Inject
  public FeatherVelocityPlugin(ProxyServer server, Logger logger) {
    this.server = server;
    this.logger = logger;

    throw new UnsupportedOperationException("Not yet implemented");
  }
}
