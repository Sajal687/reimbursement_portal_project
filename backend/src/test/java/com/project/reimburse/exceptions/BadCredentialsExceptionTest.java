package com.project.reimburse.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class BadCredentialsExceptionTest {
	@Test
	public void testDefaultConstructor() {
	}

	@Test
	public void testParameterizedConstructor() {
		String errorMessage = "Invalid credentials";
		BadCredentialsException exception = new BadCredentialsException(errorMessage);
		assertEquals(errorMessage, exception.getMessage());
	}
}
