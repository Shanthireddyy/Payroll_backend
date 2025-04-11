package com.example.dto;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePayrollDTO {
	
	private Long employeeId;
    private String name;
    private String jobTitle;
    private String phone;
    private double salary;
    private double bonus;
    private double deductions;
    private double netSalary;
    private double tax;
}
