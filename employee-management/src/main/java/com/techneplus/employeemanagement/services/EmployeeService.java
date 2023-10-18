package com.techneplus.employeemanagement.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techneplus.employeemanagement.dto.EmployeeRequest;
import com.techneplus.employeemanagement.entity.Department;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    List getAllEmployees() throws JsonProcessingException;

    ResponseEntity addEmployee(EmployeeRequest employeeRequest);

    Department getDepartmentWithMaxSalary() throws JsonProcessingException;

    List<Department> getAllDepartments();
}
