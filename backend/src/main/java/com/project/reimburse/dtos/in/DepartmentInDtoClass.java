package com.project.reimburse.dtos.in;

import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * This is a DepartmentInDto Class.
 */
public class DepartmentInDtoClass {
  /**
   * This is a Constructor for DepartmentInDtoClass.
   *
   * @param val1 It is a id of an Department.
   * @param str1 name of the Department.
  */
  public DepartmentInDtoClass(final int val1,
      final @NotEmpty(message = "Department Name is Required.") String str1) {
    super();
    this.id = val1;
    this.departmentName = str1;
  }

  /**
  * This is a No argument constructor.
  */
  public DepartmentInDtoClass() {
   // TODO Auto-generated constructor stub
  }

  private int id;

  @Pattern(message = "Department name must only contains alphabets.", regexp = "^[a-zA-Z_ ]*$")
  @Size(min = 1, message = "Department name must be greater than 2 characters.")
  @NotEmpty(message = "Department Name is Required.")
  private String departmentName;

  /**
   * This is a getter for Id.
   *
   * @return id.
  */
  public int getId() {
    return id;
  }

  /**
  *Setter for Id.
  *
  * @param departmentId Id to set.
  */
  public void setId(final int departmentId) {
    this.id = departmentId;
  }

  /**
  * Getter for department name.
  *
  * @return departmentName.
  */
  public String getDepartmentName() {
    return departmentName;
  }

  /**
  * Setter for department Name.
  *
  * @param val1 departmentName to set.
  */
  public void setDepartmentName(final String val1) {
    this.departmentName = val1;
  }

  /**
  *This is a hashCode method.
  */
  @Override
  public int hashCode() {
    return Objects.hash(departmentName, id);
  }

  /**
  * This is equals.
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
    DepartmentInDtoClass other = (DepartmentInDtoClass) obj;
    return Objects.equals(departmentName, other.departmentName) && id == other.id;
  }

  /**
  *This is a toString method.
  */
  @Override
  public String toString() {
    return "DepartmentInDtoClass [id=" + id + ", departmentName=" + departmentName + "]";
  }
}
