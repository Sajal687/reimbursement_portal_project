package com.project.reimburse.dtos.in;


import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 *This is a InDto class for Login Information.
 */
public class LoginInfoInDto {
  /**
  * Default Constructor.
  */
  public LoginInfoInDto() {
  }

  @Email(regexp = "[a-z0-9.]+@nucleusteq.com",
          message = "Email must contains nucleusteq domain name.")
  @NotBlank(message = "Emai ID is required.")
  private String email;

  @NotBlank(message = "Password is required.")
  private String password;


  /**
  *Getter for email.
  *
  * @return email.
  */
  public String getEmail() {
    return email;
  }

  /**
  *Setter for email.
  *
  * @param val Value to set.
  */
  public void setEmail(final String val) {
    this.email = val;
  }

  /**
  *Getter for Password.
  *
  * @return password.
  */
  public String getPassword() {
    return password;
  }

  /**
  *Setter for password.
  *
  * @param val Password to set.
  */
  public void setPassword(final String val) {
    this.password = val;
  }

  /**
  *This is a hashCode method.
  */
  @Override
  public int hashCode() {
    return Objects.hash(email, password);
  }

  /**
  *This is equals method.
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
    LoginInfoInDto other = (LoginInfoInDto) obj;
    return Objects.equals(email, other.email) && Objects.equals(password, other.password);
  }

  /**
  *This is toString method.
  */
  @Override
  public String toString() {
    return "LoginInfoInDto [email=" + email + ", password=" + password + "]";
  }
}
