	package com.example.demo.service;

import java.util.Date;
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
	

	
	public String addTodo(int userId, TodoDTO todoDTO) {
	    try {
	        User user = userRepo.findById(userId);
	        if (user == null) {
	            throw new RuntimeException("User not found with id: " + userId);
	        }

	        Todo todo = todoMapper.todoDTOtoEntity(todoDTO);
	        todo.setUser(user);
	        todo.setDueDate(todoDTO.getDueDate());
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
	 
	 
	 public List<TodoDTO> filterTodos(int userId, String priority, Boolean completed) {
		    try {
		        User user = userRepo.findById(userId);
		        if (user == null) {
		            throw new RuntimeException("User not found with this id: " + userId);
		        }

		        List<Todo> filteredTodos;
		        if (priority != null && completed != null) {
		            filteredTodos = todoRepo.findByUserAndPriorityAndCompleted(user, priority, completed);
		        } else if (priority != null) {
		            filteredTodos = todoRepo.findByUserAndPriority(user, priority);
		        } else if (completed != null) {
		            filteredTodos = todoRepo.findByUserAndCompleted(user, completed);
		        } else {
		            filteredTodos = todoRepo.findByUser(user);
		        }

		        return filteredTodos.stream()
		                .map(todoMapper::todoEntityToDTO)
		                .collect(Collectors.toList());
		    } catch (Exception e) {
		        throw new RuntimeException("Failed to filter todos: " + e.getMessage());
		    }
		}
	 
	 //sorting
	 
	 public List<TodoDTO> sortTodos(int userId ) {
		
		 try {
		 
		 List<Todo> sortedTodos = todoRepo.findByUserIdOrderByDueDateAsc(userId);
		 
         List<TodoDTO> sortedTodoDTOs = sortedTodos.stream()
                 .map(todoMapper::todoEntityToDTO)
                 .collect(Collectors.toList());
         
         return sortedTodoDTOs;
         
		 } catch (Exception e) {
			 throw new RuntimeException("Failed to sort todos : " + e.getMessage());
		 }
		 
	 }

	
}
