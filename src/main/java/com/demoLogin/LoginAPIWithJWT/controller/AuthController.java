package com.demoLogin.LoginAPIWithJWT.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demoLogin.LoginAPIWithJWT.dto.AuthResponse;
import com.demoLogin.LoginAPIWithJWT.dto.LoginRequest;
import com.demoLogin.LoginAPIWithJWT.dto.RegisterRequest;
import com.demoLogin.LoginAPIWithJWT.entity.UserEntity;
import com.demoLogin.LoginAPIWithJWT.jwtUtility.JwtService;
import com.demoLogin.LoginAPIWithJWT.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UserRepository userRepo;
	private final PasswordEncoder encoder;
	private final JwtService jwt;
	
	public AuthController(UserRepository userRepo, PasswordEncoder encoder, JwtService jwt) {
		
		this.userRepo = userRepo;
		this.encoder = encoder;
		this.jwt = jwt;
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req)
	{
		if(userRepo.existsByEmail(req.email))
		{
			return ResponseEntity.badRequest().body(new AuthResponse("user Already registered",null));
		}
		
		UserEntity u=new UserEntity();
		u.setEmail(req.email);
		u.setPassword(encoder.encode(req.password));
		u.setRoles(req.admin?Set.of("ROLE_ADMIN","ROLE_USER"):Set.of("ROLE_USER"));
		userRepo.save(u);
		
		return ResponseEntity.ok(new AuthResponse("user Registered successfully",null));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req)
	{
		UserEntity u=userRepo.findByEmail(req.email).orElse(null);
		
		if(u==null || !encoder.matches(req.password,u.getPassword()))
		{
			return ResponseEntity.status(401).body(new AuthResponse("Invalid Credentials",null));
		}
		
		String token=jwt.generateToken(u.getEmail(), Map.of("roles",u.getRoles()));
		
		return ResponseEntity.ok(new AuthResponse("Login Successfully", token));
	}
	
	
	
	
	
	
}
