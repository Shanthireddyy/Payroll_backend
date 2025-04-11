package com.example.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor

@Builder
public class PayrollResponseDTO {
    private Long id;
    private double basicSalary;
    private double bonus;
    private double deductions;
    private double tax;
    private double netSalary;
    private LocalDate payDate;

    private Long employeeId;
    private String employeeName;

    // Constructor
    public PayrollResponseDTO(Long id, double basicSalary, double bonus, double deductions, double tax, double netSalary, LocalDate payDate, Long employeeId, String employeeName) {
        this.id = id;
        this.basicSalary = basicSalary;
        this.bonus = bonus;
        this.deductions = deductions;
        this.tax = tax;
        this.netSalary = netSalary;
        this.payDate = payDate;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    // Getters and setters
    // (generate all using your IDE or Lombok)
}
