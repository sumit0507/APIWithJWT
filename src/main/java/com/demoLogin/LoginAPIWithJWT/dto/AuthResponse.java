package com.demoLogin.LoginAPIWithJWT.dto;

public class AuthResponse {
	
	public String message;
	public String token;
	public AuthResponse(String message,String token)
	{
		this.message=message;
		this.token=token;
		
	}

}
