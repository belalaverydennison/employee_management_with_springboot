package com.techneplus.employeemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeRequest {


    @NotBlank(message = "Name field should not be Null or Empty.")
    private String name;
    @NotNull(message = "Salary field should not be Null or Empty.")

    private Double salary;

    private Long departmentId;

    @NotBlank(message = "Employee ID field should not be Null or Empty.")
    private String employeeId;


    @NotBlank(message = "Department Name field should not be Null or Empty.")
    private String departmentName;


    @NotBlank(message = "Date Of Birth field should not be Null or Empty.")
    private String dateOfBirth;
}
