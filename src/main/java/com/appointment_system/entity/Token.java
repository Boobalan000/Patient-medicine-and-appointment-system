package com.appointment_system.entity;

import com.appointment_system.entity.enums.TokenType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String token;
	
	@Enumerated(EnumType.STRING)
	private TokenType tokenType;
	
	private boolean expired;
	
	private boolean revoked;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userToken;
	
	@ManyToOne
	@JoinColumn(name="doctor_id")
	private Doctor doctorToken;
}
