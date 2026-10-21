# Java Backend Interview Preparation Notes

## Current Project

Employee Management System

---

# Application Architecture

```text
Client (Postman / Angular)
           |
           v
      Controller
           |
           v
        Service
           |
           v
      Repository
           |
           v
   Spring Data JPA
           |
           v
       Hibernate
           |
           v
       Database
```

---

# Phase 1: CRUD

## Entity

```java
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String department;
    private Double salary;
}
```

### @Entity

Maps Java class to database table.

### @Id

Represents primary key.

### @GeneratedValue

Auto generates IDs.

---

## Repository

```java
@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee,Integer>{
}
```

Methods available:

```java
findAll()
findById()
save()
deleteById()
existsById()
```

---

## Constructor Injection

```java
private final EmployeeRepository employeeRepository;

public EmployeeService(
        EmployeeRepository employeeRepository){

    this.employeeRepository = employeeRepository;
}
```

Benefits:

- Testability
- Immutability
- Spring Best Practice

---

# DTOs

## Problem with Returning Entity

Bad Example:

```java
@Entity
public class Employee {

    private Integer id;

    private String name;

    private String email;

    private String password;
}
```

Returning Entity:

```java
return employee;
```

May expose:

```json
{
  "password": "secret"
}
```

---

# Request DTO

```java
public class EmployeeRequestDto {

    private String name;

    private String email;

    private String department;

    private Double salary;
}
```

Purpose:

Receive data from client.

---

# Response DTO

```java
public class EmployeeResponseDto {

    private Integer id;

    private String name;

    private String email;

    private String department;

    private Double salary;
}
```

Purpose:

Send data back to client.

---

# DTO Mapping

Request Flow

```text
JSON
 ↓
EmployeeRequestDto
 ↓
Employee Entity
 ↓
Database
```

Response Flow

```text
Database
 ↓
Employee Entity
 ↓
EmployeeResponseDto
 ↓
JSON Response
```

---

# Reusable Mapper

```java
private EmployeeResponseDto mapToResponseDto(
        Employee employee){

    EmployeeResponseDto dto =
            new EmployeeResponseDto();

    dto.setId(employee.getId());
    dto.setName(employee.getName());
    dto.setEmail(employee.getEmail());
    dto.setDepartment(employee.getDepartment());
    dto.setSalary(employee.getSalary());

    return dto;
}
```

Benefits:

- Reusable
- Cleaner
- Less Duplication

---

# Validation

Dependency

```xml
<dependency>
    <groupId>
        org.springframework.boot
    </groupId>
    <artifactId>
        spring-boot-starter-validation
    </artifactId>
</dependency>
```

---

## EmployeeRequestDto

```java
public class EmployeeRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String department;

    @NotNull
    @Positive
    private Double salary;
}
```

---

## @NotNull

Rejects:

```java
null
```

Allows:

```java
""
```

---

## @NotBlank

Rejects:

```java
null
""
"   "
```

---

## @Email

Accepts:

```text
abc@gmail.com
```

Rejects:

```text
abc
```

---

## @Positive

Accepts:

```java
100
```

Rejects:

```java
0
-100
```

---

## @Valid

Controller

```java
@PostMapping("/employees")
public ResponseEntity<EmployeeResponseDto>
createEmployee(
        @Valid
        @RequestBody
        EmployeeRequestDto employee){

}
```

Validation Flow

```text
Request
 ↓
DTO Conversion
 ↓
Validation
 ↓
Controller Method Executes
```

If validation fails:

```text
MethodArgumentNotValidException
```

is thrown.

Service is NOT executed.

---

# Exception Handling

## ResourceNotFoundException

```java
public class ResourceNotFoundException
        extends RuntimeException{

    public ResourceNotFoundException(
            String message){

        super(message);
    }
}
```

---

# Optional

Repository Method

```java
findById(id)
```

returns

```java
Optional<Employee>
```

---

## Bad

```java
findById(id).get();
```

Throws:

```text
NoSuchElementException
```

---

## Good

```java
findById(id)
.orElseThrow(
        () ->
            new ResourceNotFoundException(
                    "Employee not found"));
```

---

# Global Exception Handling

## @ControllerAdvice

```java
@ControllerAdvice
public class GlobalExceptionHandler {
}
```

Purpose:

Centralized Exception Handling

---

# ErrorResponse DTO

```java
public class ErrorResponse {

    private String timestamp;

    private int status;

    private String message;
}
```

Example

```json
{
  "timestamp":"2026-09-30T23:00:00",
  "status":404,
  "message":"Employee not found"
}
```

---

