package net.digitalingot.feather.serverapi.messaging;

import net.digitalingot.feather.serverapi.messaging.messages.client.S2CCreateFUI;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CDestroyFUI;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CFUIMessage;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CFUIResponse;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CGetEnabledMods;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CModsAction;
import net.digitalingot.feather.serverapi.messaging.messages.client.S2CSetFUIState;

public interface ClientMessageHandler extends MessageHandler {
  void handle(S2CCreateFUI createFUI);

  void handle(S2CDestroyFUI destroyFUI);

  void handle(S2CSetFUIState setFUIState);

  void handle(S2CFUIMessage message);

  void handle(S2CFUIResponse response);

  void handle(S2CGetEnabledMods getEnabledMods);

  void handle(S2CModsAction blockMods);
}
