package com.example.service;

import com.example.dto.EmployeePayrollDTO;

import com.example.dto.EmployeePayrollEditDTO;
import com.example.dto.FullEmployeeDTO;
import com.example.model.Employee;
import com.example.model.Payroll;
import com.example.model.User;
import com.example.repository.EmployeeRepository;
import com.example.repository.PayrollRepository;
import com.example.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Get all employees
    public List<Employee> getAllEmployees() {
    	return employeeRepository.findAll();
    }
    
    // ✅ Get employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
    	return employeeRepository.findById(id);
    }
    // ✅ Add new employee with user mapping
    public Employee saveOrUpdateEmployee(Employee employee) {
        if (employee.getUser() == null || employee.getUser().getId() == null) {
            throw new RuntimeException("User ID is required when adding an employee.");
        }

        Long userId = employee.getUser().getId();

        // Fetch full user object from DB
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        employee.setUser(user); // Attach full user to employee

        return employeeRepository.save(employee);
    }
    
    public Employee saveOrUpdateEmployee(Map<String, Object> payload) {
        Long userId = Long.parseLong(payload.get("userId").toString());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Employee employee = Employee.builder()
                .address(payload.get("address").toString())
                .jobTitle(payload.get("jobTitle").toString())
                .phone(payload.get("phone").toString())
                .salary(Double.parseDouble(payload.get("salary").toString()))
                .user(user)
                .build();

        return employeeRepository.save(employee);
    }



    // ✅ Update employee
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        existing.setJobTitle(updatedEmployee.getJobTitle());
        existing.setAddress(updatedEmployee.getAddress());
        existing.setPhone(updatedEmployee.getPhone());
        existing.setSalary(updatedEmployee.getSalary());

        // Optional: update linked user if needed
        if (updatedEmployee.getUser() != null) {
            Long userId = updatedEmployee.getUser().getId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
            existing.setUser(user);
        }

        return employeeRepository.save(existing);
    }

    // ✅ Delete employee
    public void deleteEmployee(Long id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();

            // 🛠 Break bidirectional link to avoid orphan being retained
            User user = employee.getUser();
            if (user != null) {
                user.setEmployee(null);
                userRepository.save(user);
            }

            employeeRepository.delete(employee);
        } else {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
    }
    
    
  


    public List<EmployeePayrollDTO> getAllEmployeePayrollInfo() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeePayrollDTO> result = new ArrayList<>();

        for (Employee emp : employees) {
            Payroll payroll = emp.getPayrolls().isEmpty() ? null : emp.getPayrolls().get(emp.getPayrolls().size() - 1); // latest

            EmployeePayrollDTO dto = EmployeePayrollDTO.builder()
            		.employeeId(emp.getId())
                    .name(emp.getUser().getName())
                    .jobTitle(emp.getJobTitle())
                    .phone(emp.getPhone())
                    .salary(emp.getSalary())
                    .bonus(payroll != null ? payroll.getBonus() : 0)
                    .deductions(payroll != null ? payroll.getDeductions() : 0)
                    .netSalary(payroll != null ? payroll.getNetSalary() : 0)
                    .tax(payroll != null ? payroll.getTax() : 0)
                    .build();

            result.add(dto);
        }
        return result;
    }
    
    
   

    

    
    
    
    
    @Autowired
    private PayrollRepository payrollRepository;

    // Update employee and payroll info
    public Employee updateEmployeeAndPayroll(Long id, EmployeePayrollEditDTO employeePayrollDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Update employee details
       // existingEmployee.setName(employeePayrollDTO.getName());
        existingEmployee.setJobTitle(employeePayrollDTO.getJobTitle());
       // existingEmployee.setPhone(employeePayrollDTO.getPhone());
        existingEmployee.setSalary(employeePayrollDTO.getSalary());

        // Update or add payroll details (Assuming one payroll per employee for simplicity)
        if (existingEmployee.getPayrolls().isEmpty()) {
            Payroll payroll = new Payroll();
            payroll.setEmployee(existingEmployee);
            payroll.setBonus(employeePayrollDTO.getBonus());
            payroll.setDeductions(employeePayrollDTO.getDeductions());
            payroll.setTax(employeePayrollDTO.getTax());
            payroll.setNetSalary(employeePayrollDTO.getNetSalary());
            existingEmployee.getPayrolls().add(payroll);
        } else {
            Payroll payroll = existingEmployee.getPayrolls().get(0); // Assuming 1 payroll record
            payroll.setBonus(employeePayrollDTO.getBonus());
            payroll.setDeductions(employeePayrollDTO.getDeductions());
            payroll.setTax(employeePayrollDTO.getTax());
            payroll.setNetSalary(employeePayrollDTO.getNetSalary());
        }

        // Save and return updated employee
        return employeeRepository.save(existingEmployee);
    }


    @Transactional
    public void deleteEmployeeAndPayrolls(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));
        employeeRepository.delete(employee); // This will delete payrolls too
    }
    
    
    public void registerFullEmployee(FullEmployeeDTO dto) {
        User user = User.builder()
            .name(dto.getName())
            .email(dto.getEmail())
            .password(dto.getPassword())
            .role(dto.getRole())
            .build();

        userRepository.save(user);

        Employee employee = Employee.builder()
            .address(dto.getAddress())
            .phone(dto.getPhone())
            .jobTitle(dto.getJobTitle())
            .salary(dto.getSalary())
            .user(user)
            .build();

        employeeRepository.save(employee);
        double bonus = dto.getBonus();
        double deductions = dto.getDeductions();
        double grossIncome = dto.getBasicSalary() + bonus;
        double tax = calculateTax(grossIncome); // use method below
        double netSalary = grossIncome - (tax + deductions);

        Payroll payroll = Payroll.builder()
            .basicSalary(dto.getBasicSalary())
            .bonus(dto.getBonus())
            .deductions(dto.getDeductions())
            .tax(dto.getTax())
            .netSalary(dto.getNetSalary())
            .employee(employee)
            .build();

        payrollRepository.save(payroll);
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





}
