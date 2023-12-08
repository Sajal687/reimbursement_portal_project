package com.project.reimburse.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class DepartmentEntityTest {
	@Mock
	private Department department;

	@BeforeEach
	public void setUp() {
		department = new Department();
	}

	@Test
	public void testDefaultConstructor() {
		assertNotNull(department);
	}

	@Test
	public void testParameterizedConstructor() {
		int id = 1;
		String departmentName = "HR";

		List<User> users = new ArrayList<>();
		User user1 = new User();
		User user2 = new User();
		users.add(user1);
		users.add(user2);

		department = new Department(id, departmentName, users);

		assertEquals(id, department.getId());
		assertEquals(departmentName, department.getDepartmentName());
		assertEquals(users, department.getUser());
	}

	@Test
	public void testSetAndGetId() {
		int id = 1;
		department.setId(id);
		assertEquals(id, department.getId());
	}

	@Test
	public void testSetAndGetDepartmentName() {
		String departmentName = "Finance";
		department.setDepartmentName(departmentName);
		assertEquals(departmentName, department.getDepartmentName());
	}

	@Test
	public void testSetAndGetUser() {
		List<User> users = new ArrayList<>();
		User user1 = new User();
		User user2 = new User();
		users.add(user1);
		users.add(user2);
		department.setUser(users);
		assertEquals(users, department.getUser());
	}

	@Test
	public void testToString() {
		Department department1;
		Department department2;

		department1 = new Department(1, "HR", null);
		department2 = new Department(2, "IT", null);
		String expectedToString = "Department [id=1, departmentName=HR, user=null]";
		assertEquals(expectedToString, department1.toString());
	}

	@Test
	public void testHashCode() {
		Department department1;
		Department department2;

		department1 = new Department(1, "HR", null);
		department2 = new Department(2, "IT", null);
		int hashCode1 = department1.hashCode();
		int hashCode2 = department2.hashCode();

		assertNotEquals(hashCode1, hashCode2);
	}

	@Test
	public void testEquals() {
		Department department1;
		Department department2;

		department1 = new Department(1, "HR", null);
		department2 = new Department(2, "IT", null);
		Department department1Clone = new Department(1, "HR", null);
		Department department1DifferentId = new Department(3, "HR", null);
		Department department1DifferentName = new Department(1, "Finance", null);

		assertTrue(department1.equals(department1));
		assertTrue(department1.equals(department1Clone));
		assertFalse(department1.equals(department2));
		assertFalse(department1.equals(null));
		assertFalse(department1.equals("HR"));
		assertFalse(department1.equals(department1DifferentId));
		assertFalse(department1.equals(department1DifferentName));
	}

}
