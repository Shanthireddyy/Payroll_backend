package com.example.dto;
import lombok.AllArgsConstructor;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePayrollEditDTO {
	
	
    private String jobTitle;
    
    private double salary;
    private double bonus;
    private double deductions;
    private double netSalary;
    private double tax;

}
