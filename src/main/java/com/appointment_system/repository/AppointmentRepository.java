package com.appointment_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment_system.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	List<Appointment> findByUserId(int id);
	
	List<Appointment> findByDoctorName(String name);
}
