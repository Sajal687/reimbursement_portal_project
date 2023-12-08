package com.project.reimburse.dtos.in;

import com.project.reimburse.entities.Designation;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * This is a UserInDto Class .
 */
public class UserInDto {
  /**
   *This is a Parameterized Constructor of UserInDto.
   *
 * @param val1 Name of the user.
 * @param val2 Designation of the User.
 * @param val3 Email of the User.
 * @param val4 Date of joining of the User.
 * @param val5 Manager Id of the User.
 * @param val6 Password of the User.
 * @param val7 Secret answer of the User.
 * @param val8 Department of the user.
 */
  public UserInDto(
      final @NotEmpty(message = "User Name is Required.") String val1,
      final @NotEmpty(message = "User Designation is Required.") Designation val2,
      final @Email(message = "Invalid Email Format.")
      @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@nucleusteq.com$",
      message = "Please enter a valid email.") String val3,
      final Date val4,
      final int val5,
      final @Size(min = 8, message = "Password must be minimum of 8 characters.")
      @NotEmpty(message = "User Password is Required") String val6,
      final String val7,
      final @NotNull(message = "Department ID is Required")
      DepartmentInDtoClass val8) {
    super();
    this.name = val1;
    this.designation = val2;
    this.email = val3;
    this.dateOfJoining = new Date(val4.getTime());
    this.managerId = val5;
    this.password = val6;
    this.secretAnswer = val7;
    this.department = val8;
  }

  /**
  * No argument constructor for UserInDto.
  */
  public UserInDto() {
   // TODO Auto-generated constructor stub
  }

  @NotEmpty(message = "User name is required.")
  @Size(min = 3,
      message = "Name must contains atleast 3 characters.")
  @Pattern(message = "Name must be full Name and contains only characters",
      regexp = "^[A-Za-z]+[A-Za-z ]*$")
  private String name;

  @NotNull(message = "User designation is required.")
  private Designation designation;

  @NotBlank(message = "Email is required.")
  @Email(message = "Invalid email format.")
  @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@nucleusteq.com$",
      message = "Please enter a valid email, it must contains nucleusteq domain.")
  private String email;

  @Past(message = "Date of Joining is not more than current date.")
  @Temporal(TemporalType.DATE)
  private Date dateOfJoining;

  private int managerId;

  @Size(min = 8, message = "Password must be minimum of 8 characters.")
  @NotEmpty(message = "User password is required")
  private String password;

  @NotBlank(message = "Secret answer is required.")
  private String secretAnswer;

  @NotNull(message = "Department ID is required")
  private DepartmentInDtoClass department;

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
  * It is a getter for password.
  *
  * @return password.
  */
  public String getPassword() {
    return password;
  }

  /**
  *It is a setter for password.
  *
  * @param val password to set.
  */
  public void setPassword(final String val) {
    this.password = val;
  }

  /**
  * It is a getter for secretAnswer.
  *
  * @return secretAnswer.
  */
  public String getSecretAnswer() {
    return secretAnswer;
  }

  /**
  * It is a setter of secretAnswer.
  *
  * @param val Value to set.
  */
  public void setSecretAnswer(final String val) {
    this.secretAnswer = val;
  }

  /**
  * It is a getter for department.
  *
  * @return department
  */
  public DepartmentInDtoClass getDepartment() {
    return department;
  }

  /**
  * It is a setter for department.
  *
  * @param val Department to set.
  */
  public void setDepartment(final DepartmentInDtoClass val) {
    this.department = val;
  }

  /**
  *This is a hashCode method.
  */
  @Override
  public int hashCode() {
    return Objects.hash(dateOfJoining, department, designation,
    email, managerId, name, password, secretAnswer);
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
    UserInDto other = (UserInDto) obj;
    return Objects.equals(dateOfJoining, other.dateOfJoining)
       && Objects.equals(department, other.department)
       && designation == other.designation
       && Objects.equals(email, other.email)
       && managerId == other.managerId
       && Objects.equals(name, other.name)
       && Objects.equals(password, other.password)
       && Objects.equals(secretAnswer, other.secretAnswer);
  }

  /**
  *This is a To String method.
 */
  @Override
  public String toString() {
    return "UserInDto [name=" + name + ", designation=" + designation + ", email="
       + email + ", dateOfJoining="
       + dateOfJoining + ", managerId=" + managerId + ", password=" + password
       + ", secretAnswer=" + secretAnswer
       + ", department=" + department + "]";
  }
}
