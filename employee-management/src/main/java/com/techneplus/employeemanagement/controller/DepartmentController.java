package com.techneplus.employeemanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techneplus.employeemanagement.entity.Department;
import com.techneplus.employeemanagement.services.EmployeeService;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/department", produces = MediaType.APPLICATION_JSON)
public class DepartmentController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    @Produces(MediaType.APPLICATION_JSON)
    public List<Department> getAllDepartments(){
        return employeeService.getAllDepartments();
    }


    @GetMapping("/max-salary")
    @Produces(MediaType.APPLICATION_JSON)
    public Department getDepartmentWithMaxSalary() throws JsonProcessingException {
        return employeeService.getDepartmentWithMaxSalary();
    }
}
