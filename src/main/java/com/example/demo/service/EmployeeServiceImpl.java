package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<EmployeeDTO> employeeDTOs = employeeRepository.findAll();
        return employeeDTOs.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
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
        employee.setEmployeeAnualSalary(employeeDTO.employeeSalary() * 12);

        return employee;
    }
}
