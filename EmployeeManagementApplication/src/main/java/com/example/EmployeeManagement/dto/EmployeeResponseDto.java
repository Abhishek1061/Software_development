package com.example.EmployeeManagement.dto;

import com.example.EmployeeManagement.entity.Department;

public class EmployeeResponseDto {

    private int id;
    private String name;
    private String email;
    private Department department;
    private Double salary;

    // Constructors
    public EmployeeResponseDto() {
    }

    public EmployeeResponseDto(int id, String name, String email, Department department, Double salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
