package com.cst438.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentListDTO;
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
	
	@Transactional
	@PostMapping("/add")
	public void createAssignment(@RequestBody AssignmentListDTO.AssignmentDTO adto){
		
		Optional<Assignment> a = assignmentRepository.findById(adto.getAssignmentID());
		
		if(a.isEmpty()) {
			Course c = courseRepository.findById(adto.courseId).orElse(null);
			if(c == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No courses matching entered ID");
			}else {
				Assignment ta = new Assignment(adto);
				ta.setCourse(c);
				ta.setNeedsGrading(1);
				assignmentRepository.save(ta);
			}
			
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Assignment already exists");
		}
	}
	
	@Transactional
	@DeleteMapping("/delete/{id}")
	public void updateAssignment(@PathVariable("id") Integer assignmentId) {
		Optional<Assignment> check = assignmentRepository.findById(assignmentId);
		
		if(check.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid assignment primary key."+ assignmentId);
		}
		Assignment nuu = check.get();
		if(nuu.getNeedsGrading() > 0) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Assignment still needs grading.");
		}else {
			assignmentRepository.delete(nuu);
		}
		
	}
	
	@Transactional
	@PutMapping("/update")
	public void updateAssignment (@RequestBody AssignmentListDTO.AssignmentDTO assignment) {
		
		Optional<Assignment> op = assignmentRepository.findById(assignment.assignmentId);
		
		if (!op.isPresent()) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Invalid assignment primary key. "+assignment.assignmentId);
		}else {
			Assignment nuu = new Assignment(assignment);
			assignmentRepository.save(nuu);
		}
		
	}
		
}