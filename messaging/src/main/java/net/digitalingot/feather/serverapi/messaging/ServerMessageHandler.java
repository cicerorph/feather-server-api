package net.digitalingot.feather.serverapi.messaging;

import net.digitalingot.feather.serverapi.messaging.messages.server.C2SEnabledMods;
import net.digitalingot.feather.serverapi.messaging.messages.server.C2SFUILoadError;
import net.digitalingot.feather.serverapi.messaging.messages.server.C2SFUIRequest;
import net.digitalingot.feather.serverapi.messaging.messages.server.C2SFUIStateChange;
import net.digitalingot.feather.serverapi.messaging.messages.server.C2SRequestServerBackground;

public interface ServerMessageHandler extends MessageHandler {
  void handle(C2SFUIRequest request);

  void handle(C2SFUIStateChange stateChange);

  void handle(C2SFUILoadError loadError);

  void handle(C2SEnabledMods enabledMods);

  void handle(C2SRequestServerBackground serverBackground);
}
