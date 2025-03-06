package com.modus.employeemanagement.service.serviceimp;

import com.modus.employeemanagement.entity.Employee;
import com.modus.employeemanagement.exception.EmployeeExistsException;
import com.modus.employeemanagement.payload.EmployeeDto;
import com.modus.employeemanagement.payload.EmployeeSuccessResponse;
import com.modus.employeemanagement.repository.EmployeeRepository;
import com.modus.employeemanagement.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;

    @Autowired
    public EmployeeServiceImp(EmployeeRepository employeeRepository, ModelMapper modelMapper, MessageSource messageSource) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.messageSource = messageSource;
    }

    @Override
    public EmployeeSuccessResponse createEmployee(EmployeeDto employeeDto) throws EmployeeExistsException {

        employeeRepository.findById(employeeDto.getEmpId())
                .ifPresent(emp -> {
                    throw new EmployeeExistsException(
                            messageSource.getMessage(
                                    "EXCEPTION_EMPLOYEE_EXISTS",
                                    new Object[]{emp.getEmpId().toString()},
                                    LocaleContextHolder.getLocale()
                            )
                    );
                });

        Employee employee = convertToEntity(employeeDto);
        employeeRepository.save(employee);
        return new EmployeeSuccessResponse(HttpStatus.CREATED.value(),
                messageSource.getMessage(
                        "SUCCESS_EMPLOYEE_SAVE",
                        null,
                        LocaleContextHolder.getLocale()
                )
        );
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {

        List<Employee> listOfEmployee = employeeRepository.findAll();
        return listOfEmployee.stream().map(this::convertToDto).toList();
    }

    private Employee convertToEntity(EmployeeDto employeeDto) {

        return modelMapper.map(employeeDto, Employee.class);
    }

    private EmployeeDto convertToDto(Employee employee) {

        return modelMapper.map(employee, EmployeeDto.class);
    }
}