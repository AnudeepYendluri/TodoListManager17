package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.demo.model.Todo;
import com.example.demo.model.User;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Integer> {

	 List<Todo> findByUserId(int userId);
	 
	 
	 @Query("SELECT t FROM Todo t " +
		       "WHERE (:id IS NULL OR t.id = :id) " +
		       "AND (:title IS NULL OR t.title = :title) " +
		       "AND (:description IS NULL OR t.description = :description) " +
		       "AND (:completed IS NULL OR t.completed = :completed)")
		List<Todo> findBySearchCriteria(
		    @Param("id") Integer id,
		    @Param("title") String title,
		    @Param("description") String description,
		    @Param("completed") Boolean completed
		);


	List<Todo> findByUser(User user);
	 
	
}
