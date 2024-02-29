package com.organization.service;

import java.util.List;

import com.organization.entity.Employees;
import com.organization.request.EmployeesRequest;
public interface EmployeeService {

	public Employees addEmployeeDetails(EmployeesRequest employeesRequest);

	public Employees getEmployeeDetailsBasedOnId(Integer employeeId);

	public String deleteEmployeeDetailsBasedOnId(Integer employeeId);
	
	public List<Employees> getAllEmployeeDetails();

	public String updateEmployeeDetailsBasedOnEmployeeId(EmployeesRequest employeesRequest, Integer employeeId);

}
