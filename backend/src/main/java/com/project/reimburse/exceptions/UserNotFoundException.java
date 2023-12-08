package com.project.reimburse.exceptions;

/**
 * This is a Custom class to handle user not found.
 */
@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {
  /**
     * This is a constructor to set a message to super class.
     *
     * @param message This is a custom message.
  */
  public UserNotFoundException(final String message) {
    super(message);
  }
}
