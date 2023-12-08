package com.project.reimburse.dtos.in;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.project.reimburse.entities.Designation;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class UserInDtoTest {
	@Mock
    private UserInDto userInDto;

    @BeforeEach
    public void setUp() {
        userInDto = new UserInDto();
    }

    @Test
    public void testDefaultConstructor() {
    }

    @Test
    public void testParameterizedConstructor() {
        String name = "Test Name";
        Designation designation = Designation.Admin;
        String email = "test@nucleusteq.com";
        Date dateOfJoining = new Date();
        int managerId = 1;
        String password = "password123";
        String secretAnswer = "Secret";
        DepartmentInDtoClass department = new DepartmentInDtoClass(0 , "HR");

        userInDto = new UserInDto(
            name, designation, email, dateOfJoining, managerId, password, secretAnswer, department
        );

        assertEquals(name, userInDto.getName());
        assertEquals(designation, userInDto.getDesignation());
        assertEquals(email, userInDto.getEmail());
        assertEquals(dateOfJoining, userInDto.getDateOfJoining());
        assertEquals(managerId, userInDto.getManagerId());
        assertEquals(password, userInDto.getPassword());
        assertEquals(secretAnswer, userInDto.getSecretAnswer());
        assertEquals(department, userInDto.getDepartment());
    }

    @Test
    public void testSetAndGetters() {
        String name = "Test Name";
        Designation designation = Designation.Admin;
        String email = "test@nucleusteq.com";
        Date dateOfJoining = new Date();
        int managerId = 2;
        String password = "password@123";
        String secretAnswer = "secret answer";
        DepartmentInDtoClass department = new DepartmentInDtoClass(0 , "IT");

        userInDto.setName(name);
        userInDto.setDesignation(designation);
        userInDto.setEmail(email);
        userInDto.setDateOfJoining(dateOfJoining);
        userInDto.setManagerId(managerId);
        userInDto.setPassword(password);
        userInDto.setSecretAnswer(secretAnswer);
        userInDto.setDepartment(department);

        assertEquals(name, userInDto.getName());
        assertEquals(designation, userInDto.getDesignation());
        assertEquals(email, userInDto.getEmail());
        assertEquals(dateOfJoining, userInDto.getDateOfJoining());
        assertEquals(managerId, userInDto.getManagerId());
        assertEquals(password, userInDto.getPassword());
        assertEquals(secretAnswer, userInDto.getSecretAnswer());
        assertEquals(department, userInDto.getDepartment());
    }
    
    @Test
    public void testToString() {
        DepartmentInDtoClass department = new DepartmentInDtoClass(1, "IT");
        UserInDto user1 = new UserInDto(
                "Test Name",Designation.Manager, "test@nucleusteq.com",new Date(),
                0, "password123", "Answer123",department);

        String expectedToString = "UserInDto [name=Test Name, designation=Manager, email=test@nucleusteq.com, " +
                "dateOfJoining=" + user1.getDateOfJoining() + ", managerId=0, password=password123, " +
                "secretAnswer=Answer123, department=" + department + "]";

        assertEquals(expectedToString, user1.toString());
    }

    @Test
    public void testHashCode() {
        DepartmentInDtoClass department1 = new DepartmentInDtoClass(1, "IT");
        DepartmentInDtoClass department2 = new DepartmentInDtoClass(2, "HR");

        UserInDto user1 = new UserInDto(
                "Test Name",Designation.Manager,"test@nucleusteq.com",new Date(),
                0, "password123", "Answer123", department1);

        UserInDto user2 = new UserInDto(
                "Test Name2", Designation.Manager,"test2@nucleusteq.com",new Date(),
                0,"password123", "Answer123",department2);

        int hashCode1 = user1.hashCode();
        int hashCode2 = user2.hashCode();

        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    public void testEquals() {
        DepartmentInDtoClass department1 = new DepartmentInDtoClass(1, "IT");
        DepartmentInDtoClass department2 = new DepartmentInDtoClass(2, "HR");

        UserInDto user1 = new UserInDto(
                "Test Name",Designation.Manager,"test@nucleusteq.com",new Date(),
                0, "password123", "Answer123", department1);

        UserInDto user1Clone = new UserInDto(
                "Test Name",Designation.Manager,"test@nucleusteq.com",new Date(),
                0, "password123", "Answer123", department1);

        UserInDto user1DifferentName = new UserInDto(
                "Test Nam2", Designation.Manager,"test2@nucleusteq.com",new Date(),
                0,"password123", "Answer123",department2);

        UserInDto user1DifferentEmail = new UserInDto(
                "Test Name",Designation.Manager,"test2@nucleusteq.com",new Date(),
                0, "password123", "Answer123", department1);

        assertTrue(user1.equals(user1));
        assertTrue(user1.equals(user1Clone));
        assertFalse(user1.equals(null));
        assertFalse(user1.equals("Test"));
        assertFalse(user1.equals(user1DifferentName));
        assertFalse(user1.equals(user1DifferentEmail));
    }
}

