package com.project.reimburse.services;


import com.project.reimburse.dtos.in.UserInDto;
import com.project.reimburse.dtos.out.EmployeeOutDto;
import com.project.reimburse.dtos.out.UserOutDto;
import java.util.List;


/**
 * This is a UserService Interface.
 */
public interface UserService {
  /**
   * This Service is used to create a new User.
   *
   *@param userInDto It takes userInDto Object.
   *@return It returns UserOutDto object.
   */
  UserOutDto createUser(UserInDto userInDto);

  /**
   * This is a Service for AuthUser.
   *
   * @param email This is email.
   * @param password This is a password.
   * @return It returns Object of UserDto.
   */
  UserOutDto loginUser(String email, String password);


  /**
   * This Service is used to get all employee by designation.
   *
   * @param designation String consist of designation name.
   * @return a list of UserOutDto object .
   */
  List<EmployeeOutDto> getByDesignation(String designation);

  /**
   * This Service implementation is used to get all the managers in corresponding departments.
   *
   * @param departmentName String parameter consist of departmentName.
   * @return It returns a list of UserOutDto object.
   */
  List<UserOutDto> getManagersInDepartment(String departmentName);

  /**
   * This Service is used to Update manager of particular employee.
   *
   * @param employeeEmail This is email of employee.
   * @param managerName This is a name of manager.
   * @return It returns the Boolean value.
   */
  boolean updateManagerForEmployee(String employeeEmail, String managerName);
}


