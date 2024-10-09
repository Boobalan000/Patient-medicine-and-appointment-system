package com.appointment_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.appointment_system.entity.User;
import com.appointment_system.entity.model.UserModel;
import com.appointment_system.repository.UserRepository;

@Service
public class UserDetailsServicecUser implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user=userRepository.findByEmail(email);
		
		if(user==null)
			throw new UsernameNotFoundException("Email is not found");
		
		return new UserModel(user);
	}

}
