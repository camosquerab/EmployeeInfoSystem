package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {

    @Id
    private Long id;
    private String employeeName;
    private Integer employeeSalary;
    private Integer employeeAge;
    private Integer employeeAnualSalary;
    private String profileImage;

    public void setEmployeeAnualSalary() {
        this.employeeAnualSalary = employeeSalary * 12;
    }

    // Getters y Setters
}