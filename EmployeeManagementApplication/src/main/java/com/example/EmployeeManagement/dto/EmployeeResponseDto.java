package com.example.EmployeeManagement.dto;

public class EmployeeResponseDto {

    private int id;
    private String name;
    private String email;
    private String departmentName;
    private Double salary;

    // Constructors
    public EmployeeResponseDto() {
    }

    public EmployeeResponseDto(int id, String name, String email, String departmentName, Double salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.departmentName = departmentName;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
    
}
