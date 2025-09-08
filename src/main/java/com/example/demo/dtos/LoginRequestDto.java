package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDto {
	
	@NotBlank(message = "Email is required")
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;

	public LoginRequestDto(String email, String password) {
		this.email = email;
		this.password = password;
    }    
	
    public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    
	public String getEmail() {
		return email;
	}
    
	public String getPassword() {
		return password;
	}
}
