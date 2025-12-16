package com.rhb.demo.repository;

import com.rhb.demo.dto.EmployeeDepartmentDTO;
import com.rhb.demo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE " + "LOWER(e.firstName) LIKE :keyword OR " + "LOWER(e.lastName) LIKE :keyword OR " + "LOWER(e.email) LIKE :keyword")
    Page<Employee> searchEmployees(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT new com.rhb.demo.dto.EmployeeDepartmentDTO(" + "e.id, e.firstName, e.lastName, e.email, d.id, d.name, d.location) " + "FROM Employee e " + "INNER JOIN e.department d " + "WHERE d.location = :location")
    List<EmployeeDepartmentDTO> findEmployeesByLocation(@Param("location") String location);

    @Query("SELECT new com.rhb.demo.dto.EmployeeDepartmentDTO(" + "e.id, e.firstName, e.lastName, e.email, d.id, d.name, d.location) " + "FROM Employee e " + "INNER JOIN e.department d")
    List<EmployeeDepartmentDTO> findAllWithDepartment();
}

