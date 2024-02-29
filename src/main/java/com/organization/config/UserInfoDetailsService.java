package com.organization.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.organization.entity.Employees;
import com.organization.entity.repository.EmployeesRepository;

@Component
public class UserInfoDetailsService implements UserDetailsService {

	@Autowired
	private EmployeesRepository employeesRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Employees> employee = employeesRepository.findByEmployeeName(username);
		return employee.map(UserSecurityDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException(username + " Name not found in database"));
	}

}
