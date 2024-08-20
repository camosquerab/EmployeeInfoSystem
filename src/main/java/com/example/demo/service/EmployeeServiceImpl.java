package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.Response;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RestTemplate restTemplate;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RestTemplate restTemplate) {
        this.employeeRepository = employeeRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return null;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        EmployeeDTO employeeDTO = employeeRepository.findById(id);

        if (employeeDTO == null) {
            return null;
        }

        return mapToEntity(employeeDTO);
    }

    private Employee mapToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.id());
        employee.setEmployeeName(employeeDTO.employeeName());
        employee.setEmployeeSalary(employeeDTO.employeeSalary());
        employee.setEmployeeAge(employeeDTO.employeeAge());
        employee.setProfileImage(employeeDTO.profileImage());

        return employee;
    }
}