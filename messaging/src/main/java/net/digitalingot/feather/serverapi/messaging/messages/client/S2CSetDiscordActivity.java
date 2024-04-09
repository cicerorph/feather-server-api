package net.digitalingot.feather.serverapi.messaging.messages.client;

import java.util.Optional;
import net.digitalingot.feather.serverapi.messaging.ClientMessageHandler;
import net.digitalingot.feather.serverapi.messaging.Message;
import net.digitalingot.feather.serverapi.messaging.MessageReader;
import net.digitalingot.feather.serverapi.messaging.MessageWriter;
import org.jetbrains.annotations.Nullable;

public class S2CSetDiscordActivity implements Message<ClientMessageHandler> {

  private static final int LIMIT = 127;

  private static final MessageWriter.Encoder<String> LIMITED_STRING_ENCODER =
      (writer, value) -> writer.writeUtf(value, LIMIT);
  private static final MessageReader.Decoder<String> LIMITED_STRING_DECODER =
      reader -> reader.readUtf(LIMIT);

  @Nullable private final String image;
  @Nullable private final String imageText;
  @Nullable private final String state;
  @Nullable private final String details;

  public S2CSetDiscordActivity(
      @Nullable String image,
      @Nullable String imageText,
      @Nullable String state,
      @Nullable String details) {
    this.image = image;
    this.imageText = imageText;
    this.state = state;
    this.details = details;
  }

  public S2CSetDiscordActivity(MessageReader reader) {
    this.image = reader.readOptional(LIMITED_STRING_DECODER).orElse(null);
    this.imageText = reader.readOptional(LIMITED_STRING_DECODER).orElse(null);
    this.state = reader.readOptional(LIMITED_STRING_DECODER).orElse(null);
    this.details = reader.readOptional(LIMITED_STRING_DECODER).orElse(null);
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeOptional(this.image, LIMITED_STRING_ENCODER);
    writer.writeOptional(this.imageText, LIMITED_STRING_ENCODER);
    writer.writeOptional(this.state, LIMITED_STRING_ENCODER);
    writer.writeOptional(this.details, LIMITED_STRING_ENCODER);
  }

  @Override
  public void handle(ClientMessageHandler handler) {
    handler.handle(this);
  }

  public Optional<String> getImage() {
    return Optional.ofNullable(this.image);
  }

  public Optional<String> getImageText() {
    return Optional.ofNullable(this.imageText);
  }

  public Optional<String> getState() {
    return Optional.ofNullable(this.state);
  }

  public Optional<String> getDetails() {
    return Optional.ofNullable(this.details);
  }
}
