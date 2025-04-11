package com.example.model;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double basicSalary;
    private double bonus;
    private double deductions;
    private double tax;
    private double netSalary;
    private LocalDate payDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnoreProperties({"payrolls", "otherFieldYouDontNeed"})
    @JoinColumn(name = "employee_id", unique=true,nullable = false)
    private Employee employee;

}
