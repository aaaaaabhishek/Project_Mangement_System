package com.modus.employeemanagement.controller;

import com.modus.employeemanagement.exception.EmployeeExistsException;
import com.modus.employeemanagement.payload.EmployeeDto;
import com.modus.employeemanagement.payload.EmployeeSuccessResponse;
import com.modus.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000/")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/createEmployee")
    public ResponseEntity<EmployeeSuccessResponse> crateEmployee(@RequestBody @Valid EmployeeDto employeeDto) throws EmployeeExistsException {
        System.err.println(employeeDto.getEmpId()+" "+employeeDto.getEmpName());

        EmployeeSuccessResponse response = employeeService.createEmployee(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getAllEmployee")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        List<EmployeeDto> listOfEmployees = employeeService.getAllEmployee();
        return ResponseEntity.status(HttpStatus.OK).body(listOfEmployees);
    }
}
