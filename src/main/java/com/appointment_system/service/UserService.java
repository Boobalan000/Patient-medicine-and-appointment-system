package com.appointment_system.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appointment_system.entity.Token;
import com.appointment_system.entity.User;
import com.appointment_system.entity.enums.TokenType;
import com.appointment_system.repository.TokenRepository;
import com.appointment_system.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JWTService jwtService;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
	
	
	public boolean save(User user)
	{
		user.setPassword(encoder.encode(user.getPassword()));
		return (userRepository.save(user)!=null);
	}
	
	public boolean findByUserEmail(String email)
	{
		return (userRepository.findByEmail(email)!=null);
	}
	
	public User findUser(String email)
	{
		return userRepository.findByEmail(email);
	}
	
	public boolean verify(User user,HttpServletResponse response)
	{
		boolean result=false;
		
		try
		{
			Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
			
			if(authentication.isAuthenticated())
			{
				String token=jwtService.getToken(user.getEmail());
				
				User userFind=userRepository.findByEmail(user.getEmail());
				
				saveToken(userFind,token);
				
				cookieSave(token,response);
				
				result=true;
			}
		}
		catch(BadCredentialsException e)
		{
			System.out.println("User not found");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	private void saveToken(User user,String token)
	{
		var saveTokens=Token.builder()
				.userToken(user)
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
