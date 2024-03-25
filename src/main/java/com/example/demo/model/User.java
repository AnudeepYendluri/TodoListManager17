package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity

@Table(name="app_user")
@Data
public class User {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank(message = "Username cannot be blank")
	private String username;
	@NotBlank(message = "Password cannot be blank")
	private String password;
	@NotBlank(message = "Mobile number cannot be blank")
	@Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
	private String mobileNumber;
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Invalid email format")
	private String email;
	private String token;
	
	
	@OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
	@JsonIgnoreProperties("user")
	private List<Todo> todos;
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	public User(int id, String username, String password, String mobileNumber, String email, String token) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.token = token;
	}


}
