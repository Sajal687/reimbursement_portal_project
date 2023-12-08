package com.project.reimburse.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class ResourceAlreadyExistTest {
	@Test
    public void testDefaultConstructor() {
        ResourceAlreadyExist exception = new ResourceAlreadyExist();
        assertEquals(null, exception.getMessage());
    }

    @Test
    public void testParameterizedConstructor() {
        String errorMessage = "Resource already exists";
        ResourceAlreadyExist exception = new ResourceAlreadyExist(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
