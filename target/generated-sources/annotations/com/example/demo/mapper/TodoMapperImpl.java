package com.example.demo.mapper;

import com.example.demo.dto.TodoDTO;
import com.example.demo.model.Todo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-03T17:37:21+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class TodoMapperImpl implements TodoMapper {

    @Override
    public Todo todoDTOtoEntity(TodoDTO todoDTO) {
        if ( todoDTO == null ) {
            return null;
        }

        Todo todo = new Todo();

        todo.setId( todoDTO.getId() );
        todo.setTitle( todoDTO.getTitle() );
        todo.setDescription( todoDTO.getDescription() );
        todo.setCompleted( todoDTO.isCompleted() );
        todo.setPriority( todoDTO.getPriority() );
        todo.setDueDate(todoDTO.getDueDate());

        return todo;
    }

    @Override
    public TodoDTO todoEntityToDTO(Todo todo) {
        if ( todo == null ) {
            return null;
        }

        TodoDTO todoDTO = new TodoDTO();

        todoDTO.setId( todo.getId() );
        todoDTO.setTitle( todo.getTitle() );
        todoDTO.setDescription( todo.getDescription() );
        todoDTO.setCompleted( todo.isCompleted() );
        todoDTO.setPriority( todo.getPriority() );
        todoDTO.setDueDate(todo.getDueDate());
        

        return todoDTO;
    }
}
