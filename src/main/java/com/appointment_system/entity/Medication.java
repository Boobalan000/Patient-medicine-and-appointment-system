package com.appointment_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Medication {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String patientEmail;
	
	private String medicine;
	
	private String morning;
	
	private String afternoon;
	
	private String evening;
	
	private String night;
	
	private String afterOrBefore;
	
	private String appointmentId;
	
	@ManyToOne
	@JoinColumn(name="doctor_id")
	private Doctor doctor;
	
}
