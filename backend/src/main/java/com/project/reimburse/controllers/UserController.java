package com.project.reimburse.controllers;

import com.project.reimburse.dtos.in.LoginInfoInDto;
import com.project.reimburse.dtos.in.UserInDto;
import com.project.reimburse.dtos.out.EmployeeOutDto;
import com.project.reimburse.dtos.out.UserOutDto;
import com.project.reimburse.services.UserService;
import com.project.reimburse.utils.ApiHomePath;
import com.project.reimburse.utils.MessageConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a User Controller.
*/
@CrossOrigin
@RestController
@RequestMapping(ApiHomePath.USER_HOME_PATH)
public class UserController {

  /**
  *UserService Instance.
  */
  @Autowired
  private UserService userService;

  /**
 * This is a Logger Object.
 */
  private final Logger log = LoggerFactory
       .getLogger(UserController.class);

  /**
   * This Controller is used to create a new User.
   *
   * @param userInDto It takes userInDto Object from the request body.
   * @return It returns UserOutDto object with Created HttpCode.
   */
  @PostMapping("/register")
  public ResponseEntity<Map<String, String>> createUser(
      final @Valid @RequestBody UserInDto userInDto) {
    log.info("CreateUser method is invoked");
    UserOutDto saveUser = this.userService.createUser(userInDto);
    Map<String, String> resp = new HashMap<String, String>();
    if (saveUser == null) {
      resp.put("data", MessageConstants.USER_NOT_CREATED);
    }
    log.info("User is Successfully Created");
    resp.put("data", MessageConstants.USER_CREATED_SUCCESSFULLY);
    return new ResponseEntity<Map<String, String>>(resp, HttpStatus.CREATED);
  }

  /**
   * This is a Controller for AuthUser.
   *
 * @param loginInfo It takes key value pair of email and password.
 * @return It returns Object of UserDto with corresponding Status code.
 */
  @PostMapping("/auth")
  public ResponseEntity<UserOutDto> authUser(final @Valid @RequestBody LoginInfoInDto loginInfo) {
    log.info("AuthUser method is invoked");
    UserOutDto userDto = this.userService.loginUser(
        loginInfo.getEmail(), loginInfo.getPassword());

    if (userDto != null) {
      log.info("User is Successfully Authenticated");
      return new ResponseEntity<UserOutDto>(userDto, HttpStatus.OK);
    } else {
      log.warn("Authentication failed for user with email: {}", loginInfo.getEmail());
      return new ResponseEntity<UserOutDto>(HttpStatus.UNAUTHORIZED);
    }
  }

  /**
   * This Controller request is used to get all employee by designation.
   *
   * @param designationName String consist of designation name.
   * @return a list of UserOutDto object with the the corresponding Status code.
   */
  @GetMapping("/employee/{designationName}")
  public ResponseEntity<List<EmployeeOutDto>> getEmployeeByDesignationName(
      final @PathVariable String designationName) {
    log.info("Fetching employees by designation: {}", designationName);
    List<EmployeeOutDto> resp = this.userService.getByDesignation(designationName);
    return new ResponseEntity<List<EmployeeOutDto>>(resp, HttpStatus.OK);
  }

  /**
   * This controller request is used to get all the managers in corresponding departments.
   *
   * @param departmentName String parameter consist of departmentName.
   * @return It returns a list of UserOutDto object.
   */
  @GetMapping("/managers-in-department/{departmentName}")
  public ResponseEntity<List<UserOutDto>>
      getManagersInDepartment(final @PathVariable String departmentName) {
    log.info("Fetching managers in department: {}", departmentName);
    List<UserOutDto> managers = userService.getManagersInDepartment(departmentName);
    return new ResponseEntity<List<UserOutDto>>(managers, HttpStatus.OK);
  }

  /**
   * This controller request is used to Update manager of particular employee.
   *
   * @param json Takes key-value pair as a employeeEmail and the manager Name.
   * @return It returns the string value consist of corresponding message.
   */
  @PutMapping("/update-emp-manager")
  public ResponseEntity<Map<String, String>> updateManagerForEmployee(
      final @RequestBody Map<String, String> json) {
    String employeeEmail = json.get("employeeEmail");
    String managerName = json.get("managerName");

    boolean updated = userService.updateManagerForEmployee(employeeEmail, managerName);
    if (updated) {
      log.info("Manager for employee with email {} updated to: {}", employeeEmail, managerName);
      Map<String, String> respMap = new HashMap<String, String>();
      respMap.put("data", MessageConstants.EMPLOYEE_ASSIGNED_MESSAGE);
      return new ResponseEntity<Map<String, String>>(respMap, HttpStatus.OK);
    } else {
      log.warn("Failed to update manager for employee with email: {}", employeeEmail);
      Map<String, String> respMap = new HashMap<String, String>();
      respMap.put("data", MessageConstants.EMPLOYEE_NOT_ASSINED_MESSAGE);
      return new ResponseEntity<Map<String, String>>(respMap, HttpStatus.BAD_REQUEST);
    }
  }

}
