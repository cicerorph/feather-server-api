package net.digitalingot.feather.serverapi.messaging.messages.client;

import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;

public class S2CClearDiscordActivity implements Message<ClientMessageHandler> {

    public S2CClearDiscordActivity() {}

    public S2CClearDiscordActivity(MessageReader reader) {}

    @Override
    public void write(MessageWriter writer) {
    }

    @Override
    public void handle(ClientMessageHandler handler) {
        handler.handle(this);
    }
}
