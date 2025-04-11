package com.example.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeInfoDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String jobTitle;
    private Double salary;
}

