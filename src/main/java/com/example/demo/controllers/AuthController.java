package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dtos.LoginRequestDto;
import com.example.demo.dtos.RegisterRequestDto;
import com.example.demo.dtos.UserResponseDto;
import com.example.demo.dtos.LoginResponseDto;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
public class AuthController {

	
		private final UserRepository userRepository;
		private final PasswordEncoder passwordEncoder;
		private final JwtUtil jwtUtil;

		AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
			this.userRepository = userRepository;
			this.passwordEncoder = passwordEncoder;
			this.jwtUtil = jwtUtil;
		}

	  @PostMapping("/register")
	    public String register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {

			 UserModel user = new UserModel(registerRequestDto.getName(),registerRequestDto.getEmail(),
					  passwordEncoder.encode(registerRequestDto.getPassword()));
			  userRepository.save(user);

		  return "Register successful";
	    }
	  
	  @PostMapping("/login")
	    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
		  UserModel user = userRepository.findByEmail(loginRequestDto.getEmail())
				  .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password"));
		  if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
			  throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
		  }
		  
		  String token = jwtUtil.generateToken(user.getEmail());
		  
		  return new LoginResponseDto(token ,user.getEmail(),user.getName(), "Login successful");
	    }
	  

	    @GetMapping("/profile")
	    public UserResponseDto profile(HttpServletRequest request) {
	    	String email = (String) request.getAttribute("email");
	    	UserModel user = userRepository.findByEmail(email)
	    			.orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email"));
	        return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
	    }
	    

}
