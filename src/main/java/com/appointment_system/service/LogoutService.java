package com.appointment_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.appointment_system.repository.TokenRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LogoutService implements LogoutHandler{

	@Autowired
	private TokenRepository tokenRepository;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

		Cookie cookie=WebUtils.getCookie(request, "JWT");
		String authHeader=cookie.getValue();
		String token=null;
		
		if(authHeader==null || !authHeader.startsWith("Bearer"))
		{
			return;
		}
		token=authHeader.substring(6);
		
		var storedToken=tokenRepository.findByToken(token)
				.orElse(null);
		
		if(storedToken!=null)
		{
			Cookie cookieLogout=new Cookie("JWT",null);
			cookieLogout.setPath("/");
			cookieLogout.setHttpOnly(false);
			cookieLogout.setMaxAge(0);
			
			response.addCookie(cookieLogout);
			
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokenRepository.save(storedToken);
		}
	}

}
