package com.project.reimburse.entities;


import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This is User Class.
 */
@Entity
@Table(name = "users")
public class User {
  /**
    *This is a Parameterized Constructor of User.
    *
    *@param val Id of an User
    * @param val1 Name of the user.
    * @param val2 Designation of the User.
    * @param val3 Email of the User.
    * @param val4 Date of joining of the User.
    * @param val5 Manager Id of the User.
    * @param val6 Password of the User.
    * @param val7 Secret answer of the User.
    * @param val8 Department of the user.
    * @param val9 Reimbursement Request Object.
    */
  public User(
        final int val,
        final String val1,
        final Designation val2,
        final String val3,
        final Date val4,
        final int val5,
        final String val6,
        final String val7,
        final Department val8,
        final List<ReimburseRequest> val9) {
    super();
    this.id = val;
    this.name = val1;
    this.designation = val2;
    this.email = val3;
    this.dateOfJoining = new Date(val4.getTime());
    this.managerId = val5;
    this.password = val6;
    this.secretAnswer = val7;
    this.department = val8;
    this.reimburseReq = val9;
  }

  /**
   * No argument constructor for User.
   */
  public User() {
    // TODO Auto-generated constructor stub
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private int id;

  private String name;

  @Enumerated(EnumType.STRING)
  private Designation designation;

  @Column(nullable = false, unique = true)
  private String email;

  @Temporal(TemporalType.DATE)
  @Column(name = "date_of_joining")
  private Date dateOfJoining;

  @Column(name = "manager_id")
  private int managerId;

  private String password;

  @Column(name = "secret_answer")
  private String secretAnswer;

  @ManyToOne
  private Department department;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<ReimburseRequest> reimburseReq;

  /**
   * It is a getter for Id.
   *
   * @return id.
   */
  public int getId() {
    return id;
  }

  /**
   * It is s setter for ID.
   *
   * @param val Id to set.
   */
  public void setId(final int val) {
    this.id = val;
  }

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
  public Department getDepartment() {
    return department;
  }

  /**
   * It is a setter for department.
   *
   * @param val Department to set.
   */
  public void setDepartment(final Department val) {
    this.department = val;
  }

  /**
   * This is getter for reimburseReq.
   *
   * @return reimburseReq.
   */
  public List<ReimburseRequest> getReimburseReq() {
    return reimburseReq;
  }

  /**
   *It is a setter for Reimbursement Request.
   *
   * @param val reimburseReq to set.
   */
  public void setReimburseReq(final List<ReimburseRequest> val) {
    this.reimburseReq = val;
  }

  /**
   * To String method.
   */
  @Override
  public String toString() {
    return "User [id=" + id + ", name=" + name + ","
      + " designation=" + designation + ", email=" + email
      + ", dateOfJoining=" + dateOfJoining + ", managerId=" + managerId + ", password=" + password
      + ", secretAnswer=" + secretAnswer
      + " reimburseReq=" + reimburseReq
      + "]";
  }

  /**
 *This is a hashCode method.
 */
  @Override
  public int hashCode() {
    return Objects.hash(dateOfJoining, department,
     designation, email, id, managerId, name, password, reimburseReq,
     secretAnswer);
  }

  /**
 *This is a equals method of the class.
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
    User other = (User) obj;
    return Objects.equals(dateOfJoining, other.dateOfJoining)
      && Objects.equals(department, other.department)
      && designation == other.designation && Objects.equals(email, other.email) && id == other.id
      && managerId == other.managerId && Objects.equals(name, other.name)
      && Objects.equals(password, other.password)
      && Objects.equals(reimburseReq, other.reimburseReq)
      && Objects.equals(secretAnswer, other.secretAnswer);
  }
}
