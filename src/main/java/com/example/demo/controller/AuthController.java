package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.UserAuthenticationProvider;

@RestController
public class AuthController {

	 @Autowired
	    private UserAuthenticationProvider userAuthenticationProvider;

	    @GetMapping("/userid")
	    public ResponseEntity<String> extractUserIdFromToken(@RequestHeader("Authorization") String authTokenHeader) {
	        try {
	            String authToken = authTokenHeader.substring(7); // Remove "Bearer " prefix
	            String userId = userAuthenticationProvider.getUserIdFromToken(authToken);
	            if (userId == null) {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
	            }
	            return ResponseEntity.ok(userId);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
	        }
	    }
}
