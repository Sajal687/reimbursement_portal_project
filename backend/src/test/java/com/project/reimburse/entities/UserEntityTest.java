package com.project.reimburse.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserEntityTest {

	@Mock
	private User user;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testUserConstructor() {
		int id = 1;
		String name = "Test";
		Designation designation = Designation.Admin;
		String email = "test@nucleusteq.com";
		Date dateOfJoining = new Date();
		int managerId = 0;
		String password = "password123";
		String secretAnswer = "Secret";
		Department department = new Department();

		List<ReimburseRequest> reimburseReq = new ArrayList<>();
		ReimburseRequest request1 = new ReimburseRequest();
		ReimburseRequest request2 = new ReimburseRequest();
		reimburseReq.add(request1);
		reimburseReq.add(request2);

		User user = new User(id, name, designation, email, dateOfJoining, managerId, password, secretAnswer, department,
				reimburseReq);

		assertNotNull(user);

		assertEquals(id, user.getId());
		assertEquals(name, user.getName());
		assertEquals(designation, user.getDesignation());
		assertEquals(email, user.getEmail());
		assertEquals(dateOfJoining, user.getDateOfJoining());
		assertEquals(managerId, user.getManagerId());
		assertEquals(password, user.getPassword());
		assertEquals(secretAnswer, user.getSecretAnswer());
		assertEquals(department, user.getDepartment());
		assertEquals(reimburseReq, user.getReimburseReq());
	}

	@Test
	public void testGettersAndSetters() {
		User user = new User();
		user.setId(1);
		user.setName("Test");
		user.setDesignation(Designation.Admin);
		user.setEmail("test@nucleusteq.com");
		user.setDateOfJoining(new Date());
		user.setManagerId(1);
		user.setPassword("password@123");
		user.setSecretAnswer("My secret Answer");
		Department department = new Department();
		department.setId(1);
		department.setDepartmentName("IT");
		user.setDepartment(department);

		assertEquals(1, user.getId());
		assertEquals("Test", user.getName());
		assertEquals(Designation.Admin, user.getDesignation());
		assertEquals("test@nucleusteq.com", user.getEmail());
		assertNotNull(user.getDateOfJoining());
		assertEquals(1, user.getManagerId());
		assertEquals("password@123", user.getPassword());
		assertEquals("My secret Answer", user.getSecretAnswer());
		assertEquals(department, user.getDepartment());
	}

	@Test
	public void testToString() {
		User user = new User();
		user.setId(1);
		user.setName("Test");
		user.setDesignation(Designation.Admin);
		user.setEmail("test@nucleusteq.com");
		user.setDateOfJoining(new Date());
		user.setManagerId(1);
		user.setPassword("Passwordl@123");
		user.setSecretAnswer("My secret Answer");
		Department department = new Department();
		department.setId(1);
		department.setDepartmentName("IT");
		user.setDepartment(department);

		assertEquals("User [id=1, name=Test, designation=Admin, email=test@nucleusteq.com, dateOfJoining="
				+ user.getDateOfJoining()
				+ ", managerId=1, password=Passwordl@123, secretAnswer=My secret Answer"
				+ " reimburseReq=null]", user.toString());
	}

	@Test
	public void testHashCode() {
		User user1 = new User(1, "Test", Designation.Employee, "test@nucleusteq.com", new Date(), 0, "Test@123",
				"MyAnswer", null, null);
		User user2 = new User(2, "Test1", Designation.Manager, "test1@nucleusteq.com", new Date(), 0, "Test1@123",
				"MySecretAnswer", null, null);
		int hashCode1 = user1.hashCode();
		int hashCode2 = user2.hashCode();

		assertNotEquals(hashCode1, hashCode2);
	}

	@Test
	public void testEquals() {
		User user1 = new User(1, "Test", Designation.Employee, "test@nucleusteq.com", new Date(), 0, "Test@123",
				"MyAnswer", null, null);
		User user2 = new User(2, "Test1", Designation.Manager, "test1@nucleusteq.com", new Date(), 0, "Test1@123",
				"MySecretAnswer", null, null);

		User user1Clone = new User(1, "Test", Designation.Employee, "test@nucleusteq.com", new Date(), 0, "Test@123",
				"MyAnswer", null, null);
		User user1DifferentId = new User(4, "Test1", Designation.Manager, "test1@nucleusteq.com", new Date(), 0,
				"Test1@123", "MySecretAnswer", null, null);
		User user1DifferentName = new User(2, "Test2", Designation.Employee, "tes2t@nucleusteq.com", new Date(), 0, "Test2@12345",
				"MySecretAnswer", null, null);

		assertTrue(user1.equals(user1));
		assertTrue(user1.equals(user1Clone));
		assertFalse(user1.equals(user2));
		assertFalse(user1.equals(null));
		assertFalse(user1.equals("User"));
		assertFalse(user1.equals(user1DifferentId));
		assertFalse(user1.equals(user1DifferentName));
	}
}
