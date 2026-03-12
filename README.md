# 🏦 RHB Demo - Enterprise Spring Boot Application

> A production-grade Spring Boot application demonstrating enterprise Java development patterns - REST APIs, JPA with JOIN queries, AspectJ logging, pagination, H2 in-memory database, and external API integration.

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring AOP](https://img.shields.io/badge/AspectJ_Logging-✓-6DB33F?style=for-the-badge&logo=spring)](https://spring.io)
[![H2](https://img.shields.io/badge/H2_Database-In--Memory-blue?style=for-the-badge)](https://h2database.com)
[![JUnit5](https://img.shields.io/badge/JUnit5_+_Mockito-Tests-25A162?style=for-the-badge&logo=junit5)](https://junit.org)

---

## ✨ Features

| Feature | Implementation |
|---|---|
| 🔁 Full CRUD REST APIs | GET, POST, PUT, DELETE on Employee & Department |
| 🔍 Search with Pagination | Keyword search with page/size/sort controls |
| 📋 AspectJ Logging | Auto-logs all controller & service calls with execution time |
| 🗄️ In-Memory Database | H2 with pre-loaded sample data |
| 🔗 JOIN Queries | JPQL queries joining Employee ↔ Department |
| 🌐 External API Integration | RestTemplate-based HTTP client to external services |
| 🧪 Unit & Integration Tests | JUnit 5 + Mockito for service and controller layers |
| ⚠️ Global Exception Handling | `@RestControllerAdvice` with consistent error responses |

---

## 🛠️ Tech Stack

- **Java 17** · **Spring Boot 3.2** · **Spring Data JPA** · **Spring AOP**
- **H2 Database** · **Lombok** · **Maven**
- **JUnit 5** · **Mockito** · **RestTemplate**

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+

### Run

```bash
git clone https://github.com/dhruvalvaishnav/RHB_Demo_Dhruval_V.git
cd RHB_Demo_Dhruval_V
mvn clean install
mvn spring-boot:run
```

App starts at **http://localhost:8080**

### H2 Console
```
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:rhbdb
Username: sa
Password: (blank)
```

---

## 📚 API Reference

### Employee APIs

```
GET    /api/employees                          # All employees
GET    /api/employees/{id}                     # By ID
POST   /api/employees                          # Create
PUT    /api/employees/{id}                     # Update
DELETE /api/employees/{id}                     # Delete
GET    /api/employees/search?keyword=john&page=0&size=5&sortBy=firstName&sortDirection=asc
GET    /api/employees/withDepartment           # JOIN query
GET    /api/employees/byLocation/{location}    # JOIN query by location
```

### Department APIs

```
GET    /api/departments
GET    /api/departments/{id}
POST   /api/departments
PUT    /api/departments/{id}
DELETE /api/departments/{id}
```

### External API

```
GET    /api/external/testGoogle
POST   /api/external/call       # Body: { "url": "https://..." }
```

---

## 🔑 Key Implementations

### AspectJ Logging
`LoggingAspect` intercepts all controller and service calls — logs method name, parameters, execution time, and exceptions automatically with zero boilerplate.

### Pagination
Uses Spring Data's `Pageable` — response includes total pages, total elements, and has-next/has-previous flags.

### JOIN Queries (JPQL)
```java
@Query("SELECT new com.rhb.demo.dto.EmployeeDepartmentDTO(...) 
        FROM Employee e INNER JOIN e.department d 
        WHERE d.location = :location")
List<EmployeeDepartmentDTO> findEmployeesWithDepartmentByLocation(@Param("location") String location);
```

### Global Exception Handler
`@RestControllerAdvice` maps `ResourceNotFoundException` → 404 and all others → 500, returning a consistent JSON error structure.

---

## 🗄️ Database Schema

```sql
Department (id, name, location)
Employee   (id, first_name, last_name, email, department_id → FK)
```
Sample data: **4 departments**, **10 employees** auto-loaded on startup.

---

## 🧪 Running Tests

```bash
mvn test
# or a specific class:
mvn test -Dtest=EmployeeServiceTest
```

---

## 📁 Project Structure

```
src/main/java/com/rhb/demo/
├── aspect/        # AspectJ logging
├── controller/    # REST Controllers
├── dto/           # Data Transfer Objects
├── entity/        # JPA Entities
├── exception/     # Global exception handling
├── repository/    # JPA Repositories
└── service/       # Business logic
```

---

## 👨‍💻 Author

**Dhruval Vaishnav** - Senior Software Engineer | Java · Spring Boot · Kafka · AWS

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-0077B5?style=flat-square&logo=linkedin)](https://linkedin.com/in/dhruvalvaishnav)
[![Medium](https://img.shields.io/badge/Medium-Follow-12100E?style=flat-square&logo=medium)](https://medium.com/@vdhruval)
