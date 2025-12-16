package com.rhb.demo.service;

import com.rhb.demo.dto.EmployeeDepartmentDTO;
import com.rhb.demo.entity.Department;
import com.rhb.demo.entity.Employee;
import com.rhb.demo.exception.ResourceNotFoundException;
import com.rhb.demo.repository.DepartmentRepository;
import com.rhb.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees");
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public Employee createEmployee(Employee employee) {
        log.info("Creating new employee: {}", employee.getEmail());

        if (employee.getDepartment() != null && employee.getDepartment().getId() != null) {
            Department department = departmentRepository.findById(employee.getDepartment().getId()).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            employee.setDepartment(department);
        }

        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        log.info("Updating employee with id: {}", id);

        Employee employee = getEmployeeById(id);
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());

        if (employeeDetails.getDepartment() != null && employeeDetails.getDepartment().getId() != null) {
            Department department = departmentRepository.findById(employeeDetails.getDepartment().getId()).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            employee.setDepartment(department);
        }

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

    public Page<Employee> searchEmployees(String keyword, Pageable pageable) {
        String searchKeyword = keyword == null || keyword.isBlank() ? null : "%" + keyword.trim().toLowerCase() + "%";
        log.info("Searching employees with keyword: {}, page: {}, size: {}", searchKeyword, pageable.getPageNumber(), pageable.getPageSize());

        if (searchKeyword == null) {
            return employeeRepository.findAll(pageable);
        }

        return employeeRepository.searchEmployees(searchKeyword, pageable);
    }

    public List<EmployeeDepartmentDTO> getEmployeesWithDepartment() {
        log.info("Fetching all employees with department details");
        return employeeRepository.findAllWithDepartment();
    }

    public List<EmployeeDepartmentDTO> getEmployeesByLocation(String location) {
        log.info("Fetching employees by location: {}", location);
        return employeeRepository.findEmployeesByLocation(location);
    }
}

