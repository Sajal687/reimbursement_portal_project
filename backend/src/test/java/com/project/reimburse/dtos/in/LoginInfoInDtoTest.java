package com.project.reimburse.dtos.in;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class LoginInfoInDtoTest {
	@Mock
	private LoginInfoInDto dto;

	@BeforeEach
	public void setUp() {
		dto = new LoginInfoInDto();
	}

	@Test
	public void testGetSetEmail() {
		dto.setEmail("test@nucleusTeq.com");
		assertEquals("test@nucleusTeq.com", dto.getEmail());
	}

	@Test
	public void testGetSetPassword() {
		dto.setPassword("password@123");
		assertEquals("password@123", dto.getPassword());
	}

	@Test
	public void testToString() {
		LoginInfoInDto dto1 = new LoginInfoInDto();
		dto1.setEmail("test@nucleusTeq.com");
		dto1.setPassword("password123");

		LoginInfoInDto dto2 = new LoginInfoInDto();
		dto2.setEmail("test2@nucleusTeq.com");
		dto2.setPassword("password12345");

		String expectedToString = "LoginInfoInDto [email=test@nucleusTeq.com, password=password123]";
		assertEquals(expectedToString, dto1.toString());
	}

	@Test
	public void testHashCode() {
		LoginInfoInDto dto1 = new LoginInfoInDto();
		dto1.setEmail("test@nucleusTeq.com");
		dto1.setPassword("password123");

		LoginInfoInDto dto2 = new LoginInfoInDto();
		dto2.setEmail("test2@nucleusTeq.com");
		dto2.setPassword("password12345");
		
		int hashCode1 = dto1.hashCode();
		int hashCode2 = dto2.hashCode();

		assertNotEquals(hashCode1, hashCode2);
	}

	@Test
	public void testEquals() {
		LoginInfoInDto dto1 = new LoginInfoInDto();
		dto1.setEmail("test@nucleusTeq.com");
		dto1.setPassword("password123");

		LoginInfoInDto dto2 = new LoginInfoInDto();
		dto2.setEmail("test2@nucleusTeq.com");
		dto2.setPassword("password12345");
		
		LoginInfoInDto dto1Clone = new LoginInfoInDto();
		dto1Clone.setEmail("test@nucleusTeq.com");
		dto1Clone.setPassword("password123");

		LoginInfoInDto dto1DifferentEmail = new LoginInfoInDto();
		dto1DifferentEmail.setEmail("test3@nucleusTeq.com");
		dto1DifferentEmail.setPassword("password123");

		LoginInfoInDto dto1DifferentPassword = new LoginInfoInDto();
		dto1DifferentPassword.setEmail("test4@nucleusTeq.com");
		dto1DifferentPassword.setPassword("differentpassword");

		assertTrue(dto1.equals(dto1));
		assertTrue(dto1.equals(dto1Clone));
		assertFalse(dto1.equals(dto2));
		assertFalse(dto1.equals(null));
		assertFalse(dto1.equals("test4@nucleusTeq.com"));
		assertFalse(dto1.equals(dto1DifferentEmail));
		assertFalse(dto1.equals(dto1DifferentPassword));
	}
}
