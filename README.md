# RHB Demo - Dhruval Vaishnav

A comprehensive Spring Boot application demonstrating enterprise-level Java development practices with REST APIs, database operations, pagination, logging, and external API integration.

## 📋 Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Key Implementations](#key-implementations)

## ✨ Features

This application implements all the required features:

- ✅ **Multiple HTTP Methods**: GET, POST, PUT, DELETE operations
- ✅ **Search with Pagination**: Advanced search functionality with pagination support
- ✅ **AspectJ Logging**: Comprehensive request/response logging using AspectJ
- ✅ **In-Memory Database**: H2 database for development and testing
- ✅ **JOIN Queries**: Sample queries that join Employee and Department tables
- ✅ **External API Integration**: Service method to call external APIs (Google.com example)
- ✅ **Unit Tests**: Comprehensive test coverage for services and controllers
- ✅ **Clean Architecture**: Easy to modify, understand, and extend

## 🛠 Technologies Used

- **Java 17**
- **Spring Boot 3.2.0**
  - Spring Web
  - Spring Data JPA
  - Spring AOP (AspectJ)
- **H2 Database** (In-Memory)
- **Maven** (Build Tool)
- **Lombok** (Code Generation)
- **JUnit 5 & Mockito** (Testing)
- **RestTemplate** (External API Calls)

## 📦 Prerequisites

Before running this application, ensure you have:

- **JDK 17** or higher installed
- **Maven 3.6+** installed
- **Git** (for cloning the repository)
- **Postman** (optional, for API testing)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd RHB_Demo_Dhruval_V
```

### 2. Build the Application

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on **http://localhost:8080**

### 4. Access H2 Console (Optional)

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:rhbdb`
- Username: `sa`
- Password: (leave blank)

## 📚 API Documentation

### Employee APIs

#### 1. Get All Employees
```
GET http://localhost:8080/api/employees
```

#### 2. Get Employee by ID
```
GET http://localhost:8080/api/employees/{id}
```

#### 3. Create Employee
```
POST http://localhost:8080/api/employees
Content-Type: application/json

{
  "firstName": "Michael",
  "lastName": "Jordan",
  "email": "michael.jordan@example.com",
  "department": {
    "id": 1
  }
}
```

#### 4. Update Employee
```
PUT http://localhost:8080/api/employees/{id}
Content-Type: application/json

{
  "firstName": "John Updated",
  "lastName": "Doe Updated",
  "email": "john.updated@example.com",
  "department": {
    "id": 2
  }
}
```

#### 5. Delete Employee
```
DELETE http://localhost:8080/api/employees/{id}
```

#### 6. Search Employees with Pagination
```
GET http://localhost:8080/api/employees/search?keyword=john&page=0&size=5&sortBy=firstName&sortDirection=asc
```

**Query Parameters:**
- `keyword`: Search term (searches in firstName, lastName, email)
- `page`: Page number (default: 0)
- `size`: Page size (default: 10)
- `sortBy`: Field to sort by (default: id)
- `sortDirection`: Sort direction - asc/desc (default: asc)

#### 7. Get Employees with Department (JOIN Query)
```
GET http://localhost:8080/api/employees/withDepartment
```

#### 8. Get Employees by Department Location (JOIN Query)
```
GET http://localhost:8080/api/employees/byLocation/{location}
```

### Department APIs

#### 1. Get All Departments
```
GET http://localhost:8080/api/departments
```

#### 2. Get Department by ID
```
GET http://localhost:8080/api/departments/{id}
```

#### 3. Create Department
```
POST http://localhost:8080/api/departments
Content-Type: application/json

{
  "name": "Finance",
  "location": "Boston"
}
```

#### 4. Update Department
```
PUT http://localhost:8080/api/departments/{id}
Content-Type: application/json

{
  "name": "Engineering Updated",
  "location": "Seattle"
}
```

#### 5. Delete Department
```
DELETE http://localhost:8080/api/departments/{id}
```

### External API

#### 1. Test Google API Call
```
GET http://localhost:8080/api/external/testGoogle
```

#### 2. Call Custom External API
```
POST http://localhost:8080/api/external/call
Content-Type: application/json

{
  "url": "https://www.google.com"
}
```

## 🗄 Database Schema

### Employee Table
```sql
CREATE TABLE employee (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  department_id BIGINT,
  FOREIGN KEY (department_id) REFERENCES department(id)
);
```

### Department Table
```sql
CREATE TABLE department (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  location VARCHAR(255)
);
```

