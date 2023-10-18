package com.techneplus.employeemanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techneplus.employeemanagement.dto.EmployeeRequest;
import com.techneplus.employeemanagement.services.EmployeeService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.ws.rs.*;

@RestController
@RequestMapping(value = "/v1/employee", produces = MediaType.APPLICATION_JSON)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List getAllEmployees() throws JsonProcessingException {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public ResponseEntity addEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
        return employeeService.addEmployee(employeeRequest);
    }


}
