package com.example.EmployeeManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class EmployeeRequestDto {
    
    @NotBlank 
    private String name;

    @NotBlank 
    @Email 
    private String email;
    
    @NotNull  
    private Long departmentId;

    @NotNull
    @Positive  
    private Double salary;

    // Constructors
    public EmployeeRequestDto() {
    }

    public EmployeeRequestDto(String name, String email, Long departmentId, Double salary) {
        this.name = name;
        this.email = email;
        this.departmentId = departmentId;
        this.salary = salary;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

}
