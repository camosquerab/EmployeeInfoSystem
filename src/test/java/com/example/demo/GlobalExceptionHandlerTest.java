package com.example.demo;

import com.example.demo.controller.EmployeeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeController employeeController;

    @Test
    void handleClientError_shouldReturnTooManyRequests() throws Exception {
        when(employeeController.getEmployeeById(1L))
                .thenThrow(new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS));

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isTooManyRequests());
    }

    @Test
    void handleClientError_shouldReturnTooManyRequestsOnFindAll() throws Exception {
        when(employeeController.getAllEmployees())
                .thenThrow(new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isTooManyRequests());
    }

    @Test
    void handleClientError_shouldReturnNotFound() throws Exception {
        when(employeeController.getEmployeeById(1L))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void handleServerError_shouldReturnInternalServerError() throws Exception {
        when(employeeController.getEmployeeById(1L))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void handleNumberFormatException_shouldReturnBadRequest() throws Exception {
        when(employeeController.getEmployeeById(1L))
                .thenThrow(new NumberFormatException("Invalid number format"));

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void handleGeneralException_shouldReturnInternalServerError() throws Exception {
        when(employeeController.getEmployeeById(1L))
                .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isInternalServerError());
    }
}