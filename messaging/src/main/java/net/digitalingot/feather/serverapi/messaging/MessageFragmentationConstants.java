package net.digitalingot.feather.serverapi.messaging;

public class MessageFragmentationConstants {
  public static final int FRAGMENT_SIZE = 32766 - 1;

  public static final int MAX_FRAGMENTS = 255;

  public static final int MAX_SIZE = MAX_FRAGMENTS * FRAGMENT_SIZE;

  private MessageFragmentationConstants() {
    throw new AssertionError();
  }
}
