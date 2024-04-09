package net.digitalingot.feather.serverapi.api.meta;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DiscordActivity {
  @Nullable private final String image;
  @Nullable private final String imageText;
  @Nullable private final String state;
  @Nullable private final String details;

  /**
   * @see DiscordActivity#builder()
   */
  private DiscordActivity(
      @Nullable String image,
      @Nullable String imageText,
      @Nullable String state,
      @Nullable String details) {
    this.image = image;
    this.imageText = imageText;
    this.state = state;
    this.details = details;
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

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    @Nullable private String image;
    @Nullable private String imageText;
    @Nullable private String state;
    @Nullable private String details;

    /**
     * @see DiscordActivity#builder()
     */
    private Builder() {}

    /**
     * Sets the URL of the image.
     *
     * @param image The URL of the image to be set, up to 127 characters.
     * @return The builder object
     */
    public Builder withImage(@NotNull String image) {
      this.image = image;
      return this;
    }

    /**
     * Sets the hover text for the image.
     *
     * @param imageText The hover text to be set, up to 127 characters.
     * @return The builder object
     */
    public Builder withImageText(@NotNull String imageText) {
      this.imageText = imageText;
      return this;
    }

    /**
     * Sets the player's current party status.
     *
     * @param state The party status to be set, up to 127 characters.
     * @return The builder object
     */
    public Builder withState(@NotNull String state) {
      this.state = state;
      return this;
    }

    /**
     * Sets what the player is currently doing.
     *
     * @param details The player's current activity, up to 127 characters.
     * @return The builder object
     */
    public Builder withDetails(@NotNull String details) {
      this.details = details;
      return this;
    }

    /**
     * Builds a new DiscordActivity after validating the lengths of the image, imageText, state, and details.
     *
     * @return A new DiscordActivity with the validated image, imageText, state, and details.
     * @throws IllegalArgumentException If the length of any parameter exceeds the maximum allowed length of 127.
     */
    public DiscordActivity build() {
      validateLength(this.image, "image");
      validateLength(this.imageText, "imageText");
      validateLength(this.state, "state");
      validateLength(this.details, "details");
      return new DiscordActivity(this.image, this.imageText, this.state, this.details);
    }

    private static void validateLength(@Nullable String value, @NotNull String context) {
      if (value != null && value.length() > 127) {
        throw new IllegalArgumentException("The length of `" + context + "` exceeds the maximum length of 127");
      }
    }
  }
}
