package com.example.EmployeeManagement.dto;

import com.example.EmployeeManagement.entity.Department;

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
    
    @NotBlank 
    private Department department;

    @NotNull
    @Positive  
    private Double salary;

    // Constructors
    public EmployeeRequestDto() {
    }

    public EmployeeRequestDto(String name, String email, Department department, Double salary) {
        this.name = name;
        this.email = email;
        this.department = department;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

}
