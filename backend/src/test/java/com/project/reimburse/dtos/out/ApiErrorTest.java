package com.project.reimburse.dtos.out;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ApiErrorTest {
	@Mock
    private ApiError apiError;

    @Test
    public void testDefaultConstructor() {
    }

    @Test
    public void testParameterizedConstructor() {
    	List<String> messages = Arrays.asList("Error 1", "Error 2");
        int errorCode = 400;
        String additionalInfo = "Validation failed";
        apiError = new ApiError(messages, errorCode, additionalInfo);
        
        List<String> msg = Arrays.asList("Error 1", "Error 2");
        int code = 400;
        String info = "Validation failed";

        assertEquals(msg, apiError.getMessages());
        assertEquals(code, apiError.getErrorCode());
        assertEquals(info, apiError.getAdditionalInfo());
    }

    @Test
    public void testSetAndGetMessages() {
    	List<String> messages = Arrays.asList("Error 1", "Error 2");
        int errorCode = 400;
        String additionalInfo = "Validation failed";
        apiError = new ApiError(messages, errorCode, additionalInfo);
        
        List<String> newMessages = Arrays.asList("New Error 1", "New Error 2");
        apiError.setMessages(newMessages);

        assertEquals(newMessages, apiError.getMessages());
    }

    @Test
    public void testSetAndGetErrorCode() {
    	List<String> messages = Arrays.asList("Error 1", "Error 2");
        int errorCode = 400;
        String additionalInfo = "Validation failed";
        apiError = new ApiError(messages, errorCode, additionalInfo);
        
        int newErrorCode = 500;
        apiError.setErrorCode(newErrorCode);

        assertEquals(newErrorCode, apiError.getErrorCode());
    }

    @Test
    public void testSetAndGetAdditionalInfo() {
    	List<String> messages = Arrays.asList("Error 1", "Error 2");
        int errorCode = 400;
        String additionalInfo = "Validation failed";
        apiError = new ApiError(messages, errorCode, additionalInfo);
        
        String newAdditionalInfo = "New Validation failed";
        apiError.setAdditionalInfo(newAdditionalInfo);

        assertEquals(newAdditionalInfo, apiError.getAdditionalInfo());
    }

    @Test
    public void testHashCode() {
    	List<String> messages = Arrays.asList("Error 1", "Error 2");
        int errorCode = 400;
        String additionalInfo = "Validation failed";
        apiError = new ApiError(messages, errorCode, additionalInfo);
        
        ApiError apiError1 = new ApiError(Arrays.asList("Error 1", "Error 2"), 400, "Validation failed");
        ApiError apiError2 = new ApiError(Arrays.asList("Error 3", "Error 4"), 400, "Validation failed");

        assertEquals(apiError.hashCode(), apiError1.hashCode());
        assertNotEquals(apiError.hashCode(), apiError2.hashCode());
    }

    @Test
    public void testEquals() {
    	List<String> messages = Arrays.asList("Error 1", "Error 2");
        int errorCode = 400;
        String additionalInfo = "Validation failed";
        apiError = new ApiError(messages, errorCode, additionalInfo);
        
        ApiError apiError1 = new ApiError(Arrays.asList("Error 1", "Error 2"), 400, "Validation failed");
        ApiError apiError2 = new ApiError(Arrays.asList("Error 3", "Error 4"), 400, "Validation failed");

        assertEquals(apiError, apiError1);
        assertNotEquals(apiError, apiError2);
        assertNotEquals(apiError, null);
        assertNotEquals(apiError, "Validation failed");
    }
}
