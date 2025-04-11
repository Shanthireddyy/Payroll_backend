package com.example.dto;

public class LeaveRequestDTO {
    private Long id;
    private String employeeName;
    private String description;
    private String status;

    public LeaveRequestDTO(Long id, String employeeName, String description, String status) {
        this.id = id;
        this.employeeName = employeeName;
        this.description = description;
        this.status = status;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
