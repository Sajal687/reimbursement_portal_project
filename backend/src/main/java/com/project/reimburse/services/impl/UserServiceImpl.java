package com.project.reimburse.services.impl;

import com.project.reimburse.dtos.in.UserInDto;
import com.project.reimburse.dtos.out.DepartmentOutDtoClass;
import com.project.reimburse.dtos.out.EmployeeOutDto;
import com.project.reimburse.dtos.out.UserOutDto;
import com.project.reimburse.entities.Department;
import com.project.reimburse.entities.Designation;
import com.project.reimburse.entities.User;
import com.project.reimburse.exceptions.BadCredentialsException;
import com.project.reimburse.exceptions.ResourceAlreadyExist;
import com.project.reimburse.exceptions.UserNotFoundException;
import com.project.reimburse.repositories.UserRepo;
import com.project.reimburse.services.UserService;
import com.project.reimburse.utils.MessageConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is a UserServiceImpl.
 */
@Service
public class UserServiceImpl implements UserService {
  /**
  *UserRepo Instance.
  */
  @Autowired
  private UserRepo userRepo;

  /**
 * DepartmentServiceImpl instance.
 */
  @Autowired
  private DepartmentServiceImpl departmentService;

  /**
   * This is a Logger Object.
   */
  private final Logger log = LoggerFactory
         .getLogger(UserServiceImpl.class);

  /**
   * This Service implementation is used to create a new User.
   *
   * @Param userInDto It takes userInDto Object.
   * @return It returns UserOutDto object.
   */
  @Override
   public UserOutDto createUser(final UserInDto userInDto) {
    userInDto.setManagerId(1);
    Boolean ifExistByEmail = userRepo.existsByEmailContainingIgnoreCase(userInDto.getEmail());
    if (ifExistByEmail) {
      throw new ResourceAlreadyExist(MessageConstants.EMAIL_ALREADY_EXISTS);
    }
    User user = mapToUser(userInDto);
    User savedUser = this.userRepo.save(user);
    UserOutDto resp = mapToUserOutDto(savedUser);
    log.info("Creating a new User.");
    return resp;
  }

  /**
    * This is a Service implementation for AuthUser.
    *
    * @param email This is email.
    * @param password This is a password.
    * @return It returns Object of UserDto.
    */
  @Override
  public UserOutDto loginUser(final String email, final String password) {
    Boolean ifExistByEmail = userRepo.existsByEmailContainingIgnoreCase(email);
    if (!ifExistByEmail) {
      throw new BadCredentialsException(MessageConstants.USER_NOT_FOUND);
    }
    User authUser = this.userRepo.findByEmailAndPassword(email, password);
    log.info("Login User with email {}", email);
    if (authUser != null) {
      UserOutDto resp = mapToUserOutDto(authUser);
      return resp;
    } else {
      throw new BadCredentialsException(MessageConstants.PASSWORD_NOT_VALID);
    }
  }

  /**
  * This Service implementation is used to get all employee by designation.
  *
  * @param designation String consist of designation name.
  * @return a list of UserOutDto object .
  */
  @Override
  public List<EmployeeOutDto> getByDesignation(final String designation) {
    List<User> users = null;
    if (designation.equals("Employee")) {
      users = this.userRepo.findByDesignation(Designation.Employee);
    } else if (designation.equals("Manager")) {
      users = this.userRepo.findByDesignation(Designation.Manager);
    }
    log.info("Finding a list of users with designation name {}", designation);
    List<EmployeeOutDto> resp = new ArrayList<>();
    if (users != null) {
      for (User user : users) {
        EmployeeOutDto employeeOutDto = mapToEmployeeOutDto(user);
        resp.add(employeeOutDto);
      }
    }
    return resp;
  }

  /**
  * This Service implementation is used to get all the managers in corresponding departments.
  *
  * @param departmentName String parameter consist of departmentName.
  * @return It returns a list of UserOutDto object.
  */
  @Override
  public List<UserOutDto> getManagersInDepartment(final String departmentName) {
    List<User> managers = userRepo.findManagersInDepartment(departmentName);
    List<UserOutDto> allManagersDtos = managers.stream().map(
         user -> mapToUserOutDto(user)).collect(Collectors.toList());
    return allManagersDtos;
  }

