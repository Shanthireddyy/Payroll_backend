package com.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//✅ Create this DTO first:
public class PayrollRequest {

    @NotNull
    private Long employeeId;

    @Min(0)
    private double bonus;

    @Min(0)
    private double deductions;

    // Getters and Setters
}
