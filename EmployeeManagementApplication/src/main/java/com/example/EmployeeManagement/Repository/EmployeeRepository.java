package com.example.EmployeeManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.EmployeeManagement.Entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    
    // Custom query methods can be defined here if needed
}

