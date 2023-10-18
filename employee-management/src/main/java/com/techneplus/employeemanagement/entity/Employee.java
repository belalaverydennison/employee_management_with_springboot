package com.techneplus.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "EMP")
@Data
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Double salary;

    private String dateOfBirth;

    private String employeeId;

    @ManyToOne
    private Department department;

    public Employee(String name, Double salary, String dateOfBirth, String employeeId, Department department) {
        this.name = name;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
        this.employeeId = employeeId;
    }

}
