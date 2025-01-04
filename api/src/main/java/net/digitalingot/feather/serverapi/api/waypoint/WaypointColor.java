package net.digitalingot.feather.serverapi.api.waypoint;

import java.util.Objects;

/**
 * Represents a color with red, green, blue, and alpha components. It also supports a special
 * "chroma" color that indicates a visual effect where colors continuously change over time.
 */
public class WaypointColor {
  private static final int ALPHA_MASK = 0xFF000000;
  private static final int RED_MASK = 0x00FF0000;
  private static final int GREEN_MASK = 0x0000FF00;
  private static final int BLUE_MASK = 0x000000FF;

  /**
   * A special "chroma" color that indicates a visual effect where colors continuously change over
   * time.
   */
  public static final WaypointColor CHROMA = new WaypointColor(0, true);

  private final int rgba;
  private final boolean chroma;

  /**
   * Creates a new WaypointColor with the given red, green, and blue components and an alpha value
   * of 255 (fully opaque).
   *
   * @param red the red component (0-255)
   * @param green the green component (0-255)
   * @param blue the blue component (0-255)
   */
  public WaypointColor(int red, int green, int blue) {
    this(red, green, blue, 255);
  }

  /**
   * Creates a new WaypointColor with the given red, green, and blue components (0.0-1.0) and an
   * alpha value of 1.0 (fully opaque).
   *
   * @param red the red component (0.0-1.0)
   * @param green the green component (0.0-1.0)
   * @param blue the blue component (0.0-1.0)
   */
  public WaypointColor(float red, float green, float blue) {
    this(red, green, blue, 1.0f);
  }

  /**
   * Creates a new WaypointColor with the given red, green, blue, and alpha components (0.0-1.0).
   *
   * @param red the red component (0.0-1.0)
   * @param green the green component (0.0-1.0)
   * @param blue the blue component (0.0-1.0)
   * @param alpha the alpha component (0.0-1.0)
   */
  public WaypointColor(float red, float green, float blue, float alpha) {
    this(
        (int) (red * 255 + 0.5f),
        (int) (green * 255 + 0.5f),
        (int) (blue * 255 + 0.5f),
        (int) (alpha * 255 + 0.5f));
  }

  /**
   * Creates a new WaypointColor with the given red, green, blue, and alpha components (0-255).
   *
   * @param red the red component (0-255)
   * @param green the green component (0-255)
   * @param blue the blue component (0-255)
   * @param alpha the alpha component (0-255)
   */
  public WaypointColor(int red, int green, int blue, int alpha) {
    this(
        ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | ((blue & 0xFF)),
        false);
  }

  /**
   * Creates a new WaypointColor with the given RGBA value and sets the chroma flag to false.
   *
   * @param rgba the RGBA value (0xAARRGGBB)
   */
  public WaypointColor(int rgba) {
    this(rgba, false);
  }

  private WaypointColor(int rgba, boolean chroma) {
    this.rgba = rgba;
    this.chroma = chroma;
  }

  /**
   * Returns the red component of the color (0-255).
   *
   * @return the red component
   */
  public int getRed() {
    return (this.rgba & RED_MASK) >> 16;
  }

  /**
   * Returns the green component of the color (0-255).
   *
   * @return the green component
   */
  public int getGreen() {
    return (this.rgba & GREEN_MASK) >> 8;
  }

  /**
   * Returns the blue component of the color (0-255).
   *
   * @return the blue component
   */
  public int getBlue() {
    return this.rgba & BLUE_MASK;
  }

  /**
   * Returns the alpha component of the color (0-255).
   *
   * @return the alpha component
   */
  public int getAlpha() {
    return (this.rgba & ALPHA_MASK) >> 24;
  }

  /**
   * Returns the RGBA value of the color (0xAARRGGBB).
   *
   * @return the RGBA value
   */
  public int getRgba() {
    return this.rgba;
  }

  /**
   * Returns whether this color is the special "chroma" color that indicates a visual effect where
   * colors continuously change over time.
   *
   * @return true if this color is the "chroma" color, false otherwise
   */
  public boolean isChroma() {
    return this.chroma;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (!(other instanceof WaypointColor)) return false;
    WaypointColor that = (WaypointColor) other;
    return this.rgba == that.rgba && this.chroma == that.chroma;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.rgba, this.chroma);
  }

  @Override
  public String toString() {
    return "WaypointColor{"
        + "red="
        + getRed()
        + ", green="
        + getGreen()
        + ", blue="
        + getBlue()
        + ", alpha="
        + getAlpha()
        + ", chroma="
        + isChroma()
        + '}';
  }

  /**
   * Creates a new WaypointColor from the given RGB value (0xRRGGBB).
   *
   * @param rgb the RGB value
   * @return a new WaypointColor
   */
  public static WaypointColor fromRgb(int rgb) {
    return fromRgba(0xFF000000 | rgb);
  }

  /**
   * Creates a new WaypointColor from the given red, green, and blue components (0-255).
   *
   * @param red the red component (0-255)
   * @param green the green component (0-255)
   * @param blue the blue component (0-255)
   * @return a new WaypointColor
   */
  public static WaypointColor fromRgb(int red, int green, int blue) {
    return new WaypointColor(red, green, blue);
  }

  /**
   * Creates a new WaypointColor from the given red, green, and blue components (0.0-1.0).
   *
   * @param red the red component (0.0-1.0)
   * @param green the green component (0.0-1.0)
   * @param blue the blue component (0.0-1.0)
   * @return a new WaypointColor
   */
  public static WaypointColor fromRgb(float red, float green, float blue) {
    return new WaypointColor(red, green, blue);
  }

  /**
   * Creates a new WaypointColor from the given RGBA value (0xAARRGGBB).
   *
   * @param rgba the RGBA value
   * @return a new WaypointColor
   */
  public static WaypointColor fromRgba(int rgba) {
    return new WaypointColor(rgba);
  }

  /**
   * Creates a new WaypointColor from the given red, green, blue, and alpha components (0-255).
   *
   * @param red the red component (0-255)
   * @param green the green component (0-255)
   * @param blue the blue component (0-255)
   * @param alpha the alpha component (0-255)
   * @return a new WaypointColor
   */
  public static WaypointColor fromRgba(int red, int green, int blue, int alpha) {
    return new WaypointColor(red, green, blue, alpha);
  }

  /**
   * Creates a new WaypointColor from the given red, green, blue, and alpha components (0.0-1.0).
   *
   * @param red the red component (0.0-1.0)
   * @param green the green component (0.0-1.0)
   * @param blue the blue component (0.0-1.0)
   * @param alpha the alpha component (0.0-1.0)
   * @return a new WaypointColor
   */
  public static WaypointColor fromRgba(float red, float green, float blue, float alpha) {
    return new WaypointColor(red, green, blue, alpha);
  }

  /**
   * Returns the special "chroma" color that indicates a visual effect where colors continuously
   * change over time.
   *
   * @return the "chroma" color
   */
  public static WaypointColor chroma() {
    return CHROMA;
  }
}
