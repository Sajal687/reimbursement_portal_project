package com.project.reimburse.dtos.out;

import com.project.reimburse.entities.Designation;
import java.util.Date;
import java.util.Objects;


/**
 * This is s User Dto Class.
 */
public class EmployeeOutDto {
  /**
  *This is a Parameterized Constructor of UserOutDto.
  *
  * @param val Name of the user.
  * @param val2 Designation of the User.
  * @param val3 Email of the User.
  * @param val4 Date of joining of the User.
  * @param val5 Manager Id of the User.
  * @param val6 Manager name.
  * @param val7 Department of the user.
  */
  public EmployeeOutDto(final String val, final Designation val2,
      final String val3, final Date val4, final int val5, final String val6,
      final DepartmentOutDtoClass val7) {
    super();
    this.name = val;
    this.designation = val2;
    this.email = val3;
    this.dateOfJoining = new Date(val4.getTime());
    this.managerId = val5;
    this.managerName = val6;
    this.departmentOutDto = val7;
  }

  /**
   * No argument constructor for UserOutDto.
   */
  public EmployeeOutDto() {
   // TODO Auto-generated constructor stub
  }

  private String name;

  private Designation designation;

  private String email;

  private Date dateOfJoining;

  private int managerId;

  private String managerName;

  private DepartmentOutDtoClass departmentOutDto;

  /**
   * This is a Getter for name.
   *
 * @return name.
 */
  public String getName() {
    return name;
  }

  /**
   * This is a setter for name.
   *
 * @param val Name to set.
 */
  public void setName(final String val) {
    this.name = val;
  }

  /**
   *This is a getter for designation.
   *
 * @return designation.
 */
  public Designation getDesignation() {
    return designation;
  }

  /**
   *This is a setter for designation.
   *
 * @param val designation to set.
 */
  public void setDesignation(final Designation val) {
    this.designation = val;
  }

  /**
   *It is a getter for email.
   *
 * @return email.
 */
  public String getEmail() {
    return email;
  }

  /**
   *Setter for email.
   *
 * @param val email to set.
 */
  public void setEmail(final String val) {
    this.email = val;
  }

  /**
   *It is a getter for date of joining.
   *
 * @return dateOfJoining
 */
  public Date getDateOfJoining() {
    Date date = new Date(dateOfJoining.getTime());
    return date;
  }

  /**
   *It is a setter for dateOfJoining.
   *
 * @param val  dateOfJoining to set.
 */
  public void setDateOfJoining(final Date val) {
    Date dt = new Date(val.getTime());
    dateOfJoining = dt;
  }

  /**
   * It is a getter for managerId.
   *
 * @return managerId.
 */
  public int getManagerId() {
    return managerId;
  }

  /**
   *It is a setter for ManagerId.
   *
 * @param val manangerId to set.
 */
  public void setManagerId(final int val) {
    this.managerId = val;
  }

  /**
   * It is a getter for department.
   *
 * @return department
 */
  public DepartmentOutDtoClass getDepartmentOutDto() {
    DepartmentOutDtoClass deptCopy = new DepartmentOutDtoClass();
    deptCopy.setDepartmentName(departmentOutDto.getDepartmentName());
    deptCopy.setId(departmentOutDto.getId());
    return deptCopy;
  }

  /**
   * It is a setter for department.
   *
 * @param val Department to set.
 */
  public void setDepartmentOutDto(final DepartmentOutDtoClass val) {
    this.departmentOutDto = val;
  }

  /**
 *This is a getter for manager Name.
 *
 * @return managerName.
 */
  public String getManagerName() {
    return managerName;
  }

  /**
  *This is a setter for manager name.
  *
  * @param val name of the manager to set.
  */
  public void setManagerName(final String val) {
    this.managerName = val;
  }

  /**
  *This is a HashCode method.
 */
  @Override
  public int hashCode() {
    return Objects.hash(dateOfJoining, departmentOutDto,
     designation, email, managerId, managerName, name);
  }

  /**
 *This is a equals method.
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
    EmployeeOutDto other = (EmployeeOutDto) obj;
    return Objects.equals(dateOfJoining, other.dateOfJoining)
       && Objects.equals(departmentOutDto, other.departmentOutDto)
       && designation == other.designation
       && Objects.equals(email, other.email)
       && managerId == other.managerId
       && Objects.equals(managerName, other.managerName)
       && Objects.equals(name, other.name);
  }

  /**
 *This is a toString method.
 */
  @Override
  public String toString() {
    return "EmployeeOutDto [name=" + name + ", designation="
      + designation + ", email=" + email + ", dateOfJoining="
      + dateOfJoining + ", managerId=" + managerId + ", managerName="
      + managerName + ", departmentOutDto=" + departmentOutDto + "]";
  }
}

