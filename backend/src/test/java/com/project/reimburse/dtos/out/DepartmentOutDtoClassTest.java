package com.project.reimburse.dtos.out;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class DepartmentOutDtoClassTest {
	@Mock
    private DepartmentOutDtoClass departmentOutDto;

    @BeforeEach
    public void setUp() {
        departmentOutDto = new DepartmentOutDtoClass();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(departmentOutDto);
        assertNull(departmentOutDto.getDepartmentName());
    }

    @Test
    public void testParameterizedConstructor() {
    	int id = 0;
        String departmentName = "HR";

        departmentOutDto = new DepartmentOutDtoClass(0 , departmentName);

        assertEquals(departmentName, departmentOutDto.getDepartmentName());
    }

    @Test
    public void testSetAndGetters() {
        String departmentName = "Finance";

        departmentOutDto.setDepartmentName(departmentName);

        assertEquals(departmentName, departmentOutDto.getDepartmentName());
    }
    
    @Test
    public void testToString() {
        DepartmentOutDtoClass dto1 = new DepartmentOutDtoClass(1, "IT");

        String expectedToString = "DepartmentOutDtoClass [id=1, departmentName=IT]";
        assertEquals(expectedToString, dto1.toString());
    }

    @Test
    public void testHashCode() {
        DepartmentOutDtoClass dto1 = new DepartmentOutDtoClass(1, "IT");
        DepartmentOutDtoClass dto2 = new DepartmentOutDtoClass(2, "HR");

        int hashCode1 = dto1.hashCode();
        int hashCode2 = dto2.hashCode();

        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    public void testEquals() {
        DepartmentOutDtoClass dto1 = new DepartmentOutDtoClass(1, "IT");
        DepartmentOutDtoClass dto1Clone = new DepartmentOutDtoClass(1, "IT");
        DepartmentOutDtoClass dto1DifferentId = new DepartmentOutDtoClass(2, "IT");
        DepartmentOutDtoClass dto1DifferentName = new DepartmentOutDtoClass(1, "HR");

        assertTrue(dto1.equals(dto1));
        assertTrue(dto1.equals(dto1Clone));
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("IT"));
        assertFalse(dto1.equals(dto1DifferentId));
        assertFalse(dto1.equals(dto1DifferentName));
    }
    
    
}
