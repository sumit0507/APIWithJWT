package com.demoLogin.LoginAPIWithJWT.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demoLogin.LoginAPIWithJWT.jwtUtility.JwtService;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

	private final JwtService jwtserivce;
	private final UserDetailsService uds;
	
	public JwtAuthFilter(JwtService jwtserivce, UserDetailsService uds) {
		
		this.jwtserivce = jwtserivce;
		this.uds = uds;
	}
	

	@Override
	protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res, FilterChain chain) throws  ServletException, IOException
	{
		String auth=req.getHeader("Authorization");
		
		if(auth!=null && auth.startsWith("Bearer"))
		{
			String token=auth.substring(7);
			String email=null;
			
			try {
				email=jwtserivce.extractSubject(token);
			}
			catch(Exception ignored)
			{
				
			}
			
			if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				UserDetails userdetails=uds.loadUserByUsername(email);
				
				if(jwtserivce.isTokenValid(token,email))
				{
					UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			
		}
		chain.doFilter(req, res);
	}
	
	
	
}
