package com.project.reimburse.exceptions;

/**
 * Already exists exception class.
 */
@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {
  /**
  * No argument constructor.
  */
  public BadRequestException() {
    super();
  }

  /**
  * Parameterized cons.
  *
  * @param message for getting the message.
  */
  public BadRequestException(final String message) {
    super(message);
  }
}