### Sample Data

The application automatically loads sample data on startup:
- 4 Departments (Engineering, Marketing, Sales, HR)
- 10 Employees distributed across departments

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=EmployeeServiceTest
```

### Test Coverage

The project includes:
- **Unit Tests** for Service layer
- **Integration Tests** for Controller layer
- **Mock-based Testing** using Mockito

## 📁 Project Structure

```
RHB_Demo_Dhruval_V/
├── src/
│   ├── main/
│   │   ├── java/com/rhb/demo/
│   │   │   ├── aspect/              # AspectJ logging components
│   │   │   │   └── LoggingAspect.java
│   │   │   ├── controller/          # REST Controllers
│   │   │   │   ├── EmployeeController.java
│   │   │   │   ├── DepartmentController.java
│   │   │   │   └── ExternalApiController.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   └── EmployeeDepartmentDTO.java
│   │   │   ├── entity/              # JPA Entities
│   │   │   │   ├── Employee.java
│   │   │   │   └── Department.java
│   │   │   ├── exception/           # Exception handling
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── repository/          # JPA Repositories
│   │   │   │   ├── EmployeeRepository.java
│   │   │   │   └── DepartmentRepository.java
│   │   │   ├── service/             # Business Logic
│   │   │   │   ├── EmployeeService.java
│   │   │   │   ├── DepartmentService.java
│   │   │   │   └── ExternalApiService.java
│   │   │   └── RhbDemoApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql             # Sample data
│   └── test/                        # Unit & Integration Tests
│       └── java/com/rhb/demo/
├── pom.xml
├── README.md
└── RHB_Demo_Postman_Collection.json
```

## 🔑 Key Implementations

### 1. AspectJ Logging

The `LoggingAspect` class intercepts all controller and service method calls:
- Logs all HTTP requests with method, URI, and parameters
- Logs all HTTP responses with execution time
- Logs exceptions with stack traces
- Located in: `src/main/java/com/rhb/demo/aspect/LoggingAspect.java`

### 2. Pagination

Implemented using Spring Data's `Pageable` interface:
```java
Page<Employee> searchEmployees(String keyword, Pageable pageable);
```

Response includes:
- Current page content
- Total elements
- Total pages
- Has next/previous page flags

### 3. JOIN Queries

Two types of JOIN queries implemented:

**Query 1: Get all employees with department details**
```java
@Query("SELECT new com.rhb.demo.dto.EmployeeDepartmentDTO(...) " +
       "FROM Employee e INNER JOIN e.department d")
List<EmployeeDepartmentDTO> findAllEmployeesWithDepartment();
```

**Query 2: Get employees by department location**
```java
@Query("SELECT new com.rhb.demo.dto.EmployeeDepartmentDTO(...) " +
       "FROM Employee e INNER JOIN e.department d " +
       "WHERE d.location = :location")
List<EmployeeDepartmentDTO> findEmployeesWithDepartmentByLocation(@Param("location") String location);
```

### 4. External API Integration

The `ExternalApiService` uses Spring's RestTemplate:
```java
public String callExternalApi(String url) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("User-Agent", "Mozilla/5.0");
    HttpEntity<String> entity = new HttpEntity<>(headers);
    
    ResponseEntity<String> response = restTemplate.exchange(
        url, HttpMethod.GET, entity, String.class
    );
    // ... process response
}
```

### 5. Exception Handling

Global exception handler using `@RestControllerAdvice`:
- Handles `ResourceNotFoundException` → 404
- Handles general exceptions → 500
- Returns consistent error response structure

## 📮 Postman Collection

Import the `RHB_Demo_Postman_Collection.json` file into Postman to test all APIs.

**To Import:**
1. Open Postman
2. Click on "Import" button
3. Select the `RHB_Demo_Postman_Collection.json` file
4. All API endpoints will be available for testing

## 🎯 Design Principles

This application follows:
- **SOLID Principles**
- **Clean Architecture**
- **RESTful API Design**
- **Separation of Concerns**
- **Dependency Injection**
- **Repository Pattern**
- **DTO Pattern**

## 📝 Notes

- The H2 database is in-memory, so data will be lost when the application stops
- All requests and responses are logged using AspectJ
- The application includes comprehensive error handling
- Unit tests provide good coverage of business logic

## 👤 Author

**Dhruval V**

## 📄 License

This project is created as a demonstration for RHB.

---

**Happy Coding! 🚀**

