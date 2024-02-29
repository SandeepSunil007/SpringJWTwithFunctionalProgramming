package com.organization.runtimeexceptionhandel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.organization.exception.EmployeeIdNotPresntIntoDB;

@RestControllerAdvice
public class GlobalExceptionHanler {
	@ExceptionHandler(EmployeeIdNotPresntIntoDB.class)
	public ResponseEntity<String> handleEmployeeIdNotPresntIntoDB(EmployeeIdNotPresntIntoDB ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

	}

}
