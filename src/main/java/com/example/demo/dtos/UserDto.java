package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;

public class UserDto {

		private String name;
		
		@NotBlank(message = "Email is required")
		private String email;
	    
	    public UserDto(String name, String email) {
			this.name = name;
			this.email = email;
	    }
	    
		public String getName() {
			return name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	

}
