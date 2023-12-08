package com.project.reimburse.exceptions;

/**
 * BadCredentialsException exception class.
 */
@SuppressWarnings("serial")
public class BadCredentialsException extends RuntimeException {
  /**
  * Custom BadCredentialsException constructor.
  */
  public BadCredentialsException() {
    super();
  }

  /**
  * Custom BadCredentialsException constructor with message argument.
  *
  * @param message for getting the error field.
  */
  public BadCredentialsException(final String message) {
    super(message);
  }
}
