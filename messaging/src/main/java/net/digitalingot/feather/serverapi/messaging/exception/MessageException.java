package net.digitalingot.feather.serverapi.messaging.exception;

public class MessageException extends RuntimeException {

  public MessageException() {}

  public MessageException(String message) {
    super(message);
  }
}
