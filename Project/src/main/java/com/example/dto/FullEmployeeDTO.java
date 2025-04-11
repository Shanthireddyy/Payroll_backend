package com.example.dto;



import lombok.Data;

import java.time.LocalDate;

@Data
public class FullEmployeeDTO {
    // User
    private String name;
    private String email;
    private String password;
    private String role = "EMPLOYEE";

    // Employee
    private String jobTitle;
    private String address;
    private String phone;
    private double salary;

    // Payroll
    private double basicSalary;
    private double bonus;
    private double deductions;
    private double tax;
    private double netSalary;
    
}
