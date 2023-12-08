package com.project.reimburse.utils;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

public class MessageConstantsTests {
    @Test
    public void constantMessage() {
    	MessageConstants messageConstants = new MessageConstants();
        assertEquals("Email is already exists", messageConstants.EMAIL_ALREADY_EXISTS);
        
        assertEquals("User Created Successfully", messageConstants.USER_CREATED_SUCCESSFULLY);
        
        assertEquals("User Not Created.", messageConstants.USER_NOT_CREATED);
        
        assertEquals("Category already exists", messageConstants.CATEGORY_ALREADY_EXISTS);
        
        assertEquals("Category doesn't exist", messageConstants.CATEGORY_DOESNT_EXIST);
        
        assertEquals("Department already exists", messageConstants.DEPARTMENT_ALREADY_EXISTS);
        
        assertEquals("Department doesn't exist", messageConstants.DEPARTMENT_DOESNT_EXIST);
        
        assertEquals("User not found with this email.", messageConstants.USER_NOT_FOUND);
        
        assertEquals("Password is not valid", messageConstants.PASSWORD_NOT_VALID);
        
        assertEquals("Manager assigned successfully", messageConstants.EMPLOYEE_ASSIGNED_MESSAGE);
        
        assertEquals("Employee is not assigned to the respected Manager.", messageConstants.EMPLOYEE_NOT_ASSINED_MESSAGE);
        
        assertEquals("Category is Deleted Successfully.", messageConstants.CATEGORY_DELETED_MESSAGE);
        
        assertEquals("C:\\Users\\sajal\\Desktop\\RMS_BACKEND\\Reimbursement_portal_backend\\reimbursement-portal\\src\\main\\resources\\Images\\", messageConstants.UPLOAD_DIRECTORY);
        
        assertEquals("Expense type is required.", messageConstants.EXPENSE_TYPE_REQUIRED);
        
        assertEquals("Claim amount is required.", messageConstants.CLAIM_AMOUNT_REQUIRED);
        
        assertEquals("Amount must be positive.", messageConstants.INVALID_CLAIM_AMOUNT);
        
        assertEquals("Comment is required.", messageConstants.COMMENT_REQUIRED);
        
        assertEquals("Claim not acceptable with this email.", messageConstants.INVALID_USER);
        
        assertEquals("Claim Created Successfully", messageConstants.CLAIM_CREATED_SUCCESS);
        
        assertEquals("Error occured while creating claim request.", messageConstants.CLAIM_CREATED_UNSUCCESSFUL);
        
        assertEquals("message", messageConstants.API_MESSAGE);
    }
}
