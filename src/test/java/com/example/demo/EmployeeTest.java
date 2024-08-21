package com.example.demo;

import com.example.demo.model.Employee;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class EmployeeTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Long id = 1L;
        String employeeName = "John Doe";
        int employeeSalary = 5000;
        int employeeAge = 30;
        String profileImage = "profile.jpg";

        // Act
        Employee employee = new Employee(id, employeeName, employeeSalary, employeeAge, profileImage);

        // Assert
        assertThat(employee.getId()).isEqualTo(id);
        assertThat(employee.getEmployeeName()).isEqualTo(employeeName);
        assertThat(employee.getEmployeeSalary()).isEqualTo(employeeSalary);
        assertThat(employee.getEmployeeAge()).isEqualTo(employeeAge);
        assertThat(employee.getProfileImage()).isEqualTo(profileImage);
    }

    @Test
    void testSetEmployeeAnualSalary() {
        // Arrange
        Employee employee = new Employee();
        employee.setEmployeeSalary(5000);

        // Act
        employee.setEmployeeAnualSalary();

        // Assert
        assertThat(employee.getEmployeeAnualSalary()).isEqualTo(5000 * 12);
    }

    @Test
    void testDefaultConstructor() {
        // Act
        Employee employee = new Employee();

        // Assert
        assertThat(employee.getId()).isNull();
        assertThat(employee.getEmployeeName()).isNull();
        assertThat(employee.getEmployeeSalary()).isNull();
        assertThat(employee.getEmployeeAge()).isNull();
        assertThat(employee.getProfileImage()).isNull();
        assertThat(employee.getEmployeeAnualSalary()).isNull();
    }

    @Test
    void testSetterMethods() {
        // Arrange
        Employee employee = new Employee();
        Long id = 1L;
        String employeeName = "Jane Doe";
        int employeeSalary = 4000;
        int employeeAge = 25;
        String profileImage = "profile2.jpg";

        // Act
        employee.setId(id);
        employee.setEmployeeName(employeeName);
        employee.setEmployeeSalary(employeeSalary);
        employee.setEmployeeAge(employeeAge);
        employee.setProfileImage(profileImage);
        employee.setEmployeeAnualSalary();

        // Assert
        assertThat(employee.getId()).isEqualTo(id);
        assertThat(employee.getEmployeeName()).isEqualTo(employeeName);
        assertThat(employee.getEmployeeSalary()).isEqualTo(employeeSalary);
        assertThat(employee.getEmployeeAge()).isEqualTo(employeeAge);
        assertThat(employee.getProfileImage()).isEqualTo(profileImage);
        assertThat(employee.getEmployeeAnualSalary()).isEqualTo(employeeSalary * 12);
    }
}
