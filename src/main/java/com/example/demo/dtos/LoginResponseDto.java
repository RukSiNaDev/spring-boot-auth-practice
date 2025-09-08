package com.example.demo.dtos;

public class LoginResponseDto {
	private String token;
	private String name;
	private String email;
	private String message;
	
	public LoginResponseDto(String token, String name, String email, String message) {
		this.setToken(token);
		this.setName(name);
		this.setEmail(email);
		this.setMessage(message);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