  /**
  * This Service implementation is used to Update manager of particular employee.
  *
  * @param employeeEmail This is email of employee.
  * @param managerName This is a name of manager.
  * @return It returns the Boolean value.
  */
  @Override
  public boolean updateManagerForEmployee(final String employeeEmail,
       final String managerName) {
    log.info("Find the manager by name");
    User manager = userRepo.findByDesignationAndName(Designation.Manager, managerName);

    if (manager != null) {
      log.info("Find the employee by employeeId");
      Optional<User> optionalEmployee = Optional.ofNullable(
          userRepo.findByEmail(employeeEmail));

      if (optionalEmployee.isPresent()) {
        User employee = optionalEmployee.get();
        employee.setManagerId(manager.getId());
        log.info("Save the updated Employee");
        userRepo.save(employee);
        log.info("Manager updated successfully");
        return true;
      } else {
        throw new UserNotFoundException("Employee not found.");
      }
    } else {
      throw new UserNotFoundException("Manager not found.");
    }
  }

  /**
  * This is a Custom Mapper for mapping of UserInDto class Object to User Class Object.
  *
  * @param userInDto UserInDto Object to mapping from.
  * @return User Class Object.
  */
  public static User mapToUser(final UserInDto userInDto) {
    User user = new User();

    user.setName(userInDto.getName());
    user.setDesignation(userInDto.getDesignation());
    user.setEmail(userInDto.getEmail());
    user.setDateOfJoining(userInDto.getDateOfJoining());
    user.setManagerId(userInDto.getManagerId());
    user.setPassword(userInDto.getPassword());
    user.setSecretAnswer(userInDto.getSecretAnswer());
    Department department = new Department();
    department.setId(userInDto.getDepartment().getId());
    department.setDepartmentName(userInDto.getDepartment().getDepartmentName());
    user.setDepartment(department);
    return user;
  }

  /**
   * This is a custom mapper.
   *
   * @param user This takes object of type User.
  * @return UserOutDto it returns object of type UserOutDto
  */
  public UserOutDto mapToUserOutDto(final User user) {
    if (user != null) {
      UserOutDto userOutDto = new UserOutDto();
      userOutDto.setName(user.getName());
      userOutDto.setDesignation(user.getDesignation());
      userOutDto.setEmail(user.getEmail());
      userOutDto.setDateOfJoining(user.getDateOfJoining());
      userOutDto.setManagerId(user.getManagerId());
      DepartmentOutDtoClass departmentOutDto =
          departmentService.getDepartmentById(user.getDepartment().getId());
      userOutDto.setDepartmentOutDto(departmentOutDto);
      return userOutDto;
    } else {
      return null;
    }
  }

  /**
  * This is a custom mapper.
  *
  * @param user This takes object of type User.
  * @return UserOutDto it returns object of type UserOutDto
  */
  public EmployeeOutDto mapToEmployeeOutDto(final User user) {
    EmployeeOutDto employeeOutDto = new EmployeeOutDto();
    employeeOutDto.setName(user.getName());
    employeeOutDto.setDesignation(user.getDesignation());
    employeeOutDto.setEmail(user.getEmail());
    employeeOutDto.setDateOfJoining(user.getDateOfJoining());
    employeeOutDto.setManagerId(user.getManagerId());
    User manager = userRepo.findById(user.getManagerId())
        .orElseThrow(() -> new UserNotFoundException(MessageConstants.USER_NOT_FOUND));
    employeeOutDto.setManagerName(manager.getName());
    DepartmentOutDtoClass departmentOutDto = new DepartmentOutDtoClass();
    departmentOutDto.setId(user.getDepartment().getId());
    departmentOutDto.setDepartmentName(user.getDepartment().getDepartmentName());
    employeeOutDto.setDepartmentOutDto(departmentOutDto);
    return employeeOutDto;
  }
}
