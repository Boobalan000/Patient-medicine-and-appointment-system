package com.appointment_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDetailsServiceDoctor userDetailsServiceDoctor;
	
	@Autowired
	private UserDetailsServicecUser userDetailsServiceUser;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		if(email.endsWith("@doctor.com"))
			return userDetailsServiceDoctor.loadUserByUsername(email);
		

		return userDetailsServiceUser.loadUserByUsername(email);
	}

	
}
