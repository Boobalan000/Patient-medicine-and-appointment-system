package com.appointment_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.appointment_system.entity.Doctor;
import com.appointment_system.entity.model.DoctorModel;
import com.appointment_system.repository.DoctorRepository;

@Service
public class UserDetailsServiceDoctor implements UserDetailsService {

	@Autowired
	private DoctorRepository doctorRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Doctor doctor=doctorRepository.findByEmail(email);
		
		if(doctor==null)
			throw new UsernameNotFoundException("Email is not found");
		
		return new DoctorModel(doctor);
	}

}
