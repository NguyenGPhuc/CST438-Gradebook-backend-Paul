package com.cst438.controllers;

import java.util.ArrayList;
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
import com.cst438.domain.AssignmentListDTO;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseDTOG;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.GradebookDTO;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;
import com.cst438.services.RegistrationService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class AssignmentController {
	
	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	AssignmentGradeRepository assignmentGradeRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	// Add a new assignment to a course using ID
	@PostMapping("/assignment")
	@Transactional
	public AssignmentListDTO addCourse( @RequestBody AssignmentListDTO assignmentDTO ) { 

		String email = "dwisneski@csumb.edu";  // user name (should be instructor's email) 
		
//		Assignment assignment = assignmentRepository.findByAssignmentName(assignmentDTO.assignments);

		
		if (assignmentDTO!= null) {
			// TODO check that today's date is not past add deadline for the course.
			Assignment newAssignment = new Assignment();
//			newAssignment.setName(assignmentDTO.getName());
//			newAssignment.setDueDate(assignmentDTO.getDueDate());
	
			Assignment savedAssignment = assignmentRepository.save(newAssignment);
			
			
//			AssignmentListDTO result = createCourseDTO(savedEnrollment);
			
			return assignmentDTO;
		} else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "The name for this assignment have already existed  "+assignmentDTO.assignments);
		}
		
	}
}
