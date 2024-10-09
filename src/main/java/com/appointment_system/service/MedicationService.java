package com.appointment_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appointment_system.entity.Doctor;
import com.appointment_system.entity.Medication;
import com.appointment_system.repository.DoctorRepository;
import com.appointment_system.repository.MedicationRepository;

@Service
public class MedicationService {

	@Autowired
	private MedicationRepository medicationRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	public void save(Medication medication)
	{
		medicationRepository.save(medication);
	}
	
	public List<Medication> getAllMedicationUser(String email)
	{
		return (email!=null)?(medicationRepository.findByPatientEmail(email)):(null);
	}
	
	public List<Medication> getAllMedicationDoctor(String email)
	{
		Doctor doctor=doctorRepository.findByEmail(email);
		return (doctor!=null)?(medicationRepository.findByDoctorId(doctor.getId())):(null);
	}
	
	public void deleteById(int id)
	{
		medicationRepository.deleteById(id);
	}
	
	public Medication getMedicationById(int id)
	{
		return medicationRepository.findById(id).get();
	}
}
