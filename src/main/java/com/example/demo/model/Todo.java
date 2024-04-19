package com.example.demo.model;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
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
	
	@Column(nullable = false)
	private String priority;
	
	@Column(nullable = false)
	private Date dueDate;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id")
    @JsonIgnoreProperties("todos")
	private User user;
	
	public Todo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Todo(int id, @NotBlank(message = "Title cannot be blank") String title,
			@NotBlank(message = "Description cannot be blank") String description,
			@NotNull(message = "completed status cannot be null") boolean completed, String priority, Date dueDate,
			User user) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.completed = completed;
		this.priority = priority;
		this.dueDate = dueDate;
		this.user = user;
	}


}
