package com.project.reimburse.repositories;

import com.project.reimburse.entities.Designation;
import com.project.reimburse.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * This is a UserRepo class.
 */
public interface UserRepo extends JpaRepository<User, Integer> {

  /**
   * This is used to find User by email and password.
   *
   * @param email Email address.
   * @param password Password of user.
   * @return It returns object of an User.
 */
  User findByEmailAndPassword(String email, String password);

  /**
   * This is used to find list of users by its designation.
   *
   * @param designation Name of the department.
   * @return list of Users.
   */
  List<User> findByDesignation(Designation designation);

  /**
   * This is used to find list of Users by department Name.
   *
   * @param departmentName Name of the Department.
   * @return list of Users.
  */
  @Query("SELECT u FROM User u WHERE u.designation = 'Manager' AND"
      + " u.department.departmentName =:departmentName")
  List<User> findManagersInDepartment(@Param("departmentName") String departmentName);

  /**
   * Find user by its designation and name.
   *
   * @param designation Designation of the user.
   * @param name Name of the manager.
   * @return It returns object of an User.
   */
  User findByDesignationAndName(Designation designation, String name);

  /**
   * find User by its email.
   *
   * @param email Email of User.
   * @return Object of User.
   */
  User findByEmail(String email);

  /**
   * This method is used to check if user is exist by its mail or not.
   *
 * @param userEmail email of an user.
 * @return boolean value , which provides whether user is present or not.
 */
  Boolean existsByEmailContainingIgnoreCase(String userEmail);
}
