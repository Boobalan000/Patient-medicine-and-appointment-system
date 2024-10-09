package com.appointment_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.appointment_system.entity.Doctor;
import com.appointment_system.entity.Token;
import com.appointment_system.entity.enums.TokenType;
import com.appointment_system.repository.DoctorRepository;
import com.appointment_system.repository.TokenRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	public List<Doctor> getAllDoctor()
	{
		return doctorRepository.findAll();
	}
	
	public Optional<Doctor> findById(int id)
	{
		return doctorRepository.findById(id);
	}
	
	public Doctor findByEmail(String email)
	{
		return doctorRepository.findByEmail(email);
	}
	
	public boolean verify(Doctor doctor,HttpServletResponse response)
	{
		boolean result=false;
		
		try
		{
			Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(doctor.getEmail(),doctor.getPassword()));
			
			if(authentication!=null)
			{
				String token=jwtService.getToken(doctor.getEmail());
				Doctor doctorFind=doctorRepository.findByEmail(doctor.getEmail());
				saveToken(token,doctorFind);
				cookieSave(token,response);
				result=true;
			}
		}
		catch(BadCredentialsException e)
		{
			System.out.println("Doctor not found");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	private void saveToken(String token,Doctor doctor)
	{
		var saveTokens=Token.builder()
				.doctorToken(doctor)
				.token(token)
				.tokenType(TokenType.Bearer)
				.expired(false)
				.revoked(false)
				.build();
		tokenRepository.save(saveTokens);
	}
	
	private void cookieSave(String token,HttpServletResponse response)
	{
		Cookie cookie=new Cookie("JWT","Bearer"+token);
		cookie.setHttpOnly(false);
		cookie.setSecure(true);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
