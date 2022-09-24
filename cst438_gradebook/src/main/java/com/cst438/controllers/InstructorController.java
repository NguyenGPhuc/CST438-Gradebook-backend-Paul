package com.cst438.controllers;

import java.io.Console;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentDTO;

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
	
	// Add a need assignment.
	@Transactional
	@PostMapping("/instructor/add")
	public void createAssignment(@RequestBody AssignmentDTO adto){
		
		Optional<Assignment> a = assignmentRepository.findById(adto.assignmentId);
		
		if(a.isEmpty()) {
			Assignment ta = new Assignment(adto);
			assignmentRepository.save(ta);
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Assignment already exists");
		}
	}
	
	// Delete an assignment using ID. Grade must be set to 0 before delete.
	@Transactional
	@PostMapping("/instructor/delete")
	public void deleteAssignment(@RequestBody AssignmentDTO deleteDTO) {
		Optional<Assignment> check = assignmentRepository.findById(deleteDTO.assignmentId);
		
		if(check.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid assignment primary key."+ deleteDTO.assignmentId);
		}
		Assignment nuu = check.get();
		if(nuu.getNeedsGrading() > 0) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This assignment has a grade in it. Assign grade to 0 before delete.");
		}else {
			assignmentRepository.delete(nuu);
		}
		
	}
	
	// Upgrade an assignment name.
	@Transactional
	@PutMapping("/instructor/update")
	public void updateAssignment (@RequestBody AssignmentDTO updateAssignment) {
		
//		Optional<Assignment> op = assignmentRepository.findById(updateAssignment.assignmentId);
		
		Assignment op = assignmentRepository.findById(updateAssignment.assignmentId).get();
		
//		System.out.println(op + "I'm in update function");
		
		if (op == null) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Invalid assignment primary key "+updateAssignment.assignmentId);
		}else {
			Assignment upDate = new Assignment(updateAssignment);
			
			assignmentRepository.save(upDate);
		}
		
	}
		
}
