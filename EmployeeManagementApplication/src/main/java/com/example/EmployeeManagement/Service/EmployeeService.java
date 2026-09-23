package com.example.EmployeeManagement.Service;

@Service
public class EmployeeService {    
      
    @Autowire
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

}
