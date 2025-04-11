package com.example.controller;




import com.example.dto.LeaveRequestDTO;


//import com.example.dto.LeaveRequestDTO;

import com.example.model.LeaveRequest;
import com.example.model.LeaveStatus;

import com.example.repository.LeaveRequestRepository;
import com.example.service.LeaveRequestService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "*")
public class LeaveRequestController {

    private final LeaveRequestService leaveService;
    
    private final LeaveRequestRepository leaveRequestRepository;

    public LeaveRequestController(LeaveRequestService leaveService, LeaveRequestRepository leaveRequestRepository) {
        this.leaveService = leaveService;
        this.leaveRequestRepository = leaveRequestRepository;
    }

//    public LeaveRequestController(LeaveRequestService leaveService) {
//        this.leaveService = leaveService;
//    }

    // Employee applies for leave
    @PostMapping("/apply")
    public LeaveRequest applyLeave(@RequestBody LeaveRequest request) {
        return leaveService.applyLeave(request);
    }

    // Admin fetches all leave requests
    @GetMapping("/all")
    public List<LeaveRequest> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    // Employee views their own leave history
    @GetMapping("/employee/{employeeId}")
    public List<LeaveRequest> getLeavesByEmployeeId(@PathVariable Long employeeId) {
        return leaveService.getLeavesByEmployeeId(employeeId);
    }

    // Admin updates leave status
    @PutMapping("/update/{id}")
    public LeaveRequest updateLeaveStatus(@PathVariable Long id, @RequestParam LeaveStatus status) {
        return leaveService.updateLeaveStatus(id, status);
    }
    
    
    
//    @GetMapping("/employee/{employeeId}")
//    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByEmployeeId(@PathVariable Long employeeId) {
//        List<LeaveRequest> requests = leaveRequestRepository.findByEmployeeId(employeeId);
//        return ResponseEntity.ok(requests);
//    }

    
   


    
    
  
    
  
}
