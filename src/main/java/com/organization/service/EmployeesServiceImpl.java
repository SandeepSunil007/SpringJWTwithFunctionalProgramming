package com.organization.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.organization.entity.Employees;
import com.organization.entity.repository.EmployeesRepository;
import com.organization.exception.EmployeeIdNotPresntIntoDB;
import com.organization.request.EmployeesRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl implements EmployeeService {

	private final EmployeesRepository employeesRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Employees addEmployeeDetails(EmployeesRequest employeesRequest) {
		Employees employees = Employees.builder().employeeName(employeesRequest.getEmployeeName())
				.employeeAge(employeesRequest.getEmployeeAge())
				.employeeDepartment(employeesRequest.getEmployeeDepartment())
				.employeeSalary(employeesRequest.getEmployeeSalary())
				.password(passwordEncoder.encode(employeesRequest.getPassword()))
				.roles(employeesRequest.getRoles())
				.build();
		return employeesRepository.save(employees);
	}

//	@Override
//	public String deleteEmployeeDetailsBasedOnId(Integer employeeId) {
//		Optional<Employees> byEmployeeIdAndIsDelete = employeesRepository.findByEmployeeIdAndIsDelete(employeeId,
//				false);
//		if (byEmployeeIdAndIsDelete.isPresent()) {
//			Optional<Employees> employee = employeesRepository.findById(employeeId);
//			if (employee.isPresent()) {
//				Employees employees = employee.get();
//
//				employees.setDelete(true);
//				employeesRepository.save(employees);
//			} else {
//				throw new EmployeeIdNotPresntIntoDB(employeeId + " Id not presnt into Database");
//			}
//		} else {
//			throw new EmployeeIdNotPresntIntoDB(employeeId + " Id not presnt into Database");
//		}
//
//		return "Deleted The Employee Data based on employee ID";
//
//	}
	
	@Override
	public String deleteEmployeeDetailsBasedOnId(Integer employeeId) {
	    Employees employee = employeesRepository.findById(employeeId)
	            .orElseThrow(() -> new EmployeeIdNotPresntIntoDB(employeeId + " Id not presnt into Database"));

	    if (!employee.isDelete()) {
	        employee.setDelete(true);
	        employeesRepository.save(employee);
	        return "Deleted The Employee Data based on employee ID";
	    } else {
	        throw new EmployeeIdNotPresntIntoDB(employeeId + " Id is already deleted");
	    }
	}


	@Override
	public Employees getEmployeeDetailsBasedOnId(Integer employeeId) {
		return employeesRepository.findByEmployeeIdAndIsDelete(employeeId, false)
				.orElseThrow(() -> new EmployeeIdNotPresntIntoDB(employeeId + " Id not presnt into Database"));
	}

	@Override
	public String updateEmployeeDetailsBasedOnEmployeeId(EmployeesRequest employeesRequest, Integer employeeId) {
		Optional<Employees> byEmployeeIdAndIsDelete = employeesRepository.findByEmployeeIdAndIsDelete(employeeId,
				false);
		if (byEmployeeIdAndIsDelete.isPresent()) {
			Employees employees = byEmployeeIdAndIsDelete.get();
			BeanUtils.copyProperties(employeesRequest, employees);
			employeesRepository.save(employees);

		} else {
			throw new EmployeeIdNotPresntIntoDB(employeeId + " Id not presnt into Database");

		}
		return "we update the employee details";
	}

	@Override
	public List<Employees> getAllEmployeeDetails() {
		return employeesRepository.findAll().stream().filter(x -> !x.isDelete()).collect(Collectors.toList());
	}

}
