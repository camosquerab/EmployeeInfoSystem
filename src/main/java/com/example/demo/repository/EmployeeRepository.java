package com.example.demo.repository;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.Response;
import com.example.demo.dto.ResponseList;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Repository
public class EmployeeRepository {

    private final String BASE_URL = "http://dummy.restapiexample.com/api/v1";
    private final RestTemplate restTemplate;

    public EmployeeRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<EmployeeDTO> findAll() {
        try {
            String url = "http://dummy.restapiexample.com/api/v1/employees";
            ResponseList response = restTemplate.getForObject(url, ResponseList.class);
            if (response == null || response.data() == null) {
                throw new RuntimeException("Empty or null response from API");
            }
            return response.data();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new RuntimeException("HTTP error occurred: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching employees", e);
        }
    }

    public EmployeeDTO findById(Long id) {
        String url = BASE_URL + "/employee/" + id;
        try {
            Response response = restTemplate.getForObject(url, Response.class);
            return Objects.requireNonNull(response).data();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching employee by ID", e);
        }
    }
}
