package com.project.reimburse.dtos.out;

import java.util.List;
import java.util.Objects;

/**
 * Represents an extended API error response.
 */
public class ApiError {
  private List<String> messages;
  private int errorCode;
  private String additionalInfo;

  /**
  * This is a Default Constructor.
  */
  public ApiError() {
  }

  /**
  *This is a Parameterized constructor of the class.
  *
  * @param val1 The list of error messages.
  * @param val2 Error code of an api.
  * @param val3 Additional information about the error.
  */
  public ApiError(final List<String> val1, final int val2, final String val3) {
    this.messages = val1;
    this.errorCode = val2;
    this.additionalInfo = val3;
  }

  /**
  *Getter for message.
  *
  * @return messages field.
  */
  public List<String> getMessages() {
    return messages;
  }

  /**
  *Setter for message.
  *
  * @param val Message to set.
  */
  public void setMessages(final List<String> val) {
    this.messages = val;
  }

  /**
  *Getter for errorCode.
  *
  * @return errorCode field of the class.
  */
  public int getErrorCode() {
    return errorCode;
  }

  /**
  *Setter for errorCode.
  *
  * @param val value to set in errorCode.
  */
  public void setErrorCode(final int val) {
    this.errorCode = val;
  }

  /**
  *Getter for addition info.
  *
  * @return additionalInfo field.
  */
  public String getAdditionalInfo() {
    return additionalInfo;
  }

  /**
  *Setter for additional info.
  *
  * @param val Value to set.
  */
  public void setAdditionalInfo(final String val) {
    this.additionalInfo = val;
  }

  /**
  *This is a hashcode method of the class.
  */
  @Override
  public int hashCode() {
    return Objects.hash(additionalInfo, errorCode, messages);
  }

  /**
  *This is a Equals method of the class.
  *
  *@param obj Object.
  */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ApiError other = (ApiError) obj;
    return Objects.equals(additionalInfo, other.additionalInfo) && errorCode == other.errorCode
      && Objects.equals(messages, other.messages);
  }

  /**
  *This is a toString Method.
  */
  @Override
  public String toString() {
    return "ApiError [messages=" + messages + ", errorCode=" + errorCode + ","
      + " additionalInfo=" + additionalInfo
      + "]";
  }
}
