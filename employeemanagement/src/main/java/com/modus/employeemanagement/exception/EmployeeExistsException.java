package com.modus.employeemanagement.exception;

public class EmployeeExistsException extends RuntimeException{

    public EmployeeExistsException(String message){
        super(message);
    }
}
