package com.example.demo.service;

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
	
	
	
	public User createUser(User user) {
		
		user.setPassword(passwordEncoder.encode(user.getEmail()));
		
		User user1 = userRepo.save(user);
		
		if(user1 != null) {
			return user1;
		}
		return null;
	}
	
	
	public User loginUser(User user) {
		
		User user1 = userRepo.findByEmail(user.getEmail());
		
		if(passwordEncoder.matches(user.getPassword(), user1.getPassword())) {
			return user1;
		}
		return null;
	}
	
	
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	
}
