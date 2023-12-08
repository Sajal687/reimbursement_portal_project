package com.project.reimburse.services.impl;

import com.project.reimburse.dtos.in.DepartmentInDtoClass;
import com.project.reimburse.dtos.in.UserInDto;
import com.project.reimburse.dtos.out.DepartmentOutDtoClass;
import com.project.reimburse.dtos.out.EmployeeOutDto;
import com.project.reimburse.dtos.out.UserOutDto;
import com.project.reimburse.entities.Department;
import com.project.reimburse.entities.Designation;
import com.project.reimburse.entities.User;
import com.project.reimburse.exceptions.ResourceAlreadyExist;
import com.project.reimburse.exceptions.BadCredentialsException;
import com.project.reimburse.exceptions.UserNotFoundException;
import com.project.reimburse.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    
    @Mock
    private DepartmentServiceImpl departmentServiceImpl;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser_Success() {
        UserInDto userInDto = createUserInDto();
        User savedUser = createUserEntity();
        when(userRepo.existsByEmailContainingIgnoreCase(userInDto.getEmail())).thenReturn(false);
        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        UserOutDto result = userService.createUser(userInDto);

        assertNotNull(result);
        assertEquals(savedUser.getName(), result.getName());
    }

    @Test
    public void testCreateUser_UserAlreadyExists() {
        UserInDto userInDto = createUserInDto();
        userInDto.setEmail("notexist@nulceusTeq.com");
        when(userRepo.existsByEmailContainingIgnoreCase(userInDto.getEmail())).thenReturn(true);

        assertThrows(ResourceAlreadyExist.class, () -> userService.createUser(userInDto));
    }

    @Test
    public void testLoginUser_Success() {
        String email = "test@nucleusteq.com";
        String password = "Password@123";
        User user = createUserEntity();
        User user2 = new User();
        Department department = new Department();
        user2.setDepartment(department);
        user2.setEmail("test@nucleusteq.com");
        user2.setPassword("Password@123");
        user2.setDateOfJoining(new Date());
        when(userRepo.existsByEmailContainingIgnoreCase(email)).thenReturn(true);
        when(userRepo.findByEmailAndPassword(email, password)).thenReturn(user2);

        UserOutDto result = userService.loginUser(email, password);

        assertNotNull(result);
        assertEquals(user2.getName(), result.getName());
    }

    @Test
    public void testLoginUser_UserNotFound() {
        String email = "test@nucleusteq.com";
        String password = "password";

        when(userRepo.existsByEmailContainingIgnoreCase(email)).thenReturn(false);
        assertThrows(BadCredentialsException.class, () -> userService.loginUser(email, password));
    }
    
    @Test
    public void testLoginUser_InvalidCredentials() {
        when(userRepo.existsByEmailContainingIgnoreCase("test@nucleusteq.com")).thenReturn(true);
        when(userRepo.findByEmailAndPassword("test@nucleusteq.com", "invalidPassword")).thenReturn(null);

        assertThrows(BadCredentialsException.class, () -> {
            userService.loginUser("test@nucleusteq.com", "invalidPassword");
        });
    }
    
    
    
    @Test
    public void testGetByDesignationEmployee() {
        List<User> userList = new ArrayList<>();
        Department department = new Department();
        department.setDepartmentName("IT");
    	User employee = new User(0 , "Sajal" , Designation.Employee , "test@nucleusteq.com" , new Date() , 1 , "User@123" , "This is my Secret Answer" , department, null);
    	userList.add(employee);

        User managerUser = new User();
        managerUser.setName("Manager Name");
        when(userRepo.findByDesignation(Designation.Employee)).thenReturn(userList);
        when(userRepo.findById(1)).thenReturn(Optional.of(managerUser));

        List<EmployeeOutDto> employeeList = userService.getByDesignation("Employee");

        assertEquals(1, employeeList.size());
        assertEquals("test@nucleusteq.com", employeeList.get(0).getEmail());
        assertEquals("Manager Name", employeeList.get(0).getManagerName());
    }

    @Test
    public void testGetByDesignationManager() {
        List<User> userList = new ArrayList<>();
        Department department = new Department();
        department.setDepartmentName("IT");
        
        User manager = new User(1, "Test Name", Designation.Manager, "test@nucleusteq.com", new Date(), 0, "Test@123", "This is my Secret Answer", department, null);
        userList.add(manager);

        when(userRepo.findByDesignation(Designation.Manager)).thenReturn(userList);

        User managerUser = new User();
        managerUser.setName("Manager Name");
        when(userRepo.findById(0)).thenReturn(Optional.of(managerUser));

        List<EmployeeOutDto> employeeList = userService.getByDesignation("Manager");

        assertEquals(1, employeeList.size());
        assertEquals("test@nucleusteq.com", employeeList.get(0).getEmail());
    }

    
   @Test
    public void testGetManagersInDepartment_Success() {
        String departmentName = "IT";
        DepartmentOutDtoClass newDept = new DepartmentOutDtoClass(1,"IT");
        List<User> managers = createManagerEntities();
        when(userRepo.findManagersInDepartment(departmentName)).thenReturn(managers);
        when(departmentServiceImpl.getDepartmentById(1)).thenReturn(newDept);

        List<UserOutDto> result = userService.getManagersInDepartment(departmentName);
        
        assertNotNull(result);
        assertEquals(managers.size(), result.size());
    }

    @Test
    public void testUpdateManagerForEmployee_Success() {
        String employeeEmail = "sajal@nucleusTeq.com";
        String managerName = "Ayush";
        Department department = new Department();
        department.setDepartmentName("IT");
    	User employee = new User(0 , "Sajal" , Designation.Employee , "sajal@nucleusTeq.com" , new Date() , 0 , "Sajal@123" , "This is my Secret Answer" , department, null);
    	
    	Department department2 = new Department();
        department.setDepartmentName("IT");
    	User manager = new User(0 , "Ayush" , Designation.Manager , "ayush@nucleusTeq.com" , new Date() , 0 , "Ayush@123" , "This is my Secret Answer" , department2, null);
    	
    	when(userRepo.findByDesignationAndName(Designation.Manager, managerName)).thenReturn(manager);
        when(userRepo.findByEmail(employeeEmail)).thenReturn(employee);
        when(userRepo.save(employee)).thenReturn(employee);

        boolean result = userService.updateManagerForEmployee(employeeEmail, managerName);

        assertTrue(result);
        assertEquals(manager.getId(), employee.getManagerId());
    }
    
    @Test
    public void testUpdateManagerForEmployee_EmployeeNotFound() {
        when(userRepo.findByDesignationAndName(Designation.Manager, "Test Name")).thenReturn(new User());

        when(userRepo.findByEmail("test@nulceusteq.com")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateManagerForEmployee("test@nulceusteq.com", "Test Name");
        });
    }

    @Test
    public void testUpdateManagerForEmployee_ManagerNotFound() {
        when(userRepo.findByDesignationAndName(Designation.Manager, "Test1")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateManagerForEmployee("test@nulceusteq.com", "Test1");
        });
    }

    private UserInDto createUserInDto() {
    	DepartmentInDtoClass departmentInDto = new DepartmentInDtoClass();
    	departmentInDto.setDepartmentName("IT");
       
    	UserInDto userInDto = new UserInDto("Test Name" , Designation.Employee , "test@nucleusteq.com" , new Date() , 0 , "Test@123" , "This is my Secret Answer" , departmentInDto);
         return userInDto;
       }

    private User createUserEntity() {
    	Department department = new Department();
        department.setDepartmentName("IT");
       
    	User user = new User(0 , "Test Name" , Designation.Employee , "test@nucleusteq.com" , new Date() , 0 , "User@123" , "This is my Secret Answer" , department, null);
        return user;
    }

    private List<User> createManagerEntities() {
    	Department department = new Department();
        department.setDepartmentName("HR");
        department.setId(1);
        User user1 = new User(0 , "Test1" , Designation.Manager , "test@nucleusteq.com" , new Date() , 0 , "Test@123" , "This is my Secret Answer" , department, null);
        User user2 = new User(0 , "Test2" , Designation.Manager , "test2@nucleusteq.com" , new Date() , 0 , "User@123" , "This is my Secret Answer" , department, null);
       
        List<User> list = Arrays.asList(user1,user2);
        return list;
    }
}