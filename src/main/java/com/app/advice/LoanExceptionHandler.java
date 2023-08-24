package com.app.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.exception.DateNotValid;
import com.app.exception.NotFoundException;

@RestControllerAdvice
public class LoanExceptionHandler {
	

	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST )
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return ResponseEntity.badRequest().body(errors);

}
	
	 @ExceptionHandler(NotFoundException.class)
	    @ResponseStatus(HttpStatus.NOT_FOUND)
	    public Map<String, String> handleLoanNotFoundException(NotFoundException ex) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", ex.getMessage());
	        return errorResponse;
	    }
	 
	 @ExceptionHandler(DateNotValid.class)
	    @ResponseStatus(HttpStatus.FORBIDDEN)
	    public Map<String, String> handleDateNotValidException(DateNotValid ex) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", ex.getMessage());
	        return errorResponse;
	    }
	 
}
