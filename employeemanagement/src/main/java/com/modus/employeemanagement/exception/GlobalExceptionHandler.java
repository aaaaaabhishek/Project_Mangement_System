package com.modus.employeemanagement.exception;

import com.modus.employeemanagement.payload.EmployeeErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    //This ExceptionHandler handles when exception comes during JSON parse because of incorrect JSON format
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception) {
        //If required make proper Response class for this response message
        return new ResponseEntity<>(
                messageSource.getMessage(
                        "EXCEPTION_JSON_INVALID",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    //This ExceptionHandler handles exception when there is no mapping with request url
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> noResourceFoundExceptionHandler(NoResourceFoundException exception){
        //If required make proper Response class for this response message
        return new ResponseEntity<>(
                messageSource.getMessage(
                        "EXCEPTION_URL_NOT_FOUND",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    //This ExceptionHandler is only for handling exceptions that occur during EmployeeDto validation.
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EmployeeErrorResponse> handleInvalidEmployeeArgs(MethodArgumentNotValidException exception) {

        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors())
            errorMap.put(error.getField(), error.getDefaultMessage());
        EmployeeErrorResponse employeeErrorResponse = new EmployeeErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                messageSource.getMessage(
                        "EXCEPTION_VALIDATION_FAILED",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                errorMap
        );
        return new ResponseEntity<>(employeeErrorResponse, HttpStatus.BAD_REQUEST);
    }

    //This ExceptionHandler handles when EmployeeExistsException occur
    @ExceptionHandler(EmployeeExistsException.class)
    public ResponseEntity<EmployeeErrorResponse> handleEmployeeExistsException(EmployeeExistsException exception) {

        EmployeeErrorResponse employeeErrorResponse = new EmployeeErrorResponse(
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        );
        return new ResponseEntity<>(employeeErrorResponse, HttpStatus.CONFLICT);
    }
}
