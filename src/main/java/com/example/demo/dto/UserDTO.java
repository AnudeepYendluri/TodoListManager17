package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO {
	
	private int id;
	
   
    private String username;
    
   
    private String password;
    
    
    private String mobileNumber;
    
  
    private String email;
}
