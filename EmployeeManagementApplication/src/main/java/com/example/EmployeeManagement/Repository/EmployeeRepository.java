package com.example.EmployeeManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeManagement.entity.Employee;

@Repository 
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    
    // Custom query methods can be defined here if needed

    List<Employee> findByDepartment(String department);

    List<Employee> findByNameContaining(String name);

    List<Employee> findByDepartmentAndSalary(String department, Double salary);
}

