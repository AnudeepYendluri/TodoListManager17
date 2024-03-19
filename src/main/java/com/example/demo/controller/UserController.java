package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.UserAuthenticationEntryPoint;
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
	
	
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		User user1 = userService.registerUser(user);
		if(user1 != null) {
			user1.setToken(userAuthenticationProvider.createToken(user1.getEmail()));
			return ResponseEntity.ok(user1);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user) {
	    User authenticatedUser = userService.loginUser(user);
	    if (authenticatedUser != null) {
	        // Generate a new token with a new expiration time
	        String authToken = userAuthenticationProvider.createToken(authenticatedUser.getEmail());

	        // Use a Map to encapsulate the response data
	        Map<String, Object> responseMap = new HashMap<>();
	        responseMap.put("user", authenticatedUser);
	        responseMap.put("authToken", authToken);
	        responseMap.put("userId", authenticatedUser.getId());

	        return ResponseEntity.ok(responseMap);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	    }
	}
	 
	
}
