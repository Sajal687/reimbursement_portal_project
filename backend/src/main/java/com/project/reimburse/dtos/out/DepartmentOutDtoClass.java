package com.project.reimburse.dtos.out;

import java.util.Objects;

/**
 * This is a DepartmentOutDto Class.
 */
public class DepartmentOutDtoClass {
  /** This is a Constructor for DepartmentOutDtoClass.
   *
   * @param departmentId It is a id of an Department.
   * @param val1 name of the Department.
   */
  public DepartmentOutDtoClass(final int departmentId, final String val1) {
    super();
    this.id = departmentId;
    this.departmentName = val1;
  }

  /**
   * This is a No argument constructor.
   */
  public DepartmentOutDtoClass() {
    // TODO Auto-generated constructor stub
  }

  private int id;

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
 *This is a Equals method.
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
    DepartmentOutDtoClass other = (DepartmentOutDtoClass) obj;
    return Objects.equals(departmentName, other.departmentName) && id == other.id;
  }

  /**
 *This is a toString method.
 */
  @Override
  public String toString() {
    return "DepartmentOutDtoClass [id=" + id + ", departmentName=" + departmentName + "]";
  }
}
