package com.rhb.demo.service;

import com.rhb.demo.entity.Department;
import com.rhb.demo.exception.ResourceNotFoundException;
import com.rhb.demo.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        log.info("Fetching all departments");
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        log.info("Fetching department with id: {}", id);
        return departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    public Department createDepartment(Department department) {
        log.info("Creating new department: {}", department.getName());
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department departmentDetails) {
        log.info("Updating department with id: {}", id);

        Department department = getDepartmentById(id);
        department.setName(departmentDetails.getName());
        department.setLocation(departmentDetails.getLocation());

        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        log.info("Deleting department with id: {}", id);
        Department department = getDepartmentById(id);
        departmentRepository.delete(department);
    }
}

