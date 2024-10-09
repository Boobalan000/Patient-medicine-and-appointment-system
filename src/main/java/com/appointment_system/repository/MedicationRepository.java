package com.appointment_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment_system.entity.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Integer> {

	List<Medication> findByPatientEmail(String email);
	
	List<Medication> findByDoctorId(int id);
}
