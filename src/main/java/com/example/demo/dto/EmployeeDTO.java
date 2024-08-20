package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record EmployeeDTO(
        Long id,
        @JsonAlias("employee_name")
        String employeeName,
        @JsonAlias("employee_salary")
        Integer employeeSalary,
        @JsonAlias("employee_age")
        Integer employeeAge,
        @JsonAlias("employee_name")
        String profileImage
) {}
