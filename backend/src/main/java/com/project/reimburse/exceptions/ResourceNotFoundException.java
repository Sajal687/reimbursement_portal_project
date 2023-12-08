package com.project.reimburse.exceptions;

/**
 * This is a Exception Handling class for ResourceNotFound.
 */
@SuppressWarnings("serial")

public class ResourceNotFoundException extends RuntimeException {
  private String resourceName;
  private String fieldName;
  private long fieldValue;


  /**
   * This is a constructor method to assign value the instance variables.
   *
   * @param val1 This is a Class Name where the error Occurs.
   * @param val2 This is a value where error occur.
   * @param val3 This is a attribute value.
  */
  public ResourceNotFoundException(final String val1,
      final String val2, final long val3) {
    super(String.format("%s is not found with %s : %s", val1, val2, val3));
    this.resourceName = val1;
    this.fieldName = val2;
    this.fieldValue = val3;
  }


  /**
 *This is a getter for resource name.
 *
 * @return resource name to get.
 */
  public String getResourceName() {
    return resourceName;
  }


  /**
 *Setter for resource name.
 *
 * @param val resourceName to set.
 */
  public void setResourceName(final String val) {
    this.resourceName = val;
  }


  /**
 *Field name getter.
 *
 * @return field name to get.
 */
  public String getFieldName() {
    return fieldName;
  }


  /**
 *Setter for fieldName.
 *
 * @param val fieldName to set.
 */
  public void setFieldName(final String val) {
    this.fieldName = val;
  }


  /**
   *Getter for fieldValue.
   *
 * @return fieldValue to get.
 */
  public long getFieldValue() {
    return fieldValue;
  }


  /**
 *Setter for fieldValue.
 *
 * @param val value to set.
 */
  public void setFieldValue(final long val) {
    this.fieldValue = val;
  }
}
