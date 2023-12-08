package com.project.reimburse.dtos.out;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.project.reimburse.entities.Designation;
import com.project.reimburse.entities.Status;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ReimburseRequestOutDtoTest {
	@Mock
    private ReimburseRequestOutDto reimburseRequestOutDto;

    @BeforeEach
    public void setUp() {
        reimburseRequestOutDto = new ReimburseRequestOutDto();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(reimburseRequestOutDto);
    }

    @Test
    public void testParameterizedConstructor() {
    	int id = 1;
        String expenseType = "Travel";
        Date dateOfExpense = new Date();
        String documentFilePath = "path/to/document";
        int amount = 500;
        String comment = "Expense for business trip";
        Status claimStatus = Status.Pending;
        String claimDesc = "Pending approval";
        UserOutDto user = new UserOutDto();
        user.setDateOfJoining(new Date());

        reimburseRequestOutDto = new ReimburseRequestOutDto(
            id , expenseType, dateOfExpense, documentFilePath, amount, comment, claimStatus, claimDesc, user);
        
        assertEquals(id, reimburseRequestOutDto.getId());
        assertEquals(expenseType, reimburseRequestOutDto.getExpenseType());
        assertEquals(dateOfExpense, reimburseRequestOutDto.getDateOfExpense());
        assertEquals(documentFilePath, reimburseRequestOutDto.getDocumentFilePath());
        assertEquals(amount, reimburseRequestOutDto.getAmount());
        assertEquals(comment, reimburseRequestOutDto.getComment());
        assertEquals(claimStatus, reimburseRequestOutDto.getClaimStatus());
        assertEquals(claimDesc, reimburseRequestOutDto.getClaimDesc());
        assertEquals(user, reimburseRequestOutDto.getUser());
    }

    @Test
    public void testSetAndGetters() {
    	int id = 1;
        String expenseType = "Travel";
        Date dateOfExpense = new Date();
        String documentFilePath = "path/to/document";
        int amount = 500;
        String comment = "Expense for business trip";
        Status claimStatus = Status.Pending;
        String claimDesc = "Pending approval";
        UserOutDto user = new UserOutDto();
        user.setDateOfJoining(new Date());

        reimburseRequestOutDto.setId(id);
        reimburseRequestOutDto.setExpenseType(expenseType);
        reimburseRequestOutDto.setDateOfExpense(dateOfExpense);
        reimburseRequestOutDto.setDocumentFilePath(documentFilePath);
        reimburseRequestOutDto.setAmount(amount);
        reimburseRequestOutDto.setComment(comment);
        reimburseRequestOutDto.setClaimStatus(claimStatus);
        reimburseRequestOutDto.setClaimDesc(claimDesc);
        reimburseRequestOutDto.setUser(user);
        
        assertEquals(id, reimburseRequestOutDto.getId());
        assertEquals(expenseType, reimburseRequestOutDto.getExpenseType());
        assertEquals(dateOfExpense, reimburseRequestOutDto.getDateOfExpense());
        assertEquals(documentFilePath, reimburseRequestOutDto.getDocumentFilePath());
        assertEquals(amount, reimburseRequestOutDto.getAmount());
        assertEquals(comment, reimburseRequestOutDto.getComment());
        assertEquals(claimStatus, reimburseRequestOutDto.getClaimStatus());
        assertEquals(claimDesc, reimburseRequestOutDto.getClaimDesc());
        assertEquals(user, reimburseRequestOutDto.getUser());
    }
    
    @Test
    public void testToString() {
        int id = 1;
        String expenseType = "Travel";
        Date dateOfExpense = new Date();
        String documentFilePath = "path/to/document.pdf";
        int amount = 100;
        String comment = "Travel comment";
        Status claimStatus = Status.Pending;
        String claimDesc = "Claim Description";
        UserOutDto user = null;

        ReimburseRequestOutDto request = new ReimburseRequestOutDto(id, expenseType, dateOfExpense, documentFilePath, amount, comment, claimStatus, claimDesc, user);

        String expectedToString = "ReimburseRequestOutDto [id=" + id + ", expenseType=" + expenseType +
                ", dateOfExpense=" + dateOfExpense + ", claimStatus=" + claimStatus +
                ", claimDesc=" + claimDesc + ", user=" + user + "]";
        assertEquals(expectedToString, request.toString());
    }

    @Test
    public void testHashCode() {
        int id1 = 1;
        int id2 = 2;
        String expenseType1 = "Travel";
        String expenseType2 = "Food";
        Date dateOfExpense1 = new Date();
        Date dateOfExpense2 = new Date();
        String documentFilePath1 = "path/to/document1.pdf";
        String documentFilePath2 = "path/to/document2.pdf";
        int amount1 = 100;
        int amount2 = 200;
        String comment1 = "Travel comment";
        String comment2 = "Food comment";
        Status claimStatus1 = Status.Pending;
        Status claimStatus2 = Status.Approved;
        String claimDesc1 = "Claim Description";
        String claimDesc2 = "Food Claim Description";
        UserOutDto user1 = null;
        UserOutDto user2 = new UserOutDto("User1", Designation.Manager, "test@nucleusteq.com", new Date(), 1, new DepartmentOutDtoClass(1, "HR"));

        ReimburseRequestOutDto request1 = new ReimburseRequestOutDto(id1, expenseType1, dateOfExpense1, documentFilePath1, amount1, comment1, claimStatus1, claimDesc1, user1);
        ReimburseRequestOutDto request2 = new ReimburseRequestOutDto(id2, expenseType2, dateOfExpense2, documentFilePath2, amount2, comment2, claimStatus2, claimDesc2, user2);

        int hashCode1 = request1.hashCode();
        int hashCode2 = request2.hashCode();

        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    public void testEquals() {
        int id = 1;
        String expenseType = "Travel";
        Date dateOfExpense = new Date();
        String documentFilePath = "path/to/document.pdf";
        int amount = 100;
        String comment = "Travel comment";
        Status claimStatus = Status.Pending;
        String claimDesc = "Claim Description";
        UserOutDto user1 = null;
        UserOutDto user2 = new UserOutDto("User1", Designation.Manager, "test@nucleusteq.com", new Date(), 1, new DepartmentOutDtoClass(1, "HR"));

        ReimburseRequestOutDto request1 = new ReimburseRequestOutDto(id, expenseType, dateOfExpense, documentFilePath, amount, comment, claimStatus, claimDesc, user1);
        ReimburseRequestOutDto request2 = new ReimburseRequestOutDto(id, expenseType, dateOfExpense, documentFilePath, amount, comment, claimStatus, claimDesc, user1);
        ReimburseRequestOutDto request3 = new ReimburseRequestOutDto(id, expenseType, dateOfExpense, documentFilePath, amount, comment, Status.Approved, claimDesc, user1);
        ReimburseRequestOutDto request4 = new ReimburseRequestOutDto(id, expenseType, dateOfExpense, documentFilePath, amount, comment, claimStatus, claimDesc, user2);

        assertTrue(request1.equals(request1));
        assertTrue(request1.equals(request2));
        assertFalse(request1.equals(null));
        assertFalse(request1.equals("ReimburseRequestOutDto"));
        assertFalse(request1.equals(request3));
        assertFalse(request1.equals(request4));
    }
}
