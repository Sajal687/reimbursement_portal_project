package com.project.reimburse.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class BadRequestExceptionTest {
	@Test
	public void testDefaultConstructor() {
	}

	@Test
	public void testParameterizedConstructor() {
		String errorMessage = "Bad request";
		BadRequestException exception = new BadRequestException(errorMessage);
		assertEquals(errorMessage, exception.getMessage());
	}
}
