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
	
	@Transactional
	@PostMapping("/instructor/add/{id}")
	public AssignmentDTO createAssignment(@PathVariable("id")int id, @RequestBody String assignmentName, Date dueDate){
		
		Optional<Assignment> a = assignmentRepository.findById(id);
		
		if(a.isEmpty()) {
			AssignmentDTO at = new AssignmentDTO();
			Assignment ta = new Assignment();
			ta.setId( id);
			ta.setName(assignmentName);
			ta.setDueDate(dueDate);
			at.assignmentId = ta.getId();
			at.assignmentName = ta.getName();
			at.dueDate = ta.getDueDate();
			assignmentRepository.save(ta);
			return at;
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Assignment already exists");
		}
	}
	
	@Transactional
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
	
	@Transactional
	@PutMapping("/instructor/update")
	public AssignmentDTO updateAssignment (@RequestBody AssignmentDTO assignment, String name) {
		Optional<Assignment> op = assignmentRepository.findById(assignment.assignmentId);
		
		if (!op.isPresent()) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Invalid assignment primary key. "+assignment.assignmentId);
		}else {
			Assignment a = op.get();
			AssignmentDTO ndto = new AssignmentDTO();
			a.setName(name);
			ndto.assignmentId = a.getId();
			ndto.courseId = a.getCourse(); 
			ndto.assignmentName = a.getName();
			ndto.dueDate = a.getDueDate();
			assignmentRepository.save(a);
			return ndto;
		}
		
	}
		
}
