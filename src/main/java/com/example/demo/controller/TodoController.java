package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;

@CrossOrigin("*")		
@RestController
public class TodoController {

	@Autowired
	private TodoService todoService;
	
	@PostMapping("/addtodo")
	public ResponseEntity<String> addTodo(@RequestBody Todo todo) {
		
		String res = todoService.addTodo(todo);
		
		return ResponseEntity.ok(res);
		
	}
	
	@DeleteMapping("/deletetodo/{id}")
	public ResponseEntity<String> deleteTodo(@PathVariable int id) {
		
		String res = todoService.deleteTodo(id);
		
		return ResponseEntity.ok(res);
	}
	
	
	/*@GetMapping("/getalltodo")
	public ResponseEntity<List<Todo>> getAllTodo() {
		
		List<Todo> todos = todoService.getAllTodo();
		
		return ResponseEntity.ok(todos);
	}  */
	
	
	@GetMapping("/getalltodo")
	public ResponseEntity<List<Todo>> getAllTodo(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "10") int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Todo> todoPage = todoService.getAllTodo(pageable);
		
		List<Todo> todos = todoPage.getContent();
		
		return ResponseEntity.ok(todos);
		
	}  
	
	
	
	
	@GetMapping("/gettodobyid/{id}")
	public ResponseEntity<?> getTodoById(@PathVariable int id) {
		
		Optional<Todo> todo = todoService.getTodoById(id);
		
		if(todo.isPresent()) {
			return ResponseEntity.ok(todo.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found");
		}
	}
	
	@PutMapping("/updatetodo/{id}")
	public ResponseEntity<String> updateTodo(@RequestBody Todo todo,@PathVariable int id) {	
		try {
		String res = todoService.updateTodo(id, todo);
		return ResponseEntity.ok(res);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating todo");	
		}
		
	}
		
		@GetMapping("/welcome")
		public ResponseEntity<String> welcome() {
			return ResponseEntity.ok("Welcome to bitlabs");
		}
		
		
	}