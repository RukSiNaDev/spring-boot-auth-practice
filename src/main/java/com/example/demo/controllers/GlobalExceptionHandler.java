package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(error-> {
			String fieldName = error.getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		
		return ResponseEntity.badRequest().body(errors);
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex){
		Map<String, String> errors = new HashMap<>();
		errors.put("message", ex.getReason());
		return ResponseEntity.badRequest().body(errors);
	}
	
	@ExceptionHandler(JpaSystemException.class)
	public ResponseEntity<Map<String, String>> handleJpaSystemException(JpaSystemException ex){
		Map<String, String> errors = new HashMap<>();
		errors.put("message", "Email already exists");
		return ResponseEntity.badRequest().body(errors);
	}
}
