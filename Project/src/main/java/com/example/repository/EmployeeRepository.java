package com.example.repository;

import com.example.dto.EmployeeInfoDTO;
import com.example.model.Employee;
import com.example.model.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//
//    @Query("SELECT new com.example.dto.EmployeeInfoDTO(u.id, u.name, u.email, u.role, e.jobTitle, e.salary) " +
//           "FROM Employee e JOIN e.user u")
//    List<EmployeeInfoDTO> fetchAllEmployeeDetails();
//    
//    Optional<Employee> findByUserId(Long userId);
//
//
//	
//}

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @EntityGraph(attributePaths = {"user", "payrolls"})
    List<Employee> findAll();
    
    
    Employee findByUserId(Long userId);
}

