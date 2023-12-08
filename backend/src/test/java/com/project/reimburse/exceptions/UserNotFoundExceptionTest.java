package com.project.reimburse.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class UserNotFoundExceptionTest {
	@Test
	public void testParameterizedConstructor() {
		String errorMessage = "User not found";
		UserNotFoundException exception = new UserNotFoundException(errorMessage);
		assertEquals(errorMessage, exception.getMessage());
	}
}
