package com.example.service;

import com.example.model.LeaveRequest;
import com.example.model.LeaveStatus;
import com.example.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestService {

    private final LeaveRequestRepository repository;

    public LeaveRequestService(LeaveRequestRepository repository) {
        this.repository = repository;
    }

    public LeaveRequest applyLeave(LeaveRequest request) {
        request.setStatus(LeaveStatus.PENDING);
        return repository.save(request);
    }

    public List<LeaveRequest> getAllLeaves() {
        return repository.findAll();
    }

    public List<LeaveRequest> getLeavesByEmployeeId(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public LeaveRequest updateLeaveStatus(Long id, LeaveStatus status) {
        Optional<LeaveRequest> optional = repository.findById(id);
        if (optional.isPresent()) {
            LeaveRequest leave = optional.get();
            leave.setStatus(status);
            return repository.save(leave);
        }
        return null;
    }
}
