package com.example.demo.mapper;

import org.mapstruct.Mapper;

import com.example.demo.dto.TodoDTO;
import com.example.demo.model.Todo;

@Mapper(componentModel = "spring")
public interface TodoMapper {

	Todo todoDTOtoEntity(TodoDTO todoDTO);
	
	TodoDTO todoEntityToDTO(Todo todo);
}
