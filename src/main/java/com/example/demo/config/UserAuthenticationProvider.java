package com.example.demo.config;

import java.util.Base64;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.service.UserService;

import jakarta.annotation.PostConstruct;


@Component
public class UserAuthenticationProvider {

	@Value("${security.jwt.token.secret-key:secret-value}")
	private String secretKey;
	
	@Autowired
	private UserService userService;
	
	
	public UserAuthenticationProvider(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@PostConstruct
	protected void init() {
		secretKey=Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	/*
	public String createToken(String email) {
		
	    Date now = new Date();
	    Date validity = new Date(now.getTime() + 3600000); // 1 hour expiration
	    Algorithm algorithm = Algorithm.HMAC256(secretKey);
	    
	    return JWT.create()
	       .withIssuer(email)
	       .withIssuedAt(now)
	       .withExpiresAt(validity)	
	       .withClaim("userId", user.getUserId()) 
	       .sign(algorithm);
	}  */
	
	public String createToken(String email) {
	    try {
	        // Retrieve the user object based on the email
	        User user = userService.findByEmail(email);
	        
	        // Null check for the user object
	        if (user != null) {
	            // Extract the user ID from the user object
	            String userId = String.valueOf(user.getId());
	            	
	            // Generate the JWT token
	            Algorithm algorithm = Algorithm.HMAC256(secretKey);
	            Date now = new Date();
	            Date validity = new Date(now.getTime() + 3600000); // 1 hour expiration
	            return JWT.create()
	               .withIssuer(email)
	               .withIssuedAt(now)
	               .withExpiresAt(validity)
	               .withClaim("userId", userId) // Include user ID claim
	               .sign(algorithm);
	        } else {
	            // Handle the case where user is null
	            throw new RuntimeException("User not found for email: " + email);
	        }
	    } catch (Exception e) {
	        // Handle any exceptions
	        throw new RuntimeException("Error creating token: " + e.getMessage());
	    }
	}

 
	
	public Authentication validateToken (String token) {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
		DecodedJWT decoded = verifier.verify(token);
		User user = userService.findByEmail(decoded.getSubject());
		
		return new UsernamePasswordAuthenticationToken(user,null,Collections.EMPTY_LIST);


	}
	
	/*
	public String extractUserIdFromToken(String token) {
        try {
            // Verify the token and decode its payload
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            
            // Extract userId from the decoded token payload
            String userId = decodedJWT.getSubject();
            return userId;
        } catch (Exception e) {
            return null;
        }
    } */
	
	/*
	public String getUserIdFromToken(String token) {
		  try {
		    // Verify the token and decode its payload (same logic as validateToken)
		    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
		    DecodedJWT decodedJWT = verifier.verify(token);

		    // Extract userId from the decoded token payload
		    String userId = decodedJWT.getSubject();
		    return userId;
		  } catch (Exception e) {
		    return null;
		  }
		} 
		
		*/
	
	public String getUserIdFromToken(String token) {
		  try {
		    System.out.println("Received token: " + token);  // Print received token

		    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
		    DecodedJWT decodedJWT = verifier.verify(token);

		    System.out.println("Decoded JWT payload: " + decodedJWT);  // Print decoded payload

		    String userId = decodedJWT.getClaim("userId").asString();
		    System.out.println("Extracted user ID: " + userId);  // Print extracted user ID
		    return userId;
		  } catch (Exception e) {
		    System.out.println("Error during token verification: " + e.getMessage());  // Print error message
		    return null;
		  }
		}

	
	
	
}