package com.example.demo;

import com.example.demo.controller.EmployeeController;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Collections;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setEmployeeName("John Doe");
        employee.setEmployeeSalary(5000);
        employee.setEmployeeAge(30);
        employee.setProfileImage("image.jpg");
        employee.setEmployeeAnualSalary();
    }

    @Test
    void getAllEmployees_shouldReturnListOfEmployees() throws Exception {
        Mockito.when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(employee));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeName").value(employee.getEmployeeName()));
    }

    @Test
    void getEmployeeById_shouldReturnEmployee() throws Exception {
        Mockito.when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeName").value(employee.getEmployeeName()));
    }

    @Test
    void getEmployeeById_shouldReturnNotFound() throws Exception {
        Mockito.when(employeeService.getEmployeeById(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}