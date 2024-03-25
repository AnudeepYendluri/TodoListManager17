package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.UserAuthenticationEntryPoint;
import com.example.demo.config.UserAuthenticationProvider;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAuthenticationProvider userAuthenticationProvider;
	
	@PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user ,BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder("Validation error : ");
			
			for(FieldError error : bindingResult.getFieldErrors() ) {
				errorMessage.append(error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errorMessage.toString());
		}
		
		
		
        try {
            User registeredUser = userService.registerUser(user);
            if (registeredUser != null) {
                String authToken = userAuthenticationProvider.createToken(registeredUser.getEmail());
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("user", registeredUser);
                responseMap.put("authToken", authToken);
                responseMap.put("userId", registeredUser.getId());
                return ResponseEntity.ok(responseMap);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            User authenticatedUser = userService.loginUser(user);
            if (authenticatedUser != null) {
                String authToken = userAuthenticationProvider.createToken(authenticatedUser.getEmail());
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("user", authenticatedUser);
                responseMap.put("authToken", authToken);
                responseMap.put("userId", authenticatedUser.getId());
                return ResponseEntity.ok(responseMap);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error logging in: " + e.getMessage());
        }
    }
		 
}
