package com.cst438.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.GradebookDTO;


@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class InstructorController{

	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@Transactional
	@PostMapping("/instructor/add")
	public void createAssignment(Integer id, String name, Date dueDate) throws Exception{
		Assignment assignment = assignmentRepository.findById(id).orElse(null);
		if(assignment == null) {
			assignment = new Assignment();
			assignment.setName(name);
			assignment.setDueDate(dueDate);
			assignmentRepository.save(assignment);
			throw new ResponseStatusException(HttpStatus.CREATED, "Assignment Created.");
		}else {
			throw new Exception("Assignment already exists");
		}
	}
	
	@PostMapping("/instructor/delete{id}")
	public void updateAssignment(@PathVariable("id") Integer assignmentId) {
		Assignment a = assignmentRepository.findById(assignmentId).orElse(null);
		if(a == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid assignment primary key."+ assignmentId);
		}else if(a.getNeedsGrading() > 0 ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Assignment needs grading before deletion.");
		}else {
			assignmentRepository.delete(a);
		}	
	}
	
	@PutMapping("/instructor/update{id}")
	@Transactional
	public void updateAssignment (@PathVariable("id") Integer assignmentId, String name ) {
		Assignment a = assignmentRepository.findById(assignmentId).orElse(null);
		if (a == null) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Invalid assignment primary key. "+assignmentId);
		}else {
			a.setName(name);
			System.out.printf("%s\n", a.toString());
			assignmentRepository.save(a);
		}
		
	}
		
}
