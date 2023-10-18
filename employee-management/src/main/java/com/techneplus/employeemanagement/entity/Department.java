package com.techneplus.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "DEPT")
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Transient
    private double maxSalary;

    @Override
    public String toString() {
        return "Department{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
