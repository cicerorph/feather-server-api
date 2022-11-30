package net.digitalingot.feather.serverapi.messaging.exception;

public class OverflowException extends MessageException {

  public OverflowException(String message) {
    super(message);
  }

  public OverflowException(String type, int size, int limit) {
    this(type + " exceeds limit (" + size + " > " + limit + ")");
  }
}
