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
  @Nullable private final Long partyData; // Combined partySize and partyMax
  @Nullable private final Long startTimestamp;
  @Nullable private final Long endTimestamp;

  public S2CSetDiscordActivity(
      @Nullable String image,
      @Nullable String imageText,
      @Nullable String state,
      @Nullable String details,
      @Nullable Integer partySize,
      @Nullable Integer partyMax,
      @Nullable Long startTimestamp,
      @Nullable Long endTimestamp) {
    this.image = image;
    this.imageText = imageText;
    this.state = state;
    this.details = details;
    this.partyData =
        (partySize != null && partyMax != null) ? encodePartyData(partySize, partyMax) : null;
    this.startTimestamp = startTimestamp;
    this.endTimestamp = endTimestamp;
  }

  public S2CSetDiscordActivity(MessageReader reader) {
    this.image = reader.readOptional(LIMITED_STRING_DECODER).orElse(null);
    this.imageText = reader.readOptional(LIMITED_STRING_DECODER).orElse(null);
    this.state = reader.readOptional(LIMITED_STRING_DECODER).orElse(null);
    this.details = reader.readOptional(LIMITED_STRING_DECODER).orElse(null);
    this.partyData = reader.readOptional(MessageReader::readLong).orElse(null);
    this.startTimestamp = reader.readOptional(MessageReader::readLong).orElse(null);
    this.endTimestamp = reader.readOptional(MessageReader::readLong).orElse(null);
  }

  @Override
  public void write(MessageWriter writer) {
    writer.writeOptional(this.image, LIMITED_STRING_ENCODER);
    writer.writeOptional(this.imageText, LIMITED_STRING_ENCODER);
    writer.writeOptional(this.state, LIMITED_STRING_ENCODER);
    writer.writeOptional(this.details, LIMITED_STRING_ENCODER);
    writer.writeOptional(this.partyData, MessageWriter::writeLong);
    writer.writeOptional(this.startTimestamp, MessageWriter::writeLong);
    writer.writeOptional(this.endTimestamp, MessageWriter::writeLong);
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

  public Optional<Integer> getPartySize() {
    return Optional.ofNullable(this.partyData).map(S2CSetDiscordActivity::getPartySizeFromData);
  }

  public Optional<Integer> getPartyMax() {
    return Optional.ofNullable(this.partyData).map(S2CSetDiscordActivity::getPartyMaxFromData);
  }

  public Optional<Long> getStartTimestamp() {
    return Optional.ofNullable(this.startTimestamp);
  }

  public Optional<Long> getEndTimestamp() {
    return Optional.ofNullable(this.endTimestamp);
  }

  /**
   * Encodes party size and party max into a single long value.
   *
   * @param size The current party size
   * @param max The maximum party size
   * @return A long containing both values
   */
  private static long encodePartyData(int size, int max) {
    return (((long) size) << 32) | (max & 0xFFFFFFFFL);
  }

  /**
   * Extracts the party size from the encoded party data.
   *
   * @param partyData The encoded party data
   * @return The party size
   */
  private static int getPartySizeFromData(long partyData) {
    return (int) (partyData >> 32);
  }

  /**
   * Extracts the party max from the encoded party data.
   *
   * @param partyData The encoded party data
   * @return The party max
   */
  private static int getPartyMaxFromData(long partyData) {
    return (int) partyData;
  }
}
