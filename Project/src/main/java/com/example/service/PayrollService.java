package com.example.service;

import com.example.dto.EmployeePayrollDTO;
import com.example.dto.PayrollResponseDTO;
import com.example.model.Employee;
import com.example.model.Payroll;
import com.example.repository.EmployeeRepository;
import com.example.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    
    
    // 💰 Generate payroll for employee with bonus and deductions
    public Payroll generatePayroll(Long employeeId, double bonus, double deductions) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        double baseSalary = employee.getSalary();
        double grossSalary = baseSalary + bonus;
        double tax = calculateTax(grossSalary);
        double netSalary = grossSalary - tax - deductions;

        Payroll payroll = Payroll.builder()
                .employee(employee)
                .basicSalary(baseSalary)
                .bonus(bonus)
                .deductions(deductions)
                .tax(tax)
                .netSalary(netSalary)
                .payDate(LocalDate.now())
                .build();

        return payrollRepository.save(payroll);
    }
    
    
    
    

    // 🧠 Tax calculation logic (can be improved later)
    private double calculateTax(double grossSalary) {
        if (grossSalary <= 25000) {
            return grossSalary * 0.05; // 5% tax
        } else if (grossSalary <= 50000) {
            return grossSalary * 0.10; // 10% tax
        } else {
            return grossSalary * 0.15; // 15% tax
        }
    }

    // 📄 Get all payrolls
    public List<Payroll> getAllPayrolls() {
        return payrollRepository.findAll();
    }

    // 👤 Get payrolls by employee
    public List<Payroll> getPayrollsByEmployeeId(Long employeeId) {
        return payrollRepository.findByEmployeeId(employeeId);
    }

    // 🔍 Get payroll by ID
    public Payroll getPayrollById(Long payrollId) {
        return payrollRepository.findById(payrollId)
                .orElseThrow(() -> new RuntimeException("Payroll not found with ID: " + payrollId));
    }

    // 📆 Get payrolls by month & year
    public List<Payroll> getPayrollsByMonthYear(int month, int year) {
        return payrollRepository.findByPayrollDateMonthAndYear(month, year);
    }

    // ✏️ Update payroll
    public Payroll updatePayroll(Long payrollId, Payroll updated) {
        Payroll existing = getPayrollById(payrollId);

        existing.setBasicSalary(updated.getBasicSalary());
        existing.setBonus(updated.getBonus());
        existing.setDeductions(updated.getDeductions());
        existing.setTax(updated.getTax());
        existing.setNetSalary(updated.getNetSalary());
        existing.setPayDate(updated.getPayDate());

        return payrollRepository.save(existing);
    }

    // ❌ Delete payroll
    public void deletePayroll(Long payrollId) {
        Payroll payroll = getPayrollById(payrollId);
        payrollRepository.delete(payroll);
    }

    // 📊 Get total tax paid by employee
    public double getTotalTaxPaidByEmployee(Long employeeId) {
        List<Payroll> payrolls = getPayrollsByEmployeeId(employeeId);
        return payrolls.stream().mapToDouble(Payroll::getTax).sum();
    }
    
    
    
    public List<PayrollResponseDTO> getAllPayrollss() {
        List<Payroll> payrolls = payrollRepository.findAll();

        return payrolls.stream()
                .map(payroll -> new PayrollResponseDTO(
                        payroll.getId(),
                        payroll.getBasicSalary(),
                        payroll.getBonus(),
                        payroll.getDeductions(),
                        payroll.getTax(),
                        payroll.getNetSalary(),
                        payroll.getPayDate(),
                        payroll.getEmployee().getId(),
                        payroll.getEmployee().getUser().getName()
                ))
                .collect(Collectors.toList());
    }
    
    public PayrollResponseDTO generatePayrollForEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        double basicSalary = employee.getSalary();
        double bonus = calculateBonus(employee);      // You can customize this logic
        double deductions = calculateDeductions(employee); // Customize if needed
        double tax = calculateTax(basicSalary);       // Replace with your tax logic
        double netSalary = basicSalary + bonus - deductions - tax;

        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setBasicSalary(basicSalary);
        payroll.setBonus(bonus);
        payroll.setDeductions(deductions);
        payroll.setTax(tax);
        payroll.setNetSalary(netSalary);
        payroll.setPayDate(LocalDate.now());

        payrollRepository.save(payroll);

        return new PayrollResponseDTO(
                payroll.getId(),
                basicSalary,
                bonus,
                deductions,
                tax,
                netSalary,
                payroll.getPayDate(),
                employee.getId(),
                employee.getUser().getName()
        );
    }
    
 // Dummy Bonus: 10% of salary for now
    private double calculateBonus(Employee employee) {
        return employee.getSalary() * 0.10; // 10% bonus
    }

    // Dummy Deductions: fixed 1500 for now
    private double calculateDeductions(Employee employee) {
        return 1500.0; // flat deduction amount
    }

    
    


}
