package com.project.reimburse.exceptions;

/**
 * This is a custom class for already exist exception.
 */
@SuppressWarnings("serial")
public class ResourceAlreadyExist extends RuntimeException {

  /**
 *This is a default constructor.
 */
  public ResourceAlreadyExist() {
    super();
  }

  /**
   *This is a constructor.
   *
   * @param message Message to set.
 */
  public ResourceAlreadyExist(final String message) {
    super(message);
  }
}
