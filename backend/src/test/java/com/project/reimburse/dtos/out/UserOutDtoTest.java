package com.project.reimburse.dtos.out;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.project.reimburse.entities.Designation;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class UserOutDtoTest {
	@Mock
    private UserOutDto userOutDto;

    @BeforeEach
    public void setUp() {
        userOutDto = new UserOutDto();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(userOutDto);
    }

    @Test
    public void testParameterizedConstructor() {
        String name = "Test";
        Designation designation = Designation.Admin;
        String email = "test@nucleusteq.com";
        Date dateOfJoining = new Date();
        int managerId = 1;
        DepartmentOutDtoClass departmentOutDto = new DepartmentOutDtoClass(0 , "HR");

        userOutDto = new UserOutDto(name, designation, email, dateOfJoining, managerId, departmentOutDto);

        assertEquals(name, userOutDto.getName());
        assertEquals(designation, userOutDto.getDesignation());
        assertEquals(email, userOutDto.getEmail());
        assertEquals(dateOfJoining, userOutDto.getDateOfJoining());
        assertEquals(managerId, userOutDto.getManagerId());
        assertEquals(departmentOutDto, userOutDto.getDepartmentOutDto());
    }

    @Test
    public void testSetAndGetters() {
        String name = "Test";
        Designation designation = Designation.Admin;
        String email = "test@nucleusteq.com";
        Date dateOfJoining = new Date();
        int managerId = 1;
        DepartmentOutDtoClass departmentOutDto = new DepartmentOutDtoClass(0 , "HR");

        userOutDto.setName(name);
        userOutDto.setDesignation(designation);
        userOutDto.setEmail(email);
        userOutDto.setDateOfJoining(dateOfJoining);
        userOutDto.setManagerId(managerId);
        userOutDto.setDepartmentOutDto(departmentOutDto);

        assertEquals(name, userOutDto.getName());
        assertEquals(designation, userOutDto.getDesignation());
        assertEquals(email, userOutDto.getEmail());
        assertEquals(dateOfJoining, userOutDto.getDateOfJoining());
        assertEquals(managerId, userOutDto.getManagerId());
        assertEquals(departmentOutDto, userOutDto.getDepartmentOutDto());
    }
    
    @Test
    public void testToString() {
        String name = "Test";
        Designation designation = Designation.Manager;
        String email = "test@nucleusteq.com";
        Date dateOfJoining = new Date();
        int managerId = 0;
        DepartmentOutDtoClass departmentOutDto = new DepartmentOutDtoClass(456, "HR");

        UserOutDto user = new UserOutDto("Test", Designation.Manager, "test@nucleusteq.com", new Date(), 0, departmentOutDto);

        String expectedToString = "UserOutDto [name=" + name + ", designation=" + designation +
                ", email=" + email + ", dateOfJoining=" + dateOfJoining + ", managerId=" + managerId +
                ", departmentOutDto=" + departmentOutDto + "]";
        assertEquals(expectedToString, user.toString());
    }

    @Test
    public void testHashCode() {
        DepartmentOutDtoClass departmentOutDto1 = new DepartmentOutDtoClass(1, "IT");
        DepartmentOutDtoClass departmentOutDto2 = new DepartmentOutDtoClass(2, "Finance");

        UserOutDto user1 = new UserOutDto("Test", Designation.Manager, "test@nucleusteq.com", new Date(), 0, departmentOutDto1);
        UserOutDto user2 = new UserOutDto("Test1", Designation.Manager, "test2@nucleusteq.com", new Date(), 0, departmentOutDto2);

        int hashCode1 = user1.hashCode();
        int hashCode2 = user2.hashCode();

        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    public void testEquals() {
        String name = "Test";
        Designation designation = Designation.Manager;
        String email = "test@nucleusteq.com";
        Date dateOfJoining = new Date();
        int managerId = 0;
        DepartmentOutDtoClass departmentOutDto = new DepartmentOutDtoClass(1, "HR");

        UserOutDto user1 = new UserOutDto(name, designation, email, dateOfJoining, managerId, departmentOutDto);
        UserOutDto user2 = new UserOutDto(name, designation, email, dateOfJoining, managerId, departmentOutDto);
        UserOutDto user3 = new UserOutDto("Test1", designation, email, dateOfJoining, managerId, departmentOutDto);

        assertTrue(user1.equals(user1));
        assertTrue(user1.equals(user2));
        assertFalse(user1.equals(null));
        assertFalse(user1.equals("UserOutDto"));
        assertFalse(user1.equals(user3));
    }
}
