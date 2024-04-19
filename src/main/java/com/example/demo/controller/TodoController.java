package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TodoDTO;
import com.example.demo.exception.GlobalExceptionHandler.TodoNotFoundException;
import com.example.demo.model.Todo;
import com.example.demo.model.User;
import com.example.demo.service.TodoService;

import jakarta.validation.Valid;

@CrossOrigin("*")		
@RestController
public class TodoController {

	@Autowired
	private TodoService todoService;
	
	@PostMapping("/addtodo")
    public ResponseEntity<String> addTodo(@Valid @RequestParam("userId") int userId, @RequestBody TodoDTO todoDTO , BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder("validation failed: ");
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMessage.append(error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errorMessage.toString());
		}
			
		try {
        String result = todoService.addTodo(userId, todoDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
    }
	
	@DeleteMapping("/deletetodo/{todoId}")
	public ResponseEntity<String> deleteTodo(@PathVariable int todoId) {
		try {
		String res = todoService.deleteTodo(todoId);
		return ResponseEntity.ok(res);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	

	
	@GetMapping("/getalltodo/{userId}")
	public ResponseEntity<List<TodoDTO>> getAllTodo(@PathVariable int userId) {
	    try {
	        List<TodoDTO> todos = todoService.getAllTodos(userId);
	        return ResponseEntity.ok(todos);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}


	
	@PutMapping("/updatetodo/{id}")
	public ResponseEntity<String> updateTodo(@PathVariable int id, @RequestBody TodoDTO todoDTO) {
	    try {
	        TodoDTO updatedTodo = todoService.updateTodo(id, todoDTO);
	        return ResponseEntity.ok("Todo Updated Successfully");
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}

	
	
	@GetMapping("/gettodobyid/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(todoService.getTodoById(id).orElseThrow());
        } catch (TodoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving todo");
        }
    }
	
	@GetMapping("/filter/{userId}")
	public ResponseEntity<List<TodoDTO>> filterTodos(@PathVariable int userId,
													 @RequestParam(required = false) String priority,
	                                                 @RequestParam(required = false) Boolean completed) {
	    try {
	        List<TodoDTO> filteredTodos = todoService.filterTodos(userId ,priority, completed);
	        return ResponseEntity.ok(filteredTodos);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	@GetMapping("/sort/{userId}")
	public ResponseEntity<List<TodoDTO>> sortTodos(@PathVariable int userId) {
		try {
			List<TodoDTO> sortedTodos = todoService.sortTodos(userId);
			return ResponseEntity.ok(sortedTodos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	
	}