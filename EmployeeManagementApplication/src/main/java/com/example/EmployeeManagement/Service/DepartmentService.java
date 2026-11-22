package com.example.EmployeeManagement.service;


import java.util.List;
import org.springframework.stereotype.Service;
import com.example.EmployeeManagement.entity.Department;
import com.example.EmployeeManagement.exceptions.ResourceNotFoundException;
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

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }
    
}
