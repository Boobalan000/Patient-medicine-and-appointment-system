package com.appointment_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment_system.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

	Doctor findByEmail(String email);
}
