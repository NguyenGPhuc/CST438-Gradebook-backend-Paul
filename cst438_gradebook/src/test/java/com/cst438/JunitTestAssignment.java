package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import com.cst438.controllers.GradeBookController;
import com.cst438.controllers.InstructorController;
import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.AssignmentDTO;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.GradebookDTO;
import com.cst438.services.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@ContextConfiguration(classes = { GradeBookController.class, InstructorController.class })
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest
public class JunitTestAssignment {

	
	static final String URL = "http://localhost:8080";
	public static final int TEST_COURSE_ID = 40442;
	public static final String TEST_STUDENT_EMAIL = "test@csumb.edu";
	public static final String TEST_STUDENT_NAME = "test";
	public static final String TEST_INSTRUCTOR_EMAIL = "dwisneski@csumb.edu";
	public static final int TEST_YEAR = 2021;
	public static final int TEST_DATE = 30;
	public static final String TEST_SEMESTER = "Fall";
	
	
	public static final int TEST_ASSIGNMENT_ID = 42069;
	public static final String TEST_ASSIGNMENT_NAME = "JUnit Test Assignment";

	@MockBean
	AssignmentRepository assignmentRepository;

	@MockBean
	AssignmentGradeRepository assignmentGradeRepository;

	@MockBean
	CourseRepository courseRepository; // must have this to keep Spring test happy

	@MockBean
	RegistrationService registrationService; // must have this to keep Spring test happy

	@Autowired
	private MockMvc mvc;
	
	// Test case for adding new assignment.
	@Test
	public void addAssignment() throws Exception {
		
		MockHttpServletResponse response;
		
		// Make mock assignment.
		Assignment assignment = new Assignment();
		assignment.setId(TEST_ASSIGNMENT_ID);
		assignment.setName(TEST_ASSIGNMENT_NAME);
		// set dueDate to 1 week before now.
		assignment.setDueDate(new java.sql.Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000));
		assignment.setNeedsGrading(0);
		
		// Get assignment in repository
		given(assignmentRepository.findById(TEST_ASSIGNMENT_ID)).willReturn(null);
		given(assignmentRepository.save(any(Assignment.class))).willReturn(assignment);
		
		// Test add request
		response = mvc.perform(MockMvcRequestBuilders.post("/instructor/add").accept(MediaType.APPLICATION_JSON)
				.content(asJsonString(assignment)).contentType(MediaType.APPLICATION_JSON))
		.andReturn().getResponse();
		
		// Test status
		assertEquals(200, response.getStatus());
		
	}
	
	
	// Test updating existing assignment.
	@Test
	public void updateAssignemnt()throws Exception{
		
		MockHttpServletResponse response;
		
		// Mock course
		Course course = new Course();
		course.setCourse_id(TEST_COURSE_ID);
		course.setSemester(TEST_SEMESTER);
		course.setYear(TEST_YEAR);
		course.setInstructor(TEST_INSTRUCTOR_EMAIL);
		course.setEnrollments(new java.util.ArrayList<Enrollment>());
		course.setAssignments(new java.util.ArrayList<Assignment>());
		
		// Mock assignment
		Assignment assignment = new Assignment();
		assignment.setCourse(course);
		course.getAssignments().add(assignment);
		
	
		// DTO for updating
		AssignmentDTO adto = new AssignmentDTO();
		adto.getCourseId();
		adto.setAssignmentID(TEST_ASSIGNMENT_ID);
		adto.setassignmentName("Some new name");
		// set dueDate to 1 week before now.
		adto.setDueDate(new java.sql.Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000));
		adto.setNeedsGrading(0);
		
		// Set mock repository
		given(assignmentRepository.findById(TEST_ASSIGNMENT_ID)).willReturn(Optional.of(assignment));
		given(assignmentRepository.save(any(Assignment.class))).willReturn(assignment);
		
		
		System.out.println(asJsonString(adto));
		
		// Test update request
		response = mvc.perform(MockMvcRequestBuilders.put("/instructor/update").accept(MediaType.APPLICATION_JSON)
				.content(asJsonString(adto)).contentType(MediaType.APPLICATION_JSON))
		.andReturn().getResponse();
		
		
		assertEquals(200, response.getStatus());
	}
	
	
	
	@Test
	public void deleteAssignment() throws Exception {
		
		MockHttpServletResponse response;
	
		// mock database data
		
		// Mock course
		Course course = new Course();
		course.setCourse_id(TEST_COURSE_ID);
		course.setSemester(TEST_SEMESTER);
		course.setYear(TEST_YEAR);
		course.setInstructor(TEST_INSTRUCTOR_EMAIL);
		course.setEnrollments(new java.util.ArrayList<Enrollment>());
		course.setAssignments(new java.util.ArrayList<Assignment>());
		
		// Mock assignment
		Assignment assignment = new Assignment();
		assignment.setCourse(course);
		course.getAssignments().add(assignment);
		
		// Mock DTO
		AssignmentDTO adto = new AssignmentDTO();
		adto.getCourseId();
		adto.setAssignmentID(TEST_ASSIGNMENT_ID);
		adto.setassignmentName("Some new name");
		// set dueDate to 1 week before now.
		adto.setDueDate(new java.sql.Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000));
		adto.setNeedsGrading(0);
		
		// Set mock repository
		given(assignmentRepository.findById(TEST_ASSIGNMENT_ID)).willReturn(Optional.of(assignment));
		given(assignmentRepository.save(any(Assignment.class))).willReturn(assignment);
		
		// Test delete request
		response = mvc.perform(MockMvcRequestBuilders.post("/instructor/delete").accept(MediaType.APPLICATION_JSON)
				.content(asJsonString(adto)).contentType(MediaType.APPLICATION_JSON))
		.andReturn().getResponse();
		
		
		assertEquals(200, response.getStatus());
		
	}

	private static String asJsonString(final Object obj) {
		try {

			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T fromJsonString(String str, Class<T> valueType) {
		try {
			return new ObjectMapper().readValue(str, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
