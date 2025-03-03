package net.digitalingot.feather.serverapi.api.meta;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Represents a Discord activity that can be displayed in the client. */
public final class DiscordActivity {
  @Nullable private final String image;
  @Nullable private final String imageText;
  @Nullable private final String state;
  @Nullable private final String details;
  @Nullable private final Integer partySize;
  @Nullable private final Integer partyMax;
  @Nullable private final Long startTimestamp;
  @Nullable private final Long endTimestamp;

  /**
   * Constructs a new DiscordActivity instance with the specified parameters. Use the {@link
   * DiscordActivity#builder()} method to create a new instance.
   *
   * @param image The URL of the image, between 2 and 127 characters.
   * @param imageText The hover text for the image, between 2 and 127 characters.
   * @param state The player's current party status, between 2 and 127 characters.
   * @param details What the player is currently doing, between 2 and 127 characters.
   * @param partySize The current size of the player's party.
   * @param partyMax The maximum size of the player's party.
   * @param startTimestamp Unix timestamp (in milliseconds) for when the activity started.
   * @param endTimestamp Unix timestamp (in milliseconds) for when the activity will end.
   */
  private DiscordActivity(
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
    this.partySize = partySize;
    this.partyMax = partyMax;
    this.startTimestamp = startTimestamp;
    this.endTimestamp = endTimestamp;
  }

  /**
   * Returns the URL of the image as an Optional.
   *
   * @return An Optional containing the image URL, or an empty Optional if no image is set.
   */
  public Optional<String> getImage() {
    return Optional.ofNullable(this.image);
  }

  /**
   * Returns the hover text for the image as an Optional.
   *
   * @return An Optional containing the image hover text, or an empty Optional if no hover text is
   *     set.
   */
  public Optional<String> getImageText() {
    return Optional.ofNullable(this.imageText);
  }

  /**
   * Returns the player's current party status as an Optional.
   *
   * @return An Optional containing the player's party status, or an empty Optional if no party
   *     status is set.
   */
  public Optional<String> getState() {
    return Optional.ofNullable(this.state);
  }

  /**
   * Returns what the player is currently doing as an Optional.
   *
   * @return An Optional containing the player's current activity, or an empty Optional if no
   *     activity is set.
   */
  public Optional<String> getDetails() {
    return Optional.ofNullable(this.details);
  }

  /**
   * Returns the current size of the player's party as an Optional.
   *
   * @return An Optional containing the party size, or an empty Optional if no party size is set.
   * @since 0.0.5
   */
  public Optional<Integer> getPartySize() {
    return Optional.ofNullable(this.partySize);
  }

  /**
   * Returns the maximum size of the player's party as an Optional.
   *
   * @return An Optional containing the maximum party size, or an empty Optional if no maximum party
   *     size is set.
   * @since 0.0.5
   */
  public Optional<Integer> getPartyMax() {
    return Optional.ofNullable(this.partyMax);
  }

  /**
   * Returns the timestamp when the activity started as an Optional.
   *
   * @return An Optional containing the start timestamp in milliseconds since epoch, or an empty
   *     Optional if no start timestamp is set.
   * @since 0.0.5
   */
  public Optional<Long> getStartTimestamp() {
    return Optional.ofNullable(this.startTimestamp);
  }

  /**
   * Returns the timestamp when the activity will end as an Optional.
   *
   * @return An Optional containing the end timestamp in milliseconds since epoch, or an empty
   *     Optional if no end timestamp is set.
   * @since 0.0.5
   */
  public Optional<Long> getEndTimestamp() {
    return Optional.ofNullable(this.endTimestamp);
  }

  /**
   * Returns a new Builder instance for creating a DiscordActivity.
   *
   * @return A new Builder instance.
   */
  public static Builder builder() {
    return new Builder();
  }

  /** A builder class for creating instances of DiscordActivity. */
  public static final class Builder {
    @Nullable private String image;
    @Nullable private String imageText;
    @Nullable private String state;
    @Nullable private String details;
    @Nullable private Integer partySize;
    @Nullable private Integer partyMax;
    @Nullable private Long startTimestamp;
    @Nullable private Long endTimestamp;

