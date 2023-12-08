package com.project.reimburse.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reimburse.dtos.in.DepartmentInDtoClass;
import com.project.reimburse.dtos.in.LoginInfoInDto;
import com.project.reimburse.dtos.in.UserInDto;
import com.project.reimburse.dtos.out.DepartmentOutDtoClass;
import com.project.reimburse.dtos.out.EmployeeOutDto;
import com.project.reimburse.dtos.out.UserOutDto;
import com.project.reimburse.entities.Designation;
import com.project.reimburse.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Autowired
	private ObjectMapper objectMapper = new ObjectMapper();
	
	

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateUser() throws Exception {
		DepartmentInDtoClass departmentInDto = new DepartmentInDtoClass();
		departmentInDto.setDepartmentName("IT");
		Date dtDate = new Date();
		UserInDto userInDto = new UserInDto("Test Name", Designation.Employee, "test@nucleusteq.com", dtDate, 0,
				"Test@123", "This is my Secret Answer", departmentInDto);

		DepartmentOutDtoClass departmentOutDto = new DepartmentOutDtoClass();
		departmentOutDto.setDepartmentName("IT");
		UserOutDto userOutDto = new UserOutDto("Test Name", Designation.Employee, "test@nucleusteq.com", dtDate, 0,
				departmentOutDto);

		Mockito.when(userService.createUser(userInDto)).thenReturn(userOutDto);
		ResultActions result = mockMvc.perform(post("/api/user/register")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(userInDto)));
		result.andExpect(status().isCreated()).andReturn();
	}

	@Test
	public void testAuthUser() throws Exception {
		LoginInfoInDto loginInfo = new LoginInfoInDto();
		loginInfo.setEmail("test@nucleusteq.com");
		loginInfo.setPassword("User@123");

		DepartmentOutDtoClass departmentOutDto = new DepartmentOutDtoClass();
		departmentOutDto.setDepartmentName("IT");
		UserOutDto userOutDto = new UserOutDto("Test Name", Designation.Employee, "test@nucleusteq.com", new Date(), 0,
				departmentOutDto);

		when(userService.loginUser(loginInfo.getEmail(), loginInfo.getPassword())).thenReturn(userOutDto);

		ResultActions result = mockMvc.perform(post("/api/user/auth").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginInfo)));

		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@nucleusteq.com"));
		result.andReturn();

		verify(userService, times(1)).loginUser(loginInfo.getEmail(), loginInfo.getPassword());
	}

	@Test
	public void testAuthUser_AuthenticationFailure() throws Exception {
		LoginInfoInDto loginInfo = new LoginInfoInDto();
		loginInfo.setEmail("test@nucleusteq.com");
		loginInfo.setPassword("invalidPassword");

		Mockito.when(userService.loginUser("test@nucleusteq.com", "invalidPassword")).thenReturn(null);

		mockMvc.perform(post("/api/user/auth").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginInfo))).andExpect(status().isUnauthorized());
	}
	
	@Test
	public void testGetEmployeeByDesignationName() throws Exception {
		String designationName = "Employee";
		EmployeeOutDto employeeOutDto = new EmployeeOutDto();
		employeeOutDto.setName("Test Name");
		employeeOutDto.setDepartmentOutDto(new DepartmentOutDtoClass(1, "HR"));
		employeeOutDto.setManagerId(0);

		employeeOutDto.setEmail("test@nucleusteq.com");
		employeeOutDto.setDateOfJoining(new Date());
		employeeOutDto.setDesignation(Designation.Employee);
		List<EmployeeOutDto> employeeList = Collections.singletonList(employeeOutDto);

		when(userService.getByDesignation(designationName)).thenReturn(employeeList);

		ResultActions result = mockMvc.perform(
				get("/api/user/employee/{designationName}", designationName).contentType(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$[0].email").value("test@nucleusteq.com"));
		result.andReturn();

		verify(userService, times(1)).getByDesignation(designationName);

	}

	@Test
	public void testGetManagersInDepartment() throws Exception {
		String departmentName = "HR";
		DepartmentOutDtoClass departmentOutDto = new DepartmentOutDtoClass();
		departmentOutDto.setDepartmentName("IT");
		UserOutDto userOutDto = new UserOutDto("Test Name", Designation.Employee, "test@nucleusteq.com", new Date(), 0,
				departmentOutDto);
		List<UserOutDto> userList = Collections.singletonList(userOutDto);

		when(userService.getManagersInDepartment(departmentName)).thenReturn(userList);

		ResultActions result = mockMvc.perform(get("/api/user/managers-in-department/{departmentName}", departmentName)
				.contentType(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$[0].email").value("test@nucleusteq.com")).andReturn();

		verify(userService, times(1)).getManagersInDepartment(departmentName);
	}

	@Test
	public void testUpdateManagerForEmployee_Success() throws Exception {
		Map<String, String> mp = new HashMap<>();
		mp.put("employeeEmail", "test@nucleusteq.com");
		mp.put("managerName", "ManagerName");

		when(userService.updateManagerForEmployee("test@nucleusteq.com", "ManagerName")).thenReturn(true);

		ResultActions result = mockMvc.perform(put("/api/user/update-emp-manager")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(mp)));
		result.andExpect(status().isOk());
	}

	@Test
	public void testUpdateManagerForEmployee_Failure() throws Exception {
		Map<String, String> mp = new HashMap<>();
		mp.put("employeeEmail", "test@nucleusteq.com");
		mp.put("managerName", "ManagerName");

		when(userService.updateManagerForEmployee("test@nucleusteq.com", "ManagerName")).thenReturn(false);

		ResultActions result = mockMvc.perform(put("/api/user/update-emp-manager")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(mp)));
		result.andExpect(status().isBadRequest());
	}
}