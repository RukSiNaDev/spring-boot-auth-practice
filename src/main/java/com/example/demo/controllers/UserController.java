package com.example.demo.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dtos.UserDto;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

    @GetMapping
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
        		.map(user -> new UserDto(user.getName(),user.getEmail()))
        		.toList();
    }
    
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable String id) {
        return userRepository.findAll().stream()
        		.filter(user -> user.getId().equals(id))
        		.map(user -> new UserDto(user.getName(),user.getEmail()))
        		.findFirst()
        		.orElse(null);
    }
    
    @PostMapping("/search")
    public UserDto getUserByEmail(@Valid @RequestBody UserDto userDto) {
    	return userRepository.findByEmail(userDto.getEmail())
        		.map(user -> new UserDto(user.getName(),user.getEmail()))
        		.orElse(null);
    }
    
    @PostMapping
    public String postMethodName(@RequestBody UserDto userDto) {
    	userRepository.save(new UserModel(userDto.getName(),userDto.getEmail()));
    	return "User created successfully";
    }
    
    @PutMapping("/{id}")
    public String putMethodName(@PathVariable String id, @RequestBody UserDto userDto) {
    	UserModel updateUser = new UserModel(userDto.getName(),userDto.getEmail());
    	userRepository.save(updateUser);
    	return "User updated successfully";
    }
    
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
    	UserModel userToDelete = userRepository.findAll().stream()
        		.filter(user -> user.getId().equals(id))
        		.findFirst()
        		.orElse(null);
    	if(userToDelete != null) {
        	userRepository.delete(userToDelete);
        	return "User deleted successfully";
    	}
    	return "User not found";
    }
    
    
}
