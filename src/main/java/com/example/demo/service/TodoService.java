package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;

@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepo;
	
	
	public String addTodo(Todo todo) {
		
		Todo todo1 = todoRepo.save(todo);
		
		if(todo1 != null) {
			return "created Succesfully";
		}
		return "Something went wrong";
	}
	
	
	public String deleteTodo(int id) {
		
		todoRepo.deleteById(id);
		
		return "Deleted Succesfully";
	}
	
	
	/*
	public List<Todo> getAllTodo() {		
		
		List<Todo> t =	todoRepo.findAll();
	
		return t;
	}  */
	
	
	
	public Page<Todo> getAllTodo(Pageable pageable) {
		return todoRepo.findAll(pageable);
	} 
	
	
	public Optional<Todo> getTodoById(int id) {
		
		Optional<Todo> todo = todoRepo.findById(id);
		
		return todo;
	}
	
	public String updateTodo(int id,Todo todo) {
		
		todo.setId(id);
		
		Todo todo1 = todoRepo.save(todo);
		
		if(todo1 != null) {
			return "Todo updated Succesfully";
		}
		return "Something went wrong";
	}
		
	
}
