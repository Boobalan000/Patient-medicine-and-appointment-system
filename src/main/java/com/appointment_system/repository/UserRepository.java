package com.appointment_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment_system.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);
	
	
}
