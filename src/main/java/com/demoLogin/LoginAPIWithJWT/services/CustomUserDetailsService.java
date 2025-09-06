package com.demoLogin.LoginAPIWithJWT.services;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demoLogin.LoginAPIWithJWT.entity.UserEntity;
import com.demoLogin.LoginAPIWithJWT.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository repo;
	
	public CustomUserDetailsService(UserRepository repo)
	{
		this.repo=repo;
		
	}
	 
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
	{
		UserEntity user=repo.findByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException("User not found"+email));
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getEmail())
				.password(user.getPassword())
				.authorities(user.getRoles().stream() .map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
				.build();
	}
	
	
	
	
	
	
	
	

}
