package com.example.controller;


import com.example.dto.EmployeePayrollDTO;
import com.example.dto.EmployeePayrollEditDTO;
import com.example.dto.EmployeeUpdateDTO;
import com.example.dto.FullEmployeeDTO;
import com.example.model.Employee;
import com.example.model.Payroll;
import com.example.model.User;
import com.example.repository.EmployeeRepository;
import com.example.repository.PayrollRepository;
import com.example.repository.UserRepository;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository em;
   
    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
    	return employeeService.getAllEmployees();
    }
    
    // Get employee by ID
    @GetMapping("/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable Long id) {
    	return employeeService.getEmployeeById(id);
    }

    // Create new employee
    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody Map<String, Object> payload) {
        try {
            Employee employee = employeeService.saveOrUpdateEmployee(payload);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Error: " + e.getMessage());
        }
    }
   

    // Update employee
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id); // Ensure correct ID
        return employeeService.saveOrUpdateEmployee(employee);
    }
    
    @GetMapping("/dashboard")
    public List<EmployeePayrollDTO> getDashboardInfo() {
        return employeeService.getAllEmployeePayrollInfo();
    }
    
    
    

    @PutMapping("/dashboard/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody EmployeePayrollEditDTO employeePayrollDTO) {
        try {
            // Call the service method to update the employee and payroll details
            Employee updatedEmployee = employeeService.updateEmployeeAndPayroll(id, employeePayrollDTO);
            return ResponseEntity.ok(updatedEmployee);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("❌ Error: " + e.getMessage());
        }
    }
    
    
	@DeleteMapping("/dashboard/{id}")
	
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
	    return em.findById(id).map(employee -> {
	        em.delete(employee);
	        return ResponseEntity.ok("Employee and associated payrolls deleted successfully.");
	    }).orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/full-register")
	public ResponseEntity<String> registerFullEmployee(@RequestBody FullEmployeeDTO dto) {
		employeeService.registerFullEmployee(dto);
	    return ResponseEntity.ok("Employee registered successfully");
	}
	
	

	@Autowired
	private PayrollRepository payrollRepo;

	@GetMapping("/payroll/user/{userId}")
	public ResponseEntity<EmployeePayrollDTO> getEmployeePayroll(@PathVariable Long userId) {
	    Employee employee = em.findByUserId(userId);
	    if (employee == null) {
	        return ResponseEntity.notFound().build();
	    }
	    


	    Payroll payroll = payrollRepo.findByEmployee_Id(employee.getId());
	    if (payroll == null) {
	        return ResponseEntity.notFound().build();
	    }

	    double salary = employee.getSalary();
	    double bonus = payroll.getBonus();
	    double deductions = payroll.getDeductions();

	    double tax = calculateTax(salary);
	    double netSalary = salary + bonus - deductions - tax;

	    EmployeePayrollDTO dto = EmployeePayrollDTO.builder()
	            .employeeId(employee.getId())
	            .name(employee.getUser().getName())
	            .jobTitle(employee.getJobTitle())
	            .phone(employee.getPhone())
	            .salary(salary)
	            .bonus(bonus)
	            .deductions(deductions)
	            .tax(tax)
	            .netSalary(netSalary)
	            .build();

	    return ResponseEntity.ok(dto);
	}
	

	
	 private double calculateTax(double grossSalary) {
	        if (grossSalary <= 25000) {
	            return grossSalary * 0.05; // 5% tax
	        } else if (grossSalary <= 50000) {
	            return grossSalary * 0.10; // 10% tax
	        } else {
	            return grossSalary * 0.15; // 15% tax
	        }
	    }

	
	@Autowired
	private UserRepository userRepo;
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<String> updateEmployeeInfo(
	        @PathVariable Long userId,
	        @RequestBody EmployeeUpdateDTO updateDTO) {

	    // Find employee by userId
	    Employee employee = em.findByUserId(userId);
	    if (employee == null) {
	        return ResponseEntity.notFound().build();
	    }

	    // Update name and phone in associated user
	    User user = employee.getUser();
	    if (user != null) {
	        user.setName(updateDTO.getName());
	    }

	    // Update phone in employee
	    employee.setPhone(updateDTO.getPhone());

	    // Save updated employee and user
	    em.save(employee);
	    userRepo.save(user); // Only if needed separately

	    return ResponseEntity.ok("Employee details updated successfully.");
	}




    
    
    
    
    
}





