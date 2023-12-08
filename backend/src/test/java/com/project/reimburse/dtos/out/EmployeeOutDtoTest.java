package com.project.reimburse.dtos.out;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.project.reimburse.entities.Designation;

import java.util.Date;

public class EmployeeOutDtoTest {
    private EmployeeOutDto employeeOutDto;

    @BeforeEach
    public void setUp() {
        employeeOutDto = new EmployeeOutDto();
    }

    @Test
    public void testDefaultConstructor() {
    }

    @Test
    public void testParameterizedConstructor() {
        String name = "Test";
        Designation designation = Designation.Manager;
        String email = "test@nucleusteq.com";
        Date dateOfJoining = new Date();
        int managerId = 1;
        String managerName = "Manager";
        DepartmentOutDtoClass departmentOutDtoClass = new DepartmentOutDtoClass();

        employeeOutDto = new EmployeeOutDto(
            name, designation, email, dateOfJoining, managerId, managerName, departmentOutDtoClass);

        assertEquals(name, employeeOutDto.getName());
        assertEquals(designation, employeeOutDto.getDesignation());
        assertEquals(email, employeeOutDto.getEmail());
        assertEquals(dateOfJoining, employeeOutDto.getDateOfJoining());
        assertEquals(managerId, employeeOutDto.getManagerId());
        assertEquals(managerName, employeeOutDto.getManagerName());
        assertEquals(departmentOutDtoClass, employeeOutDto.getDepartmentOutDto());
    }

    @Test
    public void testSetAndGetters() {
        String name = "Test";
        Designation designation = Designation.Employee;
        String email = "test@nucleusteq.com";
        Date dateOfJoining = new Date();
        int managerId = 2;
        String managerName = "Manager";
        DepartmentOutDtoClass departmentOutDtoClass = new DepartmentOutDtoClass();

        employeeOutDto.setName(name);
        employeeOutDto.setDesignation(designation);
        employeeOutDto.setEmail(email);
        employeeOutDto.setDateOfJoining(dateOfJoining);
        employeeOutDto.setManagerId(managerId);
        employeeOutDto.setManagerName(managerName);
        employeeOutDto.setDepartmentOutDto(departmentOutDtoClass);

        assertEquals(name, employeeOutDto.getName());
        assertEquals(designation, employeeOutDto.getDesignation());
        assertEquals(email, employeeOutDto.getEmail());
        assertEquals(dateOfJoining, employeeOutDto.getDateOfJoining());
        assertEquals(managerId, employeeOutDto.getManagerId());
        assertEquals(managerName, employeeOutDto.getManagerName());
        assertEquals(departmentOutDtoClass, employeeOutDto.getDepartmentOutDto());
    }
    
    @Test
    public void testToString() {
        DepartmentOutDtoClass departmentDto = new DepartmentOutDtoClass(1, "IT");
        EmployeeOutDto dto1 = new EmployeeOutDto("Test", Designation.Manager, "test@nucleusteq.com",
            new Date(), 101, "ManagerName", departmentDto);

        String expectedToString = "EmployeeOutDto [name=Test, designation=Manager, email=test@nucleusteq.com, " +
            "dateOfJoining=" + dto1.getDateOfJoining() + ", managerId=101, managerName=ManagerName, " +
            "departmentOutDto=DepartmentOutDtoClass [id=1, departmentName=IT]]";
        assertEquals(expectedToString, dto1.toString());
    }

    @Test
    public void testHashCode() {
        DepartmentOutDtoClass departmentDto1 = new DepartmentOutDtoClass(1, "IT");
        DepartmentOutDtoClass departmentDto2 = new DepartmentOutDtoClass(2, "HR");
        EmployeeOutDto dto1 = new EmployeeOutDto("Test", Designation.Manager, "test@nucleusteq.com",
                new Date(), 101, "Manager1", departmentDto1);
        
        EmployeeOutDto dto2 = new EmployeeOutDto("Test1", Designation.Manager, "test1@nucleusteq.com",
            new Date(), 102, "Manager2", departmentDto2);

        int hashCode1 = dto1.hashCode();
        int hashCode2 = dto2.hashCode();

        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    public void testEquals() {
        DepartmentOutDtoClass departmentDto1 = new DepartmentOutDtoClass(1, "IT");
        DepartmentOutDtoClass departmentDto2 = new DepartmentOutDtoClass(2, "HR");
        EmployeeOutDto dto1 = new EmployeeOutDto("Test", Designation.Manager, "test@nucleusteq.com",
                new Date(), 101, "ManagerName", departmentDto1);
        
        EmployeeOutDto dto1Clone = new EmployeeOutDto("Test", Designation.Manager, "test@nucleusteq.com",
                new Date(), 101, "ManagerName", departmentDto1);
        
        EmployeeOutDto dto1DifferentName = new EmployeeOutDto("Test1", Designation.Manager, "test2@nucleusteq.com",
                new Date(), 102, "ManagerName", departmentDto2);
        
        EmployeeOutDto dto1DifferentEmail = new EmployeeOutDto("Test1", Designation.Manager, "test2@nucleusteq.com",
                new Date(), 102, "ManagerName", departmentDto2);

        assertTrue(dto1.equals(dto1));
        assertTrue(dto1.equals(dto1Clone));
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals(dto1DifferentName));
        assertFalse(dto1.equals(dto1DifferentEmail));
        assertFalse(dto1.equals(dto1DifferentName));
    }
}
