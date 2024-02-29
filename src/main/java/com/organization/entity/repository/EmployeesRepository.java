package com.organization.entity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.organization.entity.Employees;

public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

	Optional<Employees> findByEmployeeIdAndIsDelete(Integer employeeId, boolean isDelete);

	Optional<Employees> findByEmployeeName(String username);

}
