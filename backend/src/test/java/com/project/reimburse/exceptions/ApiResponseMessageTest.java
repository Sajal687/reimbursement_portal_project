package com.project.reimburse.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class ApiResponseMessageTest {
	    @Test
	    public void testParameterizedConstructor() {
	        ApiResponseMessage responseMessage = new ApiResponseMessage("Test Message", true);

	        assertEquals("Test Message", responseMessage.getMessage());
	        assertEquals(true, responseMessage.isStatus());
	    }

	    @Test
	    public void testDefaultConstructor() {
	    }

	    @Test
	    public void testMessageSetterAndGetter() {
	        ApiResponseMessage responseMessage = new ApiResponseMessage();
	        responseMessage.setMessage("New Message");

	        assertEquals("New Message", responseMessage.getMessage());
	    }

	    @Test
	    public void testStatusSetterAndGetter() {
	        ApiResponseMessage responseMessage = new ApiResponseMessage();
	        responseMessage.setStatus(true);

	        assertEquals(true, responseMessage.isStatus());
	    }
}

