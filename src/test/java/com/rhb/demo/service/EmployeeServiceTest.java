package com.rhb.demo.service;

import com.rhb.demo.dto.EmployeeDepartmentDTO;
import com.rhb.demo.entity.Department;
import com.rhb.demo.entity.Employee;
import com.rhb.demo.exception.ResourceNotFoundException;
import com.rhb.demo.repository.DepartmentRepository;
import com.rhb.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("Engineering");
        department.setLocation("San Francisco");

        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setDepartment(department);
    }

    @Test
    void testGetAllEmployees() {

        List<Employee> employees = Arrays.asList(employee);
        when(employeeRepository.findAll()).thenReturn(employees);


        List<Employee> result = employeeService.getAllEmployees();


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById_Success() {

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));


        Employee result = employeeService.getEmployeeById(1L);


        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEmployeeById_NotFound() {

        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(999L);
        });
        verify(employeeRepository, times(1)).findById(999L);
    }

    @Test
    void testCreateEmployee() {

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);


        Employee result = employeeService.createEmployee(employee);


        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee() {

        Employee updatedEmployee = new Employee(1L, "Jane", "Smith", "jane.smith@example.com", department);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);


        Employee result = employeeService.updateEmployee(1L, updatedEmployee);


        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee() {

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(any(Employee.class));


        employeeService.deleteEmployee(1L);


        verify(employeeRepository, times(1)).delete(any(Employee.class));
    }

    @Test
    void testSearchEmployees() {

        List<Employee> employees = Arrays.asList(employee);
        Page<Employee> employeePage = new PageImpl<>(employees);
        Pageable pageable = PageRequest.of(0, 10);

        when(employeeRepository.searchEmployees(anyString(), any(Pageable.class))).thenReturn(employeePage);


        Page<Employee> result = employeeService.searchEmployees("John", pageable);


        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("John", result.getContent().get(0).getFirstName());
        verify(employeeRepository, times(1)).searchEmployees(anyString(), any(Pageable.class));
    }

    @Test
    void testSearchEmployeesWithNullKeyword() {

        List<Employee> employees = Arrays.asList(employee);
        Page<Employee> employeePage = new PageImpl<>(employees);
        Pageable pageable = PageRequest.of(0, 10);

        when(employeeRepository.findAll(pageable)).thenReturn(employeePage);


        Page<Employee> result = employeeService.searchEmployees(null, pageable);


        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(employeeRepository, times(1)).findAll(pageable);
        verify(employeeRepository, times(0)).searchEmployees(anyString(), any(Pageable.class));
    }

    @Test
    void testGetEmployeesWithDepartment() {

        EmployeeDepartmentDTO dto = new EmployeeDepartmentDTO(1L, "John", "Doe", "john.doe@example.com", 1L, "Engineering", "San Francisco");
        List<EmployeeDepartmentDTO> dtoList = Arrays.asList(dto);

        when(employeeRepository.findAllWithDepartment()).thenReturn(dtoList);


        List<EmployeeDepartmentDTO> result = employeeService.getEmployeesWithDepartment();


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Engineering", result.get(0).getDepartmentName());
        verify(employeeRepository, times(1)).findAllWithDepartment();
    }

    @Test
    void testGetEmployeesByLocation() {

        EmployeeDepartmentDTO dto = new EmployeeDepartmentDTO(1L, "John", "Doe", "john.doe@example.com", 1L, "Engineering", "San Francisco");
        List<EmployeeDepartmentDTO> dtoList = Arrays.asList(dto);

        when(employeeRepository.findEmployeesByLocation("San Francisco")).thenReturn(dtoList);


        List<EmployeeDepartmentDTO> result = employeeService.getEmployeesByLocation("San Francisco");


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("San Francisco", result.get(0).getDepartmentLocation());
        verify(employeeRepository, times(1)).findEmployeesByLocation("San Francisco");
    }
}

