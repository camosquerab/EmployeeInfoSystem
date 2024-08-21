package com.example.demo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setup() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void findById_shouldReturnEmployee() {
        // Arrange
        String url = "http://dummy.restapiexample.com/api/v1/employee/1";
        mockServer.expect(requestTo(url))
                .andRespond(withSuccess("{\"data\":{\"id\":1,\"employeeName\":\"Tiger Nixon\",\"employeeSalary\":320800,\"employeeAge\":61,\"employeeAnualSalary\":3849600,\"profileImage\":\"Tiger Nixon\"}}", MediaType.APPLICATION_JSON));

        // Act
        EmployeeDTO actualEmployee = employeeRepository.findById(1L);

        // Assert
        assertThat(actualEmployee, notNullValue());
        assertThat(actualEmployee.employeeName(), equalTo("Tiger Nixon"));
    }

    @Test
    public void findAll_shouldReturnListOfEmployees() {
        String url = "http://dummy.restapiexample.com/api/v1/employees";

        mockServer.expect(requestTo(url))
                .andRespond(withSuccess("{\"data\":[{\"id\":1,\"employeeName\":\"Tiger Nixon\",\"employeeSalary\":320800,\"employeeAge\":61,\"employeeAnualSalary\":3849600,\"profileImage\":\"Tiger Nixon\"},{\"id\":2,\"employeeName\":\"Garrett Winters\",\"employeeSalary\":170750,\"employeeAge\":63,\"employeeAnualSalary\":2049000,\"profileImage\":\"Garrett Winters\"}]}", MediaType.APPLICATION_JSON));

        // Act
        List<EmployeeDTO> actualEmployees = employeeRepository.findAll();

        // Assert
        assertThat(actualEmployees, hasSize(2));
        assertThat(actualEmployees.get(0).employeeName(), equalTo("Tiger Nixon"));
        assertThat(actualEmployees.get(1).employeeName(), equalTo("Garrett Winters"));
    }

    @Test
    public void findById_shouldHandleTooManyRequests() {
        // Arrange
        String url = "http://dummy.restapiexample.com/api/v1/employee/1";
        mockServer.expect(requestTo(url))
                .andRespond(withStatus(HttpStatus.TOO_MANY_REQUESTS).body("{\"message\":\"Too Many Attempts.\"}").contentType(MediaType.APPLICATION_JSON));

        // Act & Assert
        Exception exception = assertThrows(HttpClientErrorException.TooManyRequests.class, () -> {
            employeeRepository.findById(1L);
        });

        assertThat(exception.getMessage(), containsString("Too Many Requests"));
    }

    @Test
    public void findById_shouldHandleNotFoundError() {
        // Arrange
        String url = "http://dummy.restapiexample.com/api/v1/employee/999";
        mockServer.expect(requestTo(url))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        // Act & Assert
        Exception exception = assertThrows(HttpClientErrorException.NotFound.class, () -> {
            employeeRepository.findById(999L);
        });

        assertThat(exception.getMessage(), containsString("404 Not Found"));
    }

    @Test
    public void findById_shouldHandleBadRequest() {
        // Arrange
        String url = "http://dummy.restapiexample.com/api/v1/employee/";
        mockServer.expect(requestTo(url + "1"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        // Act & Assert
        Exception exception = assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            employeeRepository.findById(1L);
        });

        assertThat(exception.getMessage(), containsString("400 Bad Request"));
    }


}