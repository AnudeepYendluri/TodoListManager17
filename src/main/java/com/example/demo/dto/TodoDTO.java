package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TodoDTO {
    
    private int  id; 
    
    @NotBlank(message = "Title cannot be blank")
    private String title;
    
    @NotBlank(message = "Description cannot be blank")
    private String description;
    
    @NotNull(message = "Completed status cannot be null")
    private boolean completed;
    
    private Long user_id; // User id associated with this todo
}