    /** Constructs a new Builder instance. */
    private Builder() {}

    /**
     * Sets the URL of the image.
     *
     * @param image The URL of the image to be set, between 2 and 127 characters.
     * @return The builder object.
     */
    public Builder withImage(@NotNull String image) {
      this.image = image;
      return this;
    }

    /**
     * Sets the hover text for the image.
     *
     * @param imageText The hover text to be set, between 2 and 127 characters.
     * @return The builder object.
     */
    public Builder withImageText(@NotNull String imageText) {
      this.imageText = imageText;
      return this;
    }

    /**
     * Sets the player's current party status.
     *
     * @param state The party status to be set, between 2 and 127 characters.
     * @return The builder object.
     */
    public Builder withState(@NotNull String state) {
      this.state = state;
      return this;
    }

    /**
     * Sets what the player is currently doing.
     *
     * @param details The player's current activity, between 2 and 127 characters.
     * @return The builder object.
     */
    public Builder withDetails(@NotNull String details) {
      this.details = details;
      return this;
    }

    /**
     * Sets the party size information.
     *
     * @param size The current number of players in the party.
     * @param max The maximum number of players allowed in the party.
     * @return The builder object.
     * @throws IllegalArgumentException If size is less than 1 or greater than max, or if max is
     *     less than 1.
     * @since 0.0.5
     */
    public Builder withPartySize(int size, int max) {
      if (size < 1) {
        throw new IllegalArgumentException("Party size must be at least 1");
      }
      if (max < 1) {
        throw new IllegalArgumentException("Party maximum size must be at least 1");
      }
      if (size > max) {
        throw new IllegalArgumentException("Party size cannot exceed party maximum size");
      }
      this.partySize = size;
      this.partyMax = max;
      return this;
    }

    /**
     * Sets the timestamp for when the activity started.
     *
     * @param timestamp The timestamp in milliseconds since epoch when the activity started.
     * @return The builder object.
     * @since 0.0.5
     */
    public Builder withStartTimestamp(long timestamp) {
      this.startTimestamp = timestamp;
      return this;
    }

    /**
     * Sets the timestamp for when the activity will end.
     *
     * @param timestamp The timestamp in milliseconds since epoch when the activity will end.
     * @return The builder object.
     * @since 0.0.5
     */
    public Builder withEndTimestamp(long timestamp) {
      this.endTimestamp = timestamp;
      return this;
    }

    /**
     * Builds a new DiscordActivity after validating all parameters.
     *
     * @return A new DiscordActivity with the validated parameters.
     * @throws IllegalArgumentException If any validation fails.
     */
    public DiscordActivity build() {
      validateLength(this.image, "image");
      validateLength(this.imageText, "imageText");
      validateLength(this.state, "state");
      validateLength(this.details, "details");

      if ((this.partySize == null && this.partyMax != null)
          || (this.partySize != null && this.partyMax == null)) {
        throw new IllegalArgumentException(
            "Both party size and party max must be specified together");
      }

      if (this.startTimestamp != null
          && this.endTimestamp != null
          && this.startTimestamp > this.endTimestamp) {
        throw new IllegalArgumentException("Start timestamp cannot be after end timestamp");
      }

      return new DiscordActivity(
          this.image,
          this.imageText,
          this.state,
          this.details,
          this.partySize,
          this.partyMax,
          this.startTimestamp,
          this.endTimestamp);
    }

    private static void validateLength(@Nullable String value, @NotNull String context) {
      if (value != null) {
        if (value.length() < 2) {
          throw new IllegalArgumentException(
              "The length of `" + context + "` is less than the minimum length of 2");
        }
        if (value.length() > 127) {
          throw new IllegalArgumentException(
              "The length of `" + context + "` exceeds the maximum length of 127");
        }
      }
    }
  }
}
