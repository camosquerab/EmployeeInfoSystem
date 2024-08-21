package com.example.demo.dto;

import java.util.List;

public record ResponseList   (String status, List<EmployeeDTO> data, String message){
}
