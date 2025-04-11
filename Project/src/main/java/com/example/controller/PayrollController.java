package com.example.controller;

import com.example.dto.PayrollRequest;
import com.example.dto.PayrollResponseDTO;
import com.example.model.Payroll;
import com.example.service.PayrollService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
@CrossOrigin(origins = "*")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    // 💰 Generate payroll for an employee
//    @PostMapping("/generate/{employeeId}")
//    public Payroll generatePayroll(
//            @PathVariable Long employeeId,
//            @RequestParam double bonus,
//            @RequestParam double deductions) {
//        return payrollService.generatePayroll(employeeId, bonus, deductions);
//    }
    
    @PostMapping("/generate")
    public ResponseEntity<Payroll> generatePayroll(@Valid @RequestBody PayrollRequest request) {
        Payroll generatedPayroll = payrollService.generatePayroll(request.getEmployeeId(), request.getBonus(), request.getDeductions());
        return ResponseEntity.status(HttpStatus.CREATED).body(generatedPayroll);
    }
    
    @GetMapping("/fullgenerate")
    public ResponseEntity<List<PayrollResponseDTO>> getAllPayrollsss() {
        List<PayrollResponseDTO> payrolls = payrollService.getAllPayrollss();
        return ResponseEntity.ok(payrolls);
    }
    
    @GetMapping("/fullgenerate/{employeeId}")
    public ResponseEntity<PayrollResponseDTO> generatePayrollForEmployee(@PathVariable Long employeeId) {
        PayrollResponseDTO payroll = payrollService.generatePayrollForEmployee(employeeId);
        return ResponseEntity.ok(payroll);
    }




    
    // 📄 Get all payrolls
    @GetMapping
    public List<Payroll> getAllPayrolls() {
        return payrollService.getAllPayrolls();
    }

    // 👤 Get payrolls for a specific employee
    @GetMapping("/employee/{employeeId}")
    public List<Payroll> getByEmployee(@PathVariable Long employeeId) {
        return payrollService.getPayrollsByEmployeeId(employeeId);
    }

    // 🔍 Get payroll by payroll ID
    @GetMapping("/{payrollId}")
    public Payroll getPayrollById(@PathVariable Long payrollId) {
        return payrollService.getPayrollById(payrollId);
    }

    // 📆 Get payrolls by month and year
    @GetMapping("/month")
    public List<Payroll> getPayrollsByMonthYear(
            @RequestParam int month,
            @RequestParam int year) {
        return payrollService.getPayrollsByMonthYear(month, year);
    }

    // ✏️ Update a payroll (optional)
    @PutMapping("/{payrollId}")
    public Payroll updatePayroll(@PathVariable Long payrollId, @RequestBody Payroll payroll) {
        return payrollService.updatePayroll(payrollId, payroll);
    }

    // ❌ Delete a payroll
    @DeleteMapping("/{payrollId}")
    public String deletePayroll(@PathVariable Long payrollId) {
        payrollService.deletePayroll(payrollId);
        return "Payroll deleted successfully.";
    }

    // 📊 Total tax paid by a specific employee
    @GetMapping("/employee/{employeeId}/tax-total")
    public double getTotalTaxPaidByEmployee(@PathVariable Long employeeId) {
        return payrollService.getTotalTaxPaidByEmployee(employeeId);
    }
}
