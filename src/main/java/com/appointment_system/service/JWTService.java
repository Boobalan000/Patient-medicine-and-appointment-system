package com.appointment_system.service;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

	private String key;
	
	public JWTService()
	{
		try 
		{
			KeyGenerator generatKey=KeyGenerator.getInstance("HMACSHA256");
			SecretKey secretKey=generatKey.generateKey();
			key=Base64.getEncoder().encodeToString(secretKey.getEncoded());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getToken(String email)
	{
		//this.email=email;
		
		Map<String,Object> claims=new HashMap<>();
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(email)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+600*600*100))
				.and()
				.signWith(getKey())
				.compact();
	}
	
	public String extractEmail(String token)
	{
		return extractClaim(token,Claims::getSubject);
	}
	
	public boolean validateToken(String token, UserDetails userDetails)
	{
		final String email=extractEmail(token);
		return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	
	
	private SecretKey getKey()
	{
		byte keyByte[]=Decoders.BASE64.decode(key);
		return Keys.hmacShaKeyFor(keyByte);
	}
	
	private <T> T extractClaim(String token,Function<Claims,T>claimResolver)
	{
		final Claims claims=extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token)
	{
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	private boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token)
	{
		return extractClaim(token,Claims::getExpiration);
	}
	
}
