	package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TodoDTO;
import com.example.demo.exception.GlobalExceptionHandler.TodoNotFoundException;
import com.example.demo.mapper.TodoMapper;
import com.example.demo.model.Todo;
import com.example.demo.model.User;
import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UserRepository;

@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepo;	
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TodoMapper todoMapper;
	
	
	/*
	
	public String addTodo(int userId , TodoDTO todoDTO) {
		
		User user = userRepo.findById(userId);
		
		Todo todo = todoMapper.todoDTOtoEntity(todoDTO);
		
		todo.setUser(user);
		
		todoRepo.save(todo);
		
		return "Todo Created Succesfully";
				
	}   */
	
	public String addTodo(int userId, TodoDTO todoDTO) {
	    try {
	        User user = userRepo.findById(userId);
	        if (user == null) {
	            throw new RuntimeException("User not found with id: " + userId);
	        }

	        Todo todo = todoMapper.todoDTOtoEntity(todoDTO);
	        todo.setUser(user);
	        todoRepo.save(todo);

	        return "Todo Created Successfully";
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to add todo: " + e.getMessage());
	    }
	}

	
	
	 public String deleteTodo(int todoId) {
	        try {
	            todoRepo.deleteById(todoId);
	            return "Todo Deleted Successfully";
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to delete todo with id "  + e.getMessage());
	        }
	    }
	
	 /*
	 public List<TodoDTO> getAllTodos(int userId) {
	        User user = userRepo.findById(userId);
	        if (user == null) {
	            return List.of();
	        }

	        List<Todo> todos = todoRepo.findByUser(user);
	        return todos.stream()
	                .map(todoMapper::todoEntityToDTO)
	                .collect(Collectors.toList());
	    }  */
	 
	 public List<TodoDTO> getAllTodos(int userId) {
		    try {
		        User user = userRepo.findById(userId);
		        if (user == null) {
		            throw new RuntimeException("User not found with id: " + userId);
		        }

		        List<Todo> todos = todoRepo.findByUser(user);
		        return todos.stream()
		                .map(todoMapper::todoEntityToDTO)
		                .collect(Collectors.toList());
		    } catch (Exception e) {
		        throw new RuntimeException("Failed to get todos for user with this  id " + e.getMessage());
		    }
		}

	 
	 public TodoDTO updateTodo(int id , TodoDTO todoDTO) {
		 
		 try {
		 Optional<Todo> optionalTodo = todoRepo.findById(id);
		 
		 if(optionalTodo.isEmpty()) {
			 throw new RuntimeException("Todo not found");
		 }
		 
		 Todo todo = optionalTodo.get();
		 todo.setTitle(todoDTO.getTitle());
		 todo.setDescription(todoDTO.getDescription());
		 todo.setCompleted(todoDTO.isCompleted());
		 
		 Todo updateTodo = todoRepo.save(todo);
		 return todoMapper.todoEntityToDTO(updateTodo);
		 
		 } catch(Exception e) {
			 throw new RuntimeException("Failed to updated todo " + e.getMessage());
		 }
	 }
	 
	 
	 
	 public Optional<Todo> getTodoById(int id) {
	        Optional<Todo> todo = todoRepo.findById(id);
	        if (todo.isEmpty()) {
	            throw new TodoNotFoundException("Todo not found with ID: " + id);
	        }
	        return todo;
	    }

	 
	 
	 /*
	 public List<Todo> getAllTodo(int userId) {
	        try {
	            return todoRepo.findByUserId(userId);
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to retrieve todos: " + e.getMessage());
	        }
	    }
	    
	   */
	
	/*
	public Page<Todo> getAllTodo(Pageable pageable) {
		return todoRepo.findAll(pageable);
	} */
	
	
	 /*
	 public String updateTodo(int id, Todo todo) {
	        try {
	            todo.setId(id);
	            Todo updatedTodo = todoRepo.save(todo);
	            if (updatedTodo != null) {
	                return "Todo updated Successfully";
	            } else {
	                throw new RuntimeException("Failed to update todo with id " + id);
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("Something went wrong while updating todo " + e.getMessage());
	        }
	    } */
	
	 
	 
	 /*
	// Service Layer
	 public List<Todo> searchTodos(Integer id, String title, String description, Boolean completed) {
	     return todoRepo.findBySearchCriteria(id, title, description, completed);
	 } */
	 
	 /*
	 public List<Todo> searchTodos(Integer userId, Integer id, String title, String description, Boolean completed) {
	        try {
	            
	            List<Todo> userTodos = todoRepo.findByUserId(userId);
	            
	            if (id == null && title == null && description == null && completed == null) {
	                return userTodos;
	            }
	            
	            return todoRepo.findBySearchCriteria(id, title, description, completed);
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to search todos: " + e.getMessage());
	        }
	    }  */
	 
	 
	 /*
	 public List<Todo> searchTodos(SearchCriteria searchCriteria) {
		    try {
		        List<Todo> userTodos = todoRepo.findByUserId(searchCriteria.getUserId());
		        
		        if (searchCriteria.isEmpty()) {
		            return userTodos;
		        }
		        
		        return todoRepo.findBySearchCriteria(searchCriteria);
		    } catch (Exception e) {
		        throw new RuntimeException("Failed to search todos: " + e.getMessage());
		    }
		}
		
	*/


	 
}
