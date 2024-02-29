package com.organization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.organization.constants.EmployeesConstants.*;

import com.organization.jwt.AuthRequest;
import com.organization.jwt.JwtService;
import com.organization.request.EmployeesRequest;
import com.organization.response.EmployeeResponseBody;
import com.organization.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmployeesController {

	private final EmployeeService employeeService;

	@PostMapping("/addEmployee")
	public ResponseEntity<EmployeeResponseBody> addEmployee(@RequestBody @Valid EmployeesRequest employeesRequest) {
		return ResponseEntity
				.ok(EmployeeResponseBody.builder().isError(false).message(WE_ADDED_THE_DATA_INTO_DATABASE_SUCCESFULLY)
						.data(employeeService.addEmployeeDetails(employeesRequest)).build());
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/deleteEmployee/{employeeId}")
	public ResponseEntity<EmployeeResponseBody> deleteEmployeeDataBasedOnId(@PathVariable Integer employeeId) {
		return ResponseEntity.ok(
				EmployeeResponseBody.builder().isError(false).message(EMPLOYEE_DATA_DELETED_FROM_DATABASE_BASED_ON_ID)
						.data(employeeService.deleteEmployeeDetailsBasedOnId(employeeId)).build());
	}

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/getById/{empId}")
	public ResponseEntity<EmployeeResponseBody> getEmployeeDataBasedOnId(@PathVariable Integer empId) {
		return ResponseEntity
				.ok(EmployeeResponseBody.builder().isError(false).message(FETCHED_THE_DATA_BASED_ON_EMPLOYEE_ID)
						.data(employeeService.getEmployeeDetailsBasedOnId(empId)).build());

	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/update/{empId}")
	public ResponseEntity<EmployeeResponseBody> updateEmployeeDetailsBasedOnId(
			@RequestBody EmployeesRequest employeesRequest, @PathVariable Integer empId) {
		return ResponseEntity
				.ok(EmployeeResponseBody.builder().isError(false).message("Updated the Employee Details Succesfully")
						.data(employeeService.updateEmployeeDetailsBasedOnEmployeeId(employeesRequest, empId)).build());
	}

	@GetMapping("/getAllEmployees")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<EmployeeResponseBody> getAllEmployees() {
		return ResponseEntity.ok(EmployeeResponseBody.builder().isError(false)
				.message(WE_GOT_ALL_THE_DATA_FROM_DATABASE).data(employeeService.getAllEmployeeDetails()).build());
	}

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/authenticate")
	public String generateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authenticate.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException(authRequest.getUsername() + " username not presnt into database");
		}

	}

}
