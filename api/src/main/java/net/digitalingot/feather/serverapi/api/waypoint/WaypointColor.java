package net.digitalingot.feather.serverapi.api.waypoint;

import java.util.Objects;

public class WaypointColor {
  public static final WaypointColor CHROMA = new WaypointColor(0, true);

  private final int rgba;
  private final boolean chroma;

  public WaypointColor(int red, int green, int blue) {
    this(red, green, blue, 255);
  }

  public WaypointColor(float red, float green, float blue) {
    this(red, green, blue, 1.0f);
  }

  public WaypointColor(float red, float green, float blue, float alpha) {
    this(
        (int) (red * 255 + 0.5f),
        (int) (green * 255 + 0.5f),
        (int) (blue * 255 + 0.5f),
        (int) (alpha * 255 + 0.5f));
  }

  public WaypointColor(int red, int green, int blue, int alpha) {
    this(
        ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | ((blue & 0xFF)),
        false);
  }

  public WaypointColor(int rgba) {
    this(rgba, false);
  }

  private WaypointColor(int rgba, boolean chroma) {
    this.rgba = rgba;
    this.chroma = chroma;
  }

  public int getRed() {
    return ((this.rgba >> 16) & 0xFF);
  }

  public int getGreen() {
    return ((this.rgba >> 8) & 0xFF);
  }

  public int getBlue() {
    return ((this.rgba) & 0xFF);
  }

  public int getAlpha() {
    return (this.rgba >> 24 & 0xFF);
  }

  public int getRgba() {
    return this.rgba;
  }

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

  public static WaypointColor fromRgb(int rgb) {
    return fromRgba(0xFF000000 | rgb);
  }

  public static WaypointColor fromRgb(int red, int green, int blue) {
    return new WaypointColor(red, green, blue);
  }

  public static WaypointColor fromRgb(float red, float green, float blue) {
    return new WaypointColor(red, green, blue);
  }

  public static WaypointColor fromRgba(int rgba) {
    return new WaypointColor(rgba);
  }

  public static WaypointColor fromRgba(int red, int green, int blue, int alpha) {
    return new WaypointColor(red, green, blue, alpha);
  }

  public static WaypointColor fromRgba(float red, float green, float blue, float alpha) {
    return new WaypointColor(red, green, blue, alpha);
  }

  public static WaypointColor chroma() {
    return CHROMA;
  }
}
