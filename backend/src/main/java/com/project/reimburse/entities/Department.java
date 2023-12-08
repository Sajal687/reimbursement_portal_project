package com.project.reimburse.entities;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The is Department Class.
 */
@Entity
@Table(name = "department")
public class Department {
  /** This is a Constructor for Department.
   *
   * @param departmentId It is a id of an Department.
   * @param val1 name of the Department.
   * @param users List of Users.
   */
  public Department(final int departmentId,
      final String val1,
      final List<User> users) {
    super();
    this.id = departmentId;
    this.departmentName = val1;
    this.user = users;
  }

  /**
   * This is a No argument constructor.
   */
  public Department() {
   // TODO Auto-generated constructor stub
  }

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "department_id")
 private int id;

  @Column(name = "department_name", unique = true)
  private String departmentName;

  @OneToMany(mappedBy = "department")
  private List<User> user;

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
   * Getter for User.
   *
 * @return user.
 */
  public List<User> getUser() {
    List<User> userCopy = new ArrayList<User>();
    userCopy.addAll(user);
    return userCopy;
  }

  /**
   *Setter for user field.
   *
 * @param users User to set.
 */
  public void setUser(final List<User> users) {
    this.user = users;
  }

  /**
 *This is a hashcode for Department entity.
 */
  @Override
  public int hashCode() {
    return Objects.hash(departmentName, id, user);
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
    Department other = (Department) obj;
    return Objects.equals(departmentName, other.departmentName)
       && id == other.id && Objects.equals(user, other.user);
  }

  /**
 *This is a toString method.
 */
  @Override
  public String toString() {
    return "Department [id=" + id + ", departmentName=" + departmentName + ", user=" + user + "]";
  }
}
