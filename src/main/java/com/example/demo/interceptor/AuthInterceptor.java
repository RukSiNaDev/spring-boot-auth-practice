package com.example.demo.interceptor;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.utils.JwtUtil;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	
	private final JwtUtil jwtUtil;
	
	private AuthInterceptor(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public boolean preHandle(@NonNull HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		String authHeader = request.getHeader("Authorization");
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized header");
		}
		String token = authHeader.substring(7);
		if(!jwtUtil.isTokenValid(token)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized token invalid");
		}
		
		if(jwtUtil.isTokenExpired(token)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized token expired");
		}

		String email = jwtUtil.getEmailFromToken(token);
		if(email == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized email");
		}
		request.setAttribute("email", email);
		return true;
	}
}
