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
	

	/*
	@GetMapping("/getalltodo/{userId}")
	public ResponseEntity<List<TodoDTO>> getAllTodo(@PathVariable int userId) {
		List<TodoDTO> todos = todoService.getAllTodos(userId);
		return ResponseEntity.ok(todos);
	} */
	
	@GetMapping("/getalltodo/{userId}")
	public ResponseEntity<List<TodoDTO>> getAllTodo(@PathVariable int userId) {
	    try {
	        List<TodoDTO> todos = todoService.getAllTodos(userId);
	        return ResponseEntity.ok(todos);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	
	/*
	@PutMapping("/updatetodo/{id}")
	public ResponseEntity<String> updateTodo(@PathVariable int id ,@RequestBody TodoDTO todoDTO) {
		TodoDTO updatedTodo = todoService.updateTodo(id, todoDTO);
		return ResponseEntity.ok("Todo Update Succesfully");
	}  */
	
	
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
	
	/*
	@PutMapping("/updatetodo/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable int id, @RequestBody Todo todo) {
        try {
            String res = todoService.updateTodo(id, todo);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    } */
	

	
	
	/*
	// Controller Layer
	@GetMapping("/todos")
	public ResponseEntity<List<Todo>> searchTodos(
	        @RequestParam(required = false) Integer id,
	        @RequestParam(required = false) String title,
	        @RequestParam(required = false) String description,
	        @RequestParam(required = false) Boolean completed
	) {
	    
	    List<Todo> todos = todoService.searchTodos( id, title, description, completed);
	    return ResponseEntity.ok(todos);
	}
	*/
	
	/*
	@GetMapping("/todos")
    public ResponseEntity<List<Todo>> searchTodos(
            @RequestParam(required = false) Integer userId, 
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean completed
    ) {
        try {
            // If userId is not provided, return bad request
            if (userId == null) {
                return ResponseEntity.badRequest().body(null);
            }

            // Call service layer method passing user ID and search parameters
            List<Todo> todos = todoService.searchTodos(userId, id, title, description, completed);
            return ResponseEntity.ok(todos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    } */
	
	/*
	@PostMapping("/todos")
    public ResponseEntity<List<Todo>> searchTodos(@RequestBody SearchCriteria searchCriteria) {
        try {
            // If userId is not provided, return bad request
            if (searchCriteria.getUserId() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            // Call service layer method passing search criteria
            List<Todo> todos = todoService.searchTodos(searchCriteria);
            return ResponseEntity.ok(todos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    } */
	
	/*
	@PostMapping("/addtodo")
	public ResponseEntity<String> addTodo(@Valid @RequestBody Todo todo , BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder("validation failed: ");
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMessage.append(error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errorMessage.toString());
		}
		
		try {
		String res = todoService.addTodo(todo);
		return ResponseEntity.ok(res);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}  */
	
	/*
	@GetMapping("/getalltodo/{userId}")
	public ResponseEntity<List<Todo>> getAllTodo(@PathVariable int userId) {
		try {
		List<Todo> todos = todoService.getAllTodo(userId);
		return ResponseEntity.ok(todos);
		} catch(Exception e) {
			return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}   */
		
/*
	
	@GetMapping("/getalltodo")
	public ResponseEntity<List<Todo>> getAllTodo(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "10") int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Todo> todoPage = todoService.getAllTodo(pageable);
		
		List<Todo> todos = todoPage.getContent();
		
		return ResponseEntity.ok(todos);
			
	}
	
	*/
		
	}