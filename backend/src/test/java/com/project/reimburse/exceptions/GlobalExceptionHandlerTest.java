package com.project.reimburse.exceptions;

import com.project.reimburse.dtos.in.ReimburseRequestInDto;
import com.project.reimburse.dtos.out.ApiError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

	public class GlobalExceptionHandlerTest {

	    @InjectMocks
	    private GlobalExceptionHandler globalExceptionHandler;

	    @Mock
	    private ResourceNotFoundException resourceNotFoundException;
	    @Mock
	    private MethodArgumentNotValidException methodArgumentNotValidException;
	    @Mock
	    private DataIntegrityViolationException dataIntegrityViolationException;
	    @Mock
	    private ResourceAlreadyExist resourceAlreadyExist;
	    @Mock
	    private BadRequestException badRequestException;
	    @Mock
	    private BadCredentialsException badCredentialsException;
	    
	    @BeforeEach
	    public void setUp() {
	    	MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testResourceNotFoundException() {
	        when(resourceNotFoundException.getMessage()).thenReturn("Resource not found");
	        
	        ResponseEntity<ApiResponseMessage> response = globalExceptionHandler.resourceNotFoundException(resourceNotFoundException);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        assertEquals("Resource not found", response.getBody().getMessage());

	        verify(resourceNotFoundException).getMessage();
	    }
	    
	    @Test
		void testMethodArgumentNotValidException() {
			BindingResult bindingResult = mock(BindingResult.class);
			
			FieldError fieldError = new FieldError("ObjectName", "field1", "First message");
			
			when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));
			
			MethodArgumentNotValidException exception = new MethodArgumentNotValidException((MethodParameter)null, bindingResult);
			ResponseEntity<ApiError> response = globalExceptionHandler.handleMethodArgumentNotValidException(exception);
			
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		}

	    @Test
	    public void testHandleDataIntegrityViolationException() {
	        when(dataIntegrityViolationException.getMessage()).thenReturn("Data integrity violation");

	        ResponseEntity<String> response = globalExceptionHandler.handleDataIntegrityViolationException(dataIntegrityViolationException);

	        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	        assertEquals("Data integrity violation", response.getBody());

	        verify(dataIntegrityViolationException).getMessage();
	    }

	    @Test
	    public void testAlreadyExistExceptionHandler() {
	        when(resourceAlreadyExist.getMessage()).thenReturn("Resource already exists");

	        ResponseEntity<ApiError> response = globalExceptionHandler.alreadyExistExceptionHandler(resourceAlreadyExist);

	        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	        assertEquals("Resource already exists", response.getBody().getMessages().get(0));

	        verify(resourceAlreadyExist).getMessage();
	    }

	    @Test
	    public void testBadRequestExceptionHandler() {
	        when(badRequestException.getMessage()).thenReturn("Bad request");

	        ResponseEntity<ApiError> response = globalExceptionHandler.badRequestExceptionHandler(badRequestException);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        assertEquals("Bad request", response.getBody().getMessages().get(0));

	        verify(badRequestException).getMessage();
	    }

	    @Test
	    public void testBadCredentialsExceptionHandler() {
	        when(badCredentialsException.getMessage()).thenReturn("Incorrect credentials");

	        ResponseEntity<ApiError> response = globalExceptionHandler.badCredentialsExceptionHandler(badCredentialsException);

	        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	        assertEquals("Incorrect credentials", response.getBody().getMessages().get(0));

	        verify(badCredentialsException).getMessage();
	    }
	    
	    @Test
	    void testHandleBindException() {
	        ReimburseRequestInDto formObject = new ReimburseRequestInDto();
	        formObject.setExpenseType("Food");
	        formObject.setAmount(0);
	        
	        BindException bindException = new BindException(formObject, "formObject");
	        bindException.rejectValue("", "error_code1", "Error message 1");
	        bindException.rejectValue("", "error_code2", "Error message 2");

	        ResponseEntity<ApiError> responseEntity = globalExceptionHandler.handleBindException(bindException);

	        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	        ApiError apiError = responseEntity.getBody();
	        assertEquals(401, apiError.getErrorCode());
	        assertEquals("Please fill valid form attribute.", apiError.getAdditionalInfo());
	    }
	}

