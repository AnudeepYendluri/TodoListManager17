package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;

@CrossOrigin("*")
@RestController
public class TodoController {

	@Autowired
	private TodoService todoService;
	
	@PostMapping("/addtodo")
	public String addTodo(@RequestBody Todo todo) {
		
		String res = todoService.addTodo(todo);
		
		return res;
	}
	
	@DeleteMapping("/deletetodo/{id}")
	public String deleteTodo(@PathVariable int id) {
		
		String res = todoService.deleteTodo(id);
		
		return res;
	}
	
	@GetMapping("/getalltodo")
	public List<Todo> getAllTodo() {
		
		List<Todo> res = todoService.getAllTodo();
		
		return res;
	}
	
	@GetMapping("/gettodobyid/{id}")
	public Optional<Todo> getTodoById(@PathVariable int id) {
		
		Optional<Todo> todo = todoService.getTodoById(id);
		
		return todo;
	}
	
	@PutMapping("/updatetodo/{id}")
	public String updateTodo(@RequestBody Todo todo,@PathVariable int id) {	
		String res = todoService.updateTodo(id, todo);
		return res;
		
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to bitlabs";
	}

	
	
}
