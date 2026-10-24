package com.example.EmployeeManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.EmployeeManagement.dto.EmployeeRequestDto;
import com.example.EmployeeManagement.dto.EmployeeResponseDto;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.repository.EmployeeRepository;
import com.example.EmployeeManagement.exceptions.ResourceNotFoundException;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public Page<EmployeeResponseDto> getAllEmployees(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Page<Employee> employeePage = employeeRepository.findAll(PageRequest.of(page, size, sort));
        return employeePage.map(this::mapToResponseDto);
    }

    public EmployeeResponseDto getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return mapToResponseDto(employee);
    }

    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setSalary(employeeDto.getSalary());
        Employee savedEmployee = employeeRepository.save(employee);
       return mapToResponseDto(savedEmployee);
    }

    public EmployeeResponseDto updateEmployee(Integer id, EmployeeRequestDto employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setSalary(employeeDetails.getSalary());
        return mapToResponseDto(employeeRepository.save(employee));
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }
    

    private EmployeeResponseDto mapToResponseDto(Employee employee) {

        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getDepartment());
        dto.setSalary(employee.getSalary());

        return dto;
    }

    public List<EmployeeResponseDto>  getEmployeesByDepartment(String department){
        return employeeRepository.findByDepartment(department)
            .stream()
            .map(this::mapToResponseDto)
            // .map(employee -> (maptoResponseDto(employee)))  // Equalent to the above code snippet
            .collect(Collectors.toList());

            /*Equvalent code snippent pf above stream code */
        // List<Employee> employees = employeeRepository.findByDepartment( department);
        // List<EmployeeResponseDto> dtoList = new ArrayList<>();
        // for(Employee employee : employees){
        //     dtoList.add(
        //     mapToResponseDto(employee));
        // }
        // return dtoList;

    }

    public List<EmployeeResponseDto> getEmployeesByName(String name){
        return employeeRepository.findByNameContaining(name)
            .stream()
            .map(this::mapToResponseDto)
            .collect(Collectors.toList());
    }

    public List<EmployeeResponseDto> getEmployeesByDepartmentAndSalary(String department, Double salary){
        return employeeRepository.findByDepartmentAndSalary(department, salary)
            .stream()
            .map(this::mapToResponseDto)
            .collect(Collectors.toList());
    }

}
