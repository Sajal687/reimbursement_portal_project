package com.project.reimburse.dtos.in;

import static org.junit.jupiter.api.Assertions.*;

import com.project.reimburse.entities.Status;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class ReimburseInDtoTest {
    @Mock
    private ReimburseRequestInDto reimburseRequestInDto;

    @BeforeEach
    public void setUp() {
        reimburseRequestInDto = new ReimburseRequestInDto();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(reimburseRequestInDto);
    }

    @Test
    public void testParameterizedConstructor() {
        String expenseType = "Travel";
        Date dateOfExpense = new Date();
        MultipartFile documentFile = new MockMultipartFile("document.pdf", new byte[1024]);
        int amount = 100;
        String comment = "Travel expenses";
        Status claimStatus = Status.Pending;
        String claimDesc = "Pending review";
        String userEmail = "test@nucleusTeq.com";

        reimburseRequestInDto = new ReimburseRequestInDto(
            expenseType, dateOfExpense, documentFile, amount, comment, claimStatus, claimDesc, userEmail
        );

        assertEquals(expenseType, reimburseRequestInDto.getExpenseType());
        assertEquals(dateOfExpense, reimburseRequestInDto.getDateOfExpense());
        assertEquals(documentFile, reimburseRequestInDto.getDocumentFile());
        assertEquals(amount, reimburseRequestInDto.getAmount());
        assertEquals(comment, reimburseRequestInDto.getComment());
        assertEquals(claimStatus, reimburseRequestInDto.getClaimStatus());
        assertEquals(claimDesc, reimburseRequestInDto.getClaimDesc());
        assertEquals(userEmail, reimburseRequestInDto.getUserEmail());
    }

    @Test
    public void testSetAndGetExpenseType() {
        String expenseType = "Food";
        reimburseRequestInDto.setExpenseType(expenseType);
        assertEquals(expenseType, reimburseRequestInDto.getExpenseType());
    }

    @Test
    public void testSetAndGetDateOfExpense() {
        Date dateOfExpense = new Date();
        reimburseRequestInDto.setDateOfExpense(dateOfExpense);
        assertEquals(dateOfExpense, reimburseRequestInDto.getDateOfExpense());
    }

    @Test
    public void testSetAndGetDocumentFile() {
        MultipartFile documentFile = new MockMultipartFile("receipt.png", new byte[1024]);
        reimburseRequestInDto.setDocumentFile(documentFile);
        assertEquals(documentFile, reimburseRequestInDto.getDocumentFile());
    }

    @Test
    public void testSetAndGetAmount() {
        int amount = 200;
        reimburseRequestInDto.setAmount(amount);
        assertEquals(amount, reimburseRequestInDto.getAmount());
    }

    @Test
    public void testSetAndGetComment() {
        String comment = "Food expenses";
        reimburseRequestInDto.setComment(comment);
        assertEquals(comment, reimburseRequestInDto.getComment());
    }

    @Test
    public void testSetAndGetClaimStatus() {
        Status claimStatus = Status.Approved;
        reimburseRequestInDto.setClaimStatus(claimStatus);
        assertEquals(claimStatus, reimburseRequestInDto.getClaimStatus());
    }

    @Test
    public void testSetAndGetClaimDesc() {
        String claimDesc = "Approved";
        reimburseRequestInDto.setClaimDesc(claimDesc);
        assertEquals(claimDesc, reimburseRequestInDto.getClaimDesc());
    }

    @Test
    public void testSetAndGetUserEmail() {
        String userEmail = "test@nucleusTeq.com";
        reimburseRequestInDto.setUserEmail(userEmail);
        assertEquals(userEmail, reimburseRequestInDto.getUserEmail());
    }

    @Test
    public void testToString() {
        ReimburseRequestInDto dto1 = new ReimburseRequestInDto("Travel", new Date(), new MockMultipartFile("document.png", new byte[1024]), 100, "Travel comment", Status.Pending, "Claim Description", "test@nucleusteq.com");

        String expectedToString = "ReimburseRequestInDto [expenseType=Travel, dateOfExpense=" +
                dto1.getDateOfExpense() + ", documentFile=" + dto1.getDocumentFile() +
                ", amount=" + dto1.getAmount() + ", comment=" + dto1.getComment() +
                ", claimStatus=" + dto1.getClaimStatus() + ", claimDesc=" + dto1.getClaimDesc() +
                ", userEmail=" + dto1.getUserEmail() + "]";

        assertEquals(expectedToString, dto1.toString());
    }

    @Test
    public void testHashCode() {
    	Date date = new Date();
    	MockMultipartFile mockMultipartFile = new MockMultipartFile("document.png", new byte[1024]);
        ReimburseRequestInDto dto1 = new ReimburseRequestInDto("Travel", date, mockMultipartFile, 100, "Travel comment", Status.Pending, "Claim Description", "test@nucleusteq.com");

        ReimburseRequestInDto dto2 = new ReimburseRequestInDto("Travel", date, mockMultipartFile, 100, "Travel comment", Status.Pending, "Claim Description", "test@nucleusteq.com");

        int hashCode1 = dto1.hashCode();
        int hashCode2 = dto2.hashCode();
        
        assertEquals(hashCode1, hashCode2);
        

        dto2.setExpenseType("Food");
        hashCode2 = dto2.hashCode();
        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    public void testEquals() {
        ReimburseRequestInDto dto1 = new ReimburseRequestInDto("Travel", new Date(), null, 100, "Travel comment", Status.Pending, "Claim Description", "test@nucleusteq.com");

        ReimburseRequestInDto dto1Clone = new ReimburseRequestInDto("Travel", new Date(), null, 100, "Travel comment", Status.Pending, "Claim Description", "test@nucleusteq.com");

        ReimburseRequestInDto dto1DifferentAmount = new ReimburseRequestInDto(
            "Travel", new Date(), new MockMultipartFile("document.png", new byte[1024]), 200, "Travel comment", Status.Pending, "Claim Description", "test@nucleusteq.com");

        ReimburseRequestInDto dto1DifferentEmail = new ReimburseRequestInDto(
            "Travel", new Date(), new MockMultipartFile("document.png", new byte[1024]), 100, "Travel comment", Status.Pending, "Claim Description", "test@nucleusteq.com");

        assertTrue(dto1.equals(dto1));
        assertTrue(dto1.equals(dto1Clone));
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("Travel"));
        assertFalse(dto1.equals(dto1DifferentAmount));
        assertFalse(dto1.equals(dto1DifferentEmail));
    }
}
