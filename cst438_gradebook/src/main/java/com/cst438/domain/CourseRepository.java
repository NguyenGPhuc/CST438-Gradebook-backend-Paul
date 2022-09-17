package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends CrudRepository <Course, Integer> {
	
	//@Query("select distinct a from Assignment where a.course.instructor = :email")
	//List<Course> findByEmail(@Param("email") String email);
}
