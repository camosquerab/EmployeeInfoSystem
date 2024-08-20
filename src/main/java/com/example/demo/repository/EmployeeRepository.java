package com.example.demo.repository;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.Response;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class EmployeeRepository {

    private final String BASE_URL = "http://dummy.restapiexample.com/api/v1";
    private final RestTemplate restTemplate;

    public EmployeeRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<EmployeeDTO> findAll() {
        String url = BASE_URL + "/employees";
        EmployeeDTO[] employees = restTemplate.getForObject(url, EmployeeDTO[].class);
        return Arrays.asList(employees);
    }

    public EmployeeDTO findById(Long id) {
        String url = BASE_URL + "/employee/" + id;
        try {
            return Objects.requireNonNull(restTemplate.getForObject(url, Response.class)).data();
        } catch (Exception e) {
            return null;
        }
    }
}
