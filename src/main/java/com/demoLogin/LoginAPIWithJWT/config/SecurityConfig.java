package com.demoLogin.LoginAPIWithJWT.config;

import java.beans.Customizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.demoLogin.LoginAPIWithJWT.services.CustomUserDetailsService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	private final JwtAuthFilter jwtAuthFilter;
	private final CustomUserDetailsService uds;
	
	public SecurityConfig(JwtAuthFilter jwtAuthFilter, CustomUserDetailsService uds) {
		
		this.jwtAuthFilter = jwtAuthFilter;
		this.uds = uds;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf(csrf ->csrf.disable())
			.sessionManagement(sm ->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth ->auth
					.requestMatchers("/auth/**").permitAll()
					.requestMatchers("/actuator/health").permitAll()
					.requestMatchers("/employees/all").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
					.requestMatchers("/employee/**").hasAnyAuthority("ROLE_ADMIN")
					.anyRequest().authenticated()
					)
			.authenticationProvider(authProvider())
			.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
	
		return http.build();
			
			
	}
	
	@Bean
	public AuthenticationProvider authProvider()
	{
		DaoAuthenticationProvider p=new DaoAuthenticationProvider();
		
		p.setUserDetailsService(uds);
		p.setPasswordEncoder(passwordEncoder());
		return p;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
	
	

}
