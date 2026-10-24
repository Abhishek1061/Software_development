package com.example.EmployeeManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.EmployeeManagement.entity.Employee;

@Repository 
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    
    // Custom query methods can be defined here if needed
    //Derived query methods based on method names
    List<Employee> findByDepartment(String department);

    List<Employee> findByNameContaining(String name);

    List<Employee> findByDepartmentAndSalary(String department, Double salary);

    //JPQL query methods 

    @Query(
        "SELECT e FROM Employee e " + 
        "WHERE e.department = :department"
    )
    List<Employee> getEmployeesByDepartment(@Param ("department") String department);

    @Query(
        "SELECT e FROM Employee e WHERE e.salary > :salary "
    )
    List<Employee> getEmployeesSalaryGreaterThan(@Param("salary") Double salary);


    //Native query methods
    @Query(
        value = "SELECT * FROM employees e WHERE e.salary < :salary",
        nativeQuery = true
    )
    List<Employee> getEmployeesSalaryLessThan(@Param("salary") Double salary);

}

