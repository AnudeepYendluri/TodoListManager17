package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {	

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public User registerUser(User user) {
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return userRepo.save(user);
		} catch(Exception e) {
			throw new RuntimeException("failed to register user " + e.getMessage());
		}
	}


	  public User loginUser(User user) {
        try {
            User storedUser = userRepo.findByEmail(user.getEmail());
            if (storedUser != null && passwordEncoder.matches(user.getPassword(), storedUser.getPassword())) {
                return storedUser;
            } else {
                return null; // Invalid credentials
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to login user: " + e.getMessage());
        }
    }
	 
	
	
	  public List<User> getAllUsers() {
        try {
            return userRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all users: " + e.getMessage());
        }
    }
	  

    public User getUserByEmail(String email) {
        try {
            return userRepo.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user by email: " + e.getMessage());
        }
    }
	 
	
	
	
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	
	
}