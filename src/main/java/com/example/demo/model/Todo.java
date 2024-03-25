package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Todo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@NotBlank(message = "Title cannot be blank")
	private String title;
	@NotBlank(message = "Description cannot be blank")
	private String description;
	@NotNull(message = "Completed status cannot be null")
	private boolean completed;
	
	
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


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isCompleted() {
		return completed;
	}
	

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	
	
	
}
