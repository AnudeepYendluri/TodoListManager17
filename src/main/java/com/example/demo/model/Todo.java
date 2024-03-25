package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Todo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@NotBlank(message = "Title cannot be blank")
	private String title;
	@NotBlank(message = "Description cannot be blank")
	private String description;
	@NotNull(message = "completed status cannot be null")	
	private boolean completed;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id")
    @JsonIgnoreProperties("todos")
	private User user;
	
	public Todo() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Todo(int id, String title, String description, boolean completed) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.completed = completed;
	}


	
}
