package com.example.demo.dto;

import java.util.List;

public record Response(String status, EmployeeDTO data, String message){}
