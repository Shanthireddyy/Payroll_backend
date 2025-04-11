package com.example.repository;

import com.example.model.Payroll;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    List<Payroll> findByEmployeeId(Long employeeId);

    @Query("SELECT p FROM Payroll p WHERE MONTH(p.payDate) = ?1 AND YEAR(p.payDate) = ?2")
    List<Payroll> findByPayrollDateMonthAndYear(int month, int year);

    @Modifying
    @Transactional
    @Query("DELETE FROM Payroll p WHERE p.employee.id = :employeeId")
    int deleteByEmployeeId(@Param("employeeId") Long employeeId);

	Payroll findByEmployee_Id(Long id);
}
