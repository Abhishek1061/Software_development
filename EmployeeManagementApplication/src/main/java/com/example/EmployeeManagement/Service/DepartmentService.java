package com.example.EmployeeManagement.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.EmployeeManagement.dto.EmployeeRequestDto;
import com.example.EmployeeManagement.dto.EmployeeResponseDto;
import com.example.EmployeeManagement.entity.Department;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.repository.DepartmentRepository;

@Service 
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    
}
