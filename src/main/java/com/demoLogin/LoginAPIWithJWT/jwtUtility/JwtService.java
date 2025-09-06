package com.demoLogin.LoginAPIWithJWT.jwtUtility;


import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private final Key key;
	private final Long expirationMs;
	public JwtService(@Value(" ${app.jwt.secret}") String secret,
					@Value("${app.jwt.expiration-ms}") Long expirationMs) {
		
		this.key =Keys.hmacShaKeyFor(secret.getBytes());
		this.expirationMs = expirationMs;
	}
	
	public String generateToken(String subject,Map<String,Object> claims)
	{
		Date now=new Date();
		Date exp=new Date(now.getTime() + expirationMs);
		
		return Jwts.builder()
				.setSubject(subject)
				.addClaims(claims)
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(key,SignatureAlgorithm.HS256)
				.compact();
		
	}
	
	public String extractSubject(String token)
	{
		return parse(token).getBody().getSubject();
	}
	
	public Claims extractAllClaims(String token)
	{
		return parse(token).getBody();
	}
	
	public boolean isTokenValid(String token,String email)
	{
		try {
			Claims c=extractAllClaims(token);
			return email.equals(c.getSubject()) && c.getExpiration().after(new Date());
			
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	private Jws<Claims> parse(String token)
	{
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
	}
	
	
	
	
	

}
