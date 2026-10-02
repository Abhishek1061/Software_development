package com.example.EmployeeManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EmployeeManagement.dto.EmployeeRequestDto;
import com.example.EmployeeManagement.dto.EmployeeResponseDto;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.repository.EmployeeRepository;

@Service 
public class EmployeeService {    
      

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository =  employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setSalary(employeeDto.getSalary());
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeResponseDto responseDto = new EmployeeResponseDto();

        responseDto.setId(savedEmployee.getId());
        responseDto.setName(savedEmployee.getName());
        responseDto.setEmail(savedEmployee.getEmail());
        responseDto.setDepartment(savedEmployee.getDepartment());
        responseDto.setSalary(savedEmployee.getSalary());
        return responseDto;
    }

    public Employee updateEmployee(Integer id, Employee employeeDetails){
        Employee employee =  employeeRepository.findById(id).orElse(null);
        if(employee != null){
            employee.setName(employeeDetails.getName());
            employee.setEmail(employeeDetails.getEmail());
            employee.setDepartment(employeeDetails.getDepartment());
            employee.setSalary(employeeDetails.getSalary());
            return employeeRepository.save(employee);
        }
        return null;
    }

    public void deleteEmployee(Integer id){
        employeeRepository.deleteById(id);
    }

}
