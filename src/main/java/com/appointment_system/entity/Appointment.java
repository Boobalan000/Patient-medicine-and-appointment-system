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
public class Appointment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String userName;
	
	private String email;
	
	private String number;
	
	private String address;
	
	private String doctorName;
	
	private String date;
	
	private String problem;
	
	private String appointmentId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
}
