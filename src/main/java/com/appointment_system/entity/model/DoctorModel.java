package com.appointment_system.entity.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.appointment_system.entity.Doctor;

public class DoctorModel implements UserDetails {

	private Doctor doctor;
	
	public DoctorModel(Doctor doctor)
	{
		this.doctor=doctor;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_Doctor"));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return doctor.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return doctor.getEmail();
	}

}
