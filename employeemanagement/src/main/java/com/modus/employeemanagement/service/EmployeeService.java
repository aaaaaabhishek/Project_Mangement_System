package com.modus.employeemanagement.service;

import com.modus.employeemanagement.payload.EmployeeDto;
import com.modus.employeemanagement.payload.EmployeeSuccessResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeSuccessResponse createEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> getAllEmployee();
}
