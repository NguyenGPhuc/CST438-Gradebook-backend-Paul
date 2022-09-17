package com.cst438.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentRepository;


@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class InstructorController{

	@Autowired
	AssignmentRepository assignmentRepository;
	
	@GetMapping("/instructor/{assignmentId}")
	public Assignment findAssignmentById(@PathVariable int assignmentId){
		
		String email = "dwisneski@csumb.edu";
		Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
		
		return assignment;
	}
	
	@PostMapping("/instructor/add")
	public void createAssignment(Integer id, String name, Date dueDate) throws Exception{
		String email = "dwisneski@csumb.edu";
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
		
}
