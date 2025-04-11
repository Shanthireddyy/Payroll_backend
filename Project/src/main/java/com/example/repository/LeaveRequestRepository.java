package com.example.repository;

import com.example.model.LeaveRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployeeId(Long employeeId);
 // ✅ Correct
//    List<LeaveRequest> findByEmployee_Id(Long employeeId);

    
  

    
}
