package com.project.reimburse.dtos.in;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class DepartmentInDtoClassTest {
	@Mock
    private DepartmentInDtoClass departmentInDto;

    @BeforeEach
    public void setUp() {
        departmentInDto = new DepartmentInDtoClass();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(departmentInDto);
    }

    @Test
    public void testParameterizedConstructor() {
    	int id = 0;
        String departmentName = "HR";
        
        departmentInDto = new DepartmentInDtoClass(id , departmentName);

        assertEquals(departmentName, departmentInDto.getDepartmentName());
    }

    @Test
    public void testSetAndGetId() {
        int id = 0;
        departmentInDto.setId(0);
        assertEquals(id, departmentInDto.getId());
    }
    
    @Test
    public void testSetAndGetDepartmentName() {
        String departmentName = "Finance";
        departmentInDto.setDepartmentName(departmentName);
        assertEquals(departmentName, departmentInDto.getDepartmentName());
    }
    
    @Test
    public void testToString() {
    	DepartmentInDtoClass dto1 = new DepartmentInDtoClass(1, "HR");
        
        String expectedToString = "DepartmentInDtoClass [id=1, departmentName=HR]";
        assertEquals(expectedToString, dto1.toString());
    }

    @Test
    public void testHashCode() {
    	DepartmentInDtoClass dto1 = new DepartmentInDtoClass(1, "HR");
        DepartmentInDtoClass dto2 = new DepartmentInDtoClass(2 , "IT");
        
        int hashCode1 = dto1.hashCode();
        int hashCode2 = dto2.hashCode();

        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    public void testEquals() {
    	DepartmentInDtoClass dto1 = new DepartmentInDtoClass(1, "HR");
        DepartmentInDtoClass dto2 = new DepartmentInDtoClass(2 , "IT");
        
        DepartmentInDtoClass dto1Clone = new DepartmentInDtoClass(1 , "HR");
        DepartmentInDtoClass dto1DifferentName = new DepartmentInDtoClass(1 , "Finance");
        DepartmentInDtoClass dto1DifferentId = new DepartmentInDtoClass(3 , "HR");

        assertTrue(dto1.equals(dto1));
        assertTrue(dto1.equals(dto1Clone));
        assertFalse(dto1.equals(dto2));
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("HR"));
        assertFalse(dto1.equals(dto1DifferentName));
        assertFalse(dto1.equals(dto1DifferentId));
    }
}
