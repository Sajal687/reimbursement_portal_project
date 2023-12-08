package com.project.reimburse.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ReimburseEntityTest {
	@Mock
    private ReimburseRequest reimburseRequest;

    @BeforeEach
    public void setUp() {
        reimburseRequest = new ReimburseRequest();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(reimburseRequest);
    }

    @Test
    public void testParameterizedConstructor() {
        int id = 1;
        String expenseType = "Travel";
        Date dateOfExpense = new Date();
        String documentFilePath = "path/to/document.pdf";
        int amount = 100;
        String comment = "Expense comment";
        Status claimStatus = Status.Pending;
        String claimDesc = "Claim description";

        reimburseRequest = new ReimburseRequest(id, expenseType, dateOfExpense, documentFilePath, amount, comment,
                claimStatus, claimDesc, new User());

        assertEquals(id, reimburseRequest.getId());
        assertEquals(expenseType, reimburseRequest.getExpenseType());
        assertEquals(dateOfExpense, reimburseRequest.getDateOfExpense());
        assertEquals(documentFilePath, reimburseRequest.getDocumentFilePath());
        assertEquals(amount, reimburseRequest.getAmount());
        assertEquals(comment, reimburseRequest.getComment());
        assertEquals(claimStatus, reimburseRequest.getClaimStatus());
        assertEquals(claimDesc, reimburseRequest.getClaimDesc());
        assertNotNull(reimburseRequest.getUser());
    }

    @Test
    public void testSetAndGetId() {
        int id = 1;
        reimburseRequest.setId(id);
        assertEquals(id, reimburseRequest.getId());
    }

    @Test
    public void testSetAndGetExpenseType() {
        String expenseType = "Food";
        reimburseRequest.setExpenseType(expenseType);
        assertEquals(expenseType, reimburseRequest.getExpenseType());
    }

    @Test
    public void testSetAndGetDateOfExpense() {
        Date dateOfExpense = new Date();
        reimburseRequest.setDateOfExpense(dateOfExpense);
        assertEquals(dateOfExpense, reimburseRequest.getDateOfExpense());
    }

    @Test
    public void testSetAndGetDocumentFilePath() {
        String documentFilePath = "path/to/document.pdf";
        reimburseRequest.setDocumentFilePath(documentFilePath);
        assertEquals(documentFilePath, reimburseRequest.getDocumentFilePath());
    }

    @Test
    public void testSetAndGetAmount() {
        int amount = 100;
        reimburseRequest.setAmount(amount);
        assertEquals(amount, reimburseRequest.getAmount());
    }

    @Test
    public void testSetAndGetComment() {
        String comment = "Expense comment";
        reimburseRequest.setComment(comment);
        assertEquals(comment, reimburseRequest.getComment());
    }

    @Test
    public void testSetAndGetClaimStatus() {
        Status claimStatus = Status.Pending;
        reimburseRequest.setClaimStatus(claimStatus);
        assertEquals(claimStatus, reimburseRequest.getClaimStatus());
    }

    @Test
    public void testSetAndGetClaimDesc() {
        String claimDesc = "Claim description";
        reimburseRequest.setClaimDesc(claimDesc);
        assertEquals(claimDesc, reimburseRequest.getClaimDesc());
    }

    @Test
    public void testSetAndGetUser() {
        User user = new User();
        reimburseRequest.setUser(user);
        assertNotNull(reimburseRequest.getUser());
    }
    


    @Test
    public void testToString() {
        ReimburseRequest request1 = new ReimburseRequest(1, "Travel", new Date(), "path/to/document.pdf", 100, "Travel comment",
                Status.Pending, "Claim Description", null);
        ReimburseRequest request2 = new ReimburseRequest(1, "Food", new Date(), "path/to/document2.pdf", 200, "Food comment",
                Status.Pending, "Food Claim Description", null);

        String expectedToString = "ReimburseRequest [id=1, expenseType=Travel, dateOfExpense="+new Date()+", documentFilePath=path/to/document.pdf, amount=100, comment=Travel comment, claimStatus=Pending, claimDesc=Claim Description]";
        assertEquals(expectedToString, request1.toString());
    }

    @Test
    public void testHashCode() {
    	ReimburseRequest request1 = new ReimburseRequest(1, "Travel", new Date(), "path/to/document.pdf", 100, "Travel comment",
    			Status.Pending, "Claim Description" , null);
    	ReimburseRequest request2 = new ReimburseRequest(1, "Food", new Date(), "path/to/document2.pdf", 200, "Food comment",
    			Status.Pending, "Food Claim Description" , null);
    	
        int hashCode1 = request1.hashCode();
        int hashCode2 = request2.hashCode();

        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    public void testEquals() {
    	ReimburseRequest request1 = new ReimburseRequest(1, "Travel", new Date(), "path/to/document.pdf", 100, "Travel comment",
    			Status.Pending, "Claim Description" , null);
    	ReimburseRequest request2 = new ReimburseRequest(2, "Food", new Date(), "path/to/document2.pdf", 200, "Food comment",
    			Status.Pending, "Food Claim Description" , null);
    	
    	
        ReimburseRequest request1Clone = new ReimburseRequest(1, "Travel", new Date(), "path/to/document.pdf", 100, "Travel comment",
    			Status.Pending, "Claim Description" , null);
        ReimburseRequest request1DifferentId = new ReimburseRequest(99, "Official Supplies", new Date(), "path/to/document.pdf", 100, "Travel comment",
    			Status.Pending, "Claim Description" , null);
        ReimburseRequest request1DifferentDesc = new ReimburseRequest(1, "Travel", new Date(), "path/to/document.pdf", 100, "Travel comment",
    			Status.Pending, "Different Description" , null);

        assertTrue(request1.equals(request1));
        assertTrue(request1.equals(request1Clone));
        assertFalse(request1.equals(request2));
        assertFalse(request1.equals(null));
        assertFalse(request1.equals("ReimburseRequest"));
        assertFalse(request1.equals(request1DifferentId));
        assertFalse(request1.equals(request1DifferentDesc));
    }
}
