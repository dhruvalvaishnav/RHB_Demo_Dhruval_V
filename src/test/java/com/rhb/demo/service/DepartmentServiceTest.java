package com.rhb.demo.service;

import com.rhb.demo.entity.Department;
import com.rhb.demo.exception.ResourceNotFoundException;
import com.rhb.demo.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("Engineering");
        department.setLocation("San Francisco");
    }

    @Test
    void testGetAllDepartments() {

        List<Department> departments = Arrays.asList(department);
        when(departmentRepository.findAll()).thenReturn(departments);


        List<Department> result = departmentService.getAllDepartments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Engineering", result.get(0).getName());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void testGetDepartmentById_Success() {

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));


        Department result = departmentService.getDepartmentById(1L);

        assertNotNull(result);
        assertEquals("Engineering", result.getName());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDepartmentById_NotFound() {

        when(departmentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            departmentService.getDepartmentById(999L);
        });
        verify(departmentRepository, times(1)).findById(999L);
    }

    @Test
    void testCreateDepartment() {

        when(departmentRepository.save(any(Department.class))).thenReturn(department);


        Department result = departmentService.createDepartment(department);

        assertNotNull(result);
        assertEquals("Engineering", result.getName());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void testUpdateDepartment() {

        Department updatedDept = new Department();
        updatedDept.setId(1L);
        updatedDept.setName("Marketing");
        updatedDept.setLocation("New York");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(updatedDept);


        Department result = departmentService.updateDepartment(1L, updatedDept);

        assertNotNull(result);
        assertEquals("Marketing", result.getName());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void testDeleteDepartment() {

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        doNothing().when(departmentRepository).delete(any(Department.class));


        departmentService.deleteDepartment(1L);

        verify(departmentRepository, times(1)).delete(any(Department.class));
    }
}