# ResourceNotFoundException Handler

```java
@ExceptionHandler(
        ResourceNotFoundException.class)
public ResponseEntity<ErrorResponse>
handleResourceNotFoundException(
        ResourceNotFoundException ex){

    ErrorResponse errorResponse =
            new ErrorResponse(
                    LocalDateTime.now().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage());

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorResponse);
}
```

---

# Validation Exception Handler

```java
@ExceptionHandler(
        MethodArgumentNotValidException.class)
public ResponseEntity<ErrorResponse>
handleValidationException(
        MethodArgumentNotValidException ex){

    ErrorResponse errorResponse =
            new ErrorResponse(
                    LocalDateTime.now().toString(),
                    HttpStatus.BAD_REQUEST.value(),
                    ex.getBindingResult()
                      .getFieldError()
                      .getDefaultMessage());

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorResponse);
}
```

---

# ResponseEntity

Controls:

- Status Code
- Headers
- Response Body

---

## 200 OK

```java
return ResponseEntity.ok(employee);
```

---

## 201 CREATED

```java
return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(responseDto);
```

---

## 204 NO CONTENT

```java
return ResponseEntity.noContent()
        .build();
```

---

## 400 BAD REQUEST

Validation error.

---

## 404 NOT FOUND

Resource missing.

---

## 500 INTERNAL SERVER ERROR

Unexpected server issue.

---

# Current CRUD APIs

## CREATE

```java
POST /api/employees
```

Returns

```http
201 CREATED
```

---

## GET BY ID

```java
GET /api/employees/{id}
```

Returns

```java
EmployeeResponseDto
```

Uses

```java
orElseThrow(...)
```

---

## UPDATE

Uses

```java
EmployeeRequestDto
```

Returns

```java
EmployeeResponseDto
```

---

## DELETE

Returns

```http
204 NO CONTENT
```

---

# Pagination

Problem

```java
findAll()
```

Problems:

- High Memory Usage
- Slow Responses
- Large Network Payload
- Frontend Performance Issues

---

## Pageable

Represents pagination request.

```java
Pageable pageable
```

---

## PageRequest

Creates Pageable.

```java
PageRequest.of(0,10)
```

Meaning:

```text
Page Number = 0
Page Size = 10
```

---

## Page

```java
Page<Employee>
```

Contains:

```java
getContent()
getNumber()
getSize()
getTotalPages()
getTotalElements()
```

---

Example

25 Employees

```text
Page 0 -> Employees 1-10

Page 1 -> Employees 11-20

Page 2 -> Employees 21-25
```

---

Service

```java
public Page<EmployeeResponseDto>
getAllEmployees(
        int page,
        int size){

    Page<Employee> employeePage =
            employeeRepository.findAll(
                PageRequest.of(page,size));

    return employeePage.map(
            this::mapToResponseDto);
}
```

---

# Sorting (Current Stage)

---

Ascending

```java
Sort.by("salary")
```

Output

```text
30000
50000
70000
90000
```

---

Descending

```java
Sort.by("salary")
    .descending()
```

Output

```text
90000
70000
50000
30000
```

---

Current Implementation

```java
public Page<EmployeeResponseDto>
getAllEmployees(
        int page,
        int size,
        String sortBy,
        String direction){

    Sort sort =
            direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

    Pageable pageable =
            PageRequest.of(
                    page,
                    size,
                    sort);

    Page<Employee> employeePage =
            employeeRepository.findAll(pageable);

    return employeePage.map(
            this::mapToResponseDto);
}
```

Controller

```java
@GetMapping("/employees")
public ResponseEntity<
        Page<EmployeeResponseDto>>
getAllEmployees(

        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam String direction){

    return ResponseEntity.ok(
            employeeService.getAllEmployees(
                    page,
                    size,
                    sortBy,
                    direction));
}
```

---

# Current Roadmap Status

## Phase 1

✅ CRUD

---

## Phase 2

✅ DTO

✅ Validation

✅ Exception Handling

✅ ResponseEntity

---

## Phase 3

✅ Pagination

🔄 Sorting (Current)

⬜ Filtering

⬜ Searching

⬜ JPQL

⬜ Native SQL

---

## Phase 4

⬜ JPA Relationships

---

## Phase 5

⬜ Testing

---

## Phase 6

⬜ Spring Security

---

## Phase 7

⬜ Microservices

---

## Phase 8

⬜ Kafka

---

## Phase 9

⬜ Angular Integration

---

## Phase 10

⬜ Docker

⬜ Kubernetes

---

## Phase 11

⬜ Production Debugging

---

## Phase 12

⬜ Mock Interview