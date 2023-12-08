package com.project.reimburse.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class ResourceNotFoundExceptionTest {
	@Test
    public void testParameterizedConstructor() {
        ResourceNotFoundException exception = new ResourceNotFoundException("ResourceName", "FieldName", 123);

        assertEquals("ResourceName is not found with FieldName : 123", exception.getMessage());
        assertEquals("ResourceName", exception.getResourceName());
        assertEquals("FieldName", exception.getFieldName());
        assertEquals(123, exception.getFieldValue());
    }

    @Test
    public void testGettersAndSetters() {
        ResourceNotFoundException exception = new ResourceNotFoundException("ResourceName", "FieldName", 123);
        
        exception.setResourceName("NewResourceName");
        exception.setFieldName("NewFieldName");
        exception.setFieldValue(456);
        
        assertEquals("NewResourceName", exception.getResourceName());
        assertEquals("NewFieldName", exception.getFieldName());
        assertEquals(456, exception.getFieldValue());
    }
}
