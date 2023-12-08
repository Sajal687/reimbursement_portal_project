package com.project.reimburse.exceptions;

/**
 * This is a ApiResponseMessage Class.
 */
public class ApiResponseMessage {
  private String message;
  private boolean status;

  /**
   * This is a Parameterized constructor for assigning instance variables.
   *
   * @param val1 This is API message.
   * @param val2 This is a status of API.
   */
  public ApiResponseMessage(final String val1, final boolean val2) {
    this.message = val1;
    this.status = val2;
  }

  /**
   * Getter for message.
   *
   * @return message to get.
   */
  public String getMessage() {
    return message;
  }

  /**
  * Default Constructor of the class.
  */
  public ApiResponseMessage() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
  *Setter for message.
  *
  * @param val Message to set.
  */
  public void setMessage(final String val) {
    this.message = val;
  }

  /**
  *Getter for status.
  *
  * @return status to get.
  */
  public boolean isStatus() {
    return status;
  }

  /**
  *Setter for status.
  *
  * @param val Status to set.
  */
  public void setStatus(final boolean val) {
    this.status = val;
  }
}
