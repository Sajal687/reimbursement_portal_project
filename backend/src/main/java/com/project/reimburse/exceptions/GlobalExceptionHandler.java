package com.project.reimburse.exceptions;

import com.project.reimburse.dtos.out.ApiError;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/**
 * This is a GlobalExceptionHandler Class.
 */
@SuppressWarnings("serial")
@RestControllerAdvice
public class GlobalExceptionHandler extends Exception {

  /**
   * This is a handler to deal with resource not found exception.
   *
   * @param ex Exception instance of Resource Not Found.
   *
   * @return ApiResponse.
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponseMessage> resourceNotFoundException(
       final ResourceNotFoundException ex) {
    final String msg = ex.getMessage();
    return new ResponseEntity<ApiResponseMessage>(
        new ApiResponseMessage(msg, true), HttpStatus.BAD_REQUEST);
  }

  /**
   * This method is used to deal with not valid method argument.
   *
   * @param ex Exception instance of invalid argument.
   * @return key-value pair where key denotes field name where exception
   *     occur and value is the actual value.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleMethodArgumentNotValidException(
      final MethodArgumentNotValidException ex) {
    List<String> list = new ArrayList<String>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String errorMessage = error.getDefaultMessage();
      list.add(errorMessage);
    });
    ApiError apiError = new ApiError(list, 400, "Validation Failed.");
    return new ResponseEntity<ApiError>(apiError,
       HttpStatus.BAD_REQUEST);
  }

  /**
   * This is a handler used to deal with data violation during Validation time.
   *
   *@param exception Exception instance of data violation.
   *
   * @return Key-value pair where key denotes field name and value denotes the cause of occurance .
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<String> handleDataIntegrityViolationException(
       final DataIntegrityViolationException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
  }

  /**
   * ResourceAlreadyExist handler.
   *
   * @param ex ResourceAlreadyExist type parameter.
   * @return message
   */
  @ExceptionHandler(ResourceAlreadyExist.class)
   public ResponseEntity<ApiError> alreadyExistExceptionHandler(
      final ResourceAlreadyExist ex) {
    List<String> list = new ArrayList<String>();
    list.add(ex.getMessage());
    ApiError apiError = new ApiError(list, 409, "Resource Already Exist");
    return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
  }

  /**
    * Bad Request Exception handler.
    *
    * @param ex BadRequestException type parameter.
    * @return ResponseEntity(Message)
    */
  @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> badRequestExceptionHandler(
      final BadRequestException ex) {
    List<String> list = new ArrayList<String>();
    list.add(ex.getMessage());
    ApiError apiError = new ApiError(list, 400, "Bad Request");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
  }

  /**
     *  Bad credential Exception handler.
     *
     * @param ex BadCredentialsException type parameter.
     * @return ResponseEntity(Message)
     */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiError> badCredentialsExceptionHandler(
      final BadCredentialsException ex) {
    String errorMessage = ex.getMessage();
    ApiError apiError = new ApiError(Collections.singletonList(errorMessage),
              401, "Login failed due to incorrect credentials");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
  }

  /**
  *This is a Custom validation method for handling exception due to binding.
  *
  * @param ex Object of BindException.
  * @return ApiError response.
  */
  @ExceptionHandler(BindException.class)
  public ResponseEntity<ApiError> handleBindException(final BindException ex) {
    String errorMessage = ex.getMessage();
    ApiError apiError = new ApiError(Collections.singletonList(errorMessage),
        401, "Please fill valid form attribute.");
    List<String> errorFieldList = new ArrayList<>();
    for (FieldError fieldError : ex.getFieldErrors()) {
      errorFieldList.add(fieldError.getDefaultMessage());
    }
    apiError.setMessages(errorFieldList);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
  }
}

