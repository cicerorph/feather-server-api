package net.digitalingot.feather.serverapi.messaging;

import net.digitalingot.feather.serverapi.messaging.messages.client.S2CCreateFUI;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CDestroyFUI;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CFUIMessage;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CFUIResponse;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CGetEnabledMods;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CModsAction;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CServerBackground;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CSetFUIState;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CWaypointCreate;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CWaypointDestroy;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CWorldChange;

public interface ClientMessageHandler extends MessageHandler {
  void handle(S2CCreateFUI createFUI);

  void handle(S2CDestroyFUI destroyFUI);

  void handle(S2CSetFUIState setFUIState);

  void handle(S2CFUIMessage message);

  void handle(S2CFUIResponse response);

  void handle(S2CGetEnabledMods getEnabledMods);

  void handle(S2CModsAction blockMods);

  void handle(S2CServerBackground serverBackground);

  void handle(S2CWaypointCreate waypointCreate);

  void handle(S2CWaypointDestroy waypointDestroy);

  void handle(S2CWorldChange worldChange);
}
