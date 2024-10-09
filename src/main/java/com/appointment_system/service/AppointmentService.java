package com.appointment_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appointment_system.entity.Appointment;
import com.appointment_system.entity.Doctor;
import com.appointment_system.entity.User;
import com.appointment_system.repository.AppointmentRepository;
import com.appointment_system.repository.DoctorRepository;
import com.appointment_system.repository.UserRepository;

@Service
public class AppointmentService {

	@Autowired
	private  UserRepository userRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	public List<Appointment> getAllAppointmentsByUser(String email)
	{
		User user=userRepository.findByEmail(email);
		return (user!=null)?(appointmentRepository.findByUserId(user.getId())):( null);
	}
	
	public List<Appointment> getAllAppointmentsByDdooctor(String email)
	{
		Doctor doctor=doctorRepository.findByEmail(email);
		return (doctor!=null)?(appointmentRepository.findByDoctorName(doctor.getName())):(null);
	}
	
	public boolean save(Appointment appointment)
	{
		return (appointmentRepository.save(appointment)!=null);
	}
}
