package com.example.demo;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeDTO employeeDTO;
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employeeDTO = new EmployeeDTO(1L, "John Doe", 5000, 30, "image.jpg");
        employee = new Employee();
        employee.setId(1L);
        employee.setEmployeeName("John Doe");
        employee.setEmployeeSalary(5000);
        employee.setEmployeeAge(30);
        employee.setProfileImage("image.jpg");
        employee.setEmployeeAnualSalary();
    }

    @Test
    void getAllEmployees_shouldReturnListOfEmployees() {
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employeeDTO));

        List<Employee> employees = employeeService.getAllEmployees();

        assertEquals(1, employees.size());
        assertEquals(employee.getId(), employees.get(0).getId());
    }

    @Test
    void getEmployeeById_shouldReturnEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(employeeDTO);

        Employee foundEmployee = employeeService.getEmployeeById(1L);

        assertEquals(employee.getId(), foundEmployee.getId());
    }

    @Test
    void getEmployeeById_shouldReturnNullIfNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(null);

        Employee foundEmployee = employeeService.getEmployeeById(1L);

        assertEquals(null, foundEmployee);
    }
}