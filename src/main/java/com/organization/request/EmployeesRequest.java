package com.organization.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmployeesRequest {
	@NotBlank(message = "'employeeName' should not be empty or null")
	private String employeeName;
	@NotEmpty(message = "'employeeDepartment' shount not be empty")
	private String employeeDepartment;
	@Min(18)
	@Max(60)
	private int employeeAge;
	@NotNull
	@Min(value = 0, message = "Salary must be greater than or equal to zero")
	private double employeeSalary;
	@NotBlank
	private String password;
	@NotEmpty
	private String roles;
}
