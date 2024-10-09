package com.appointment_system.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.appointment_system.filter.JWTFilter;
import com.appointment_system.service.CustomUserDetailsService;
import com.appointment_system.service.LogoutService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

	@Autowired
	private JWTFilter jwtFilter;
	
	@Autowired 
	private LogoutService logoutHandler;
	
	@Autowired 
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	SecurityFilterChain security(HttpSecurity http) throws Exception
	{
		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(authorize->authorize
				.requestMatchers("/user/register", "/", "/user/login", "/bg.jpg", "/doctor/login", "/button/quick", "/button/available", "/button/customer", "/privacy", "/terms", 
						"/swagger-ui/index.html", 
						"/v3/api-docs/**",      // OpenAPI documentation
                        "/swagger-ui/**",       // Swagger UI
                        "/swagger-resources/**", // Swagger resources
                        "/webjars/**").permitAll()
				.requestMatchers("/user/**").hasRole("User")
				.requestMatchers("/doctor/**").hasRole("Doctor")
				.anyRequest().authenticated())
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		.logout(logout->logout
				.logoutUrl("/user/logout")
				.logoutSuccessUrl("/")
				.addLogoutHandler(logoutHandler)
				.logoutSuccessHandler((request,response,authentication)->
				{
					SecurityContextHolder.clearContext();
					response.sendRedirect("/");
				}));
		return http.build();
	}
	
	@Bean
	AuthenticationProvider provider()
	{
		DaoAuthenticationProvider daoAuthentication=new DaoAuthenticationProvider();
		daoAuthentication.setUserDetailsService(customUserDetailsService);
		daoAuthentication.setPasswordEncoder(new BCryptPasswordEncoder(12));
		
		return daoAuthentication;
	}
	
	@Bean
	AuthenticationManager manager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
	
}
