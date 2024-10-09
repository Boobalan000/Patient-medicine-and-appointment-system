package com.appointment_system.entity;

import java.util.List;

import com.appointment_system.entity.enums.Speciality;
import com.appointment_system.entity.enums.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Doctor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Speciality speciality;
	
	@Enumerated(EnumType.STRING)
	private Time time;
	
	private String email;
	
	private String password;
	
	@OneToMany(mappedBy = "doctor")
	private List<Medication> medication;
	
	@OneToMany(mappedBy = "doctorToken")
	private List<Token> token;
}
