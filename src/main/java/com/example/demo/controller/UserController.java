package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.UserAuthenticationProvider;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAuthenticationProvider userAuthenticationProvider;
	
	
	
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		
		User user1 = userService.createUser(user);
		
		if(user1 != null) {
			user1.setToken(userAuthenticationProvider.createToken(user1.getEmail()));
			return ResponseEntity.ok(user1);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	
	
}
