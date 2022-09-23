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
	
	@Test
	public void addAssignment() throws Exception {
		
		MockHttpServletResponse response;
		
		Assignment assignment = new Assignment();
		// set dueDate to 1 week before now.
		assignment.setId(TEST_ASSIGNMENT_ID);
		assignment.setName(TEST_ASSIGNMENT_NAME);
		assignment.setDueDate(new java.sql.Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000));
		assignment.setNeedsGrading(0);
		
		given(assignmentRepository.findById(TEST_ASSIGNMENT_ID)).willReturn(null);
		given(assignmentRepository.save(any(Assignment.class))).willReturn(assignment);
		
		
		// set dueDate to 1 week before now.
		AssignmentDTO adto = new AssignmentDTO();
		adto.setAssignmentID(TEST_ASSIGNMENT_ID + 1);
		adto.setassignmentName(TEST_ASSIGNMENT_NAME);
		adto.setDueDate(new java.sql.Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000));
		adto.setNeedsGrading(1);
		
		System.out.println(asJsonString(adto));
		
		response = mvc.perform(MockMvcRequestBuilders.post("/instructor/add").accept(MediaType.APPLICATION_JSON)
				.content(asJsonString(adto)).contentType(MediaType.APPLICATION_JSON))
		.andReturn().getResponse();
		
//		verify(assignmentRepository, times(1)).save(any());
		assertEquals(200, response.getStatus());
		
	
//		AssignmentDTO result = fromJsonString(response.getContentAsString(), AssignmentDTO.class);
//		assertNotEquals(0, result.assignmentId);
		
		
//		assertEquals(200, response.getStatus());
		
	}
	
	@Test
	public void updateAssignemnt()throws Exception{
		
		MockHttpServletResponse response;
		
		Assignment assignment = new Assignment();
		// set dueDate to 1 week before now.
		assignment.setId(TEST_ASSIGNMENT_ID);
		assignment.setName(TEST_ASSIGNMENT_NAME);
		assignment.setDueDate(new java.sql.Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000));
		assignment.setNeedsGrading(1);
		
		given(assignmentRepository.findById(TEST_ASSIGNMENT_ID)).willReturn(null);
		given(assignmentRepository.save(any(Assignment.class))).willReturn(assignment);
		
		AssignmentDTO adto = new AssignmentDTO();
		adto.setAssignmentID(TEST_ASSIGNMENT_ID);
		adto.setassignmentName("Some new name");
		adto.setDueDate(new java.sql.Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000));
		adto.setNeedsGrading(0);
		
		System.out.println(asJsonString(adto));
		
		
		response = mvc.perform(MockMvcRequestBuilders.put("/instructor/update").accept(MediaType.APPLICATION_JSON)
				.content(asJsonString(adto)).contentType(MediaType.APPLICATION_JSON))
		.andReturn().getResponse();
		
		
		assertEquals(200, response.getStatus());
	}
	
	
	
	@Disabled("WIP")
	@Test
	public void deleteAssignment() throws Exception {
		
		MockHttpServletResponse response;
	
		// mock database data
		
		Assignment assignment = new Assignment();
		// set dueDate to 1 week before now.
		assignment.setDueDate(new java.sql.Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000));
		assignment.setId(TEST_ASSIGNMENT_ID);
		assignment.setName(TEST_ASSIGNMENT_NAME);
		assignment.setNeedsGrading(1);
		
		given(assignmentRepository.findById(TEST_ASSIGNMENT_ID)).willReturn(null);
		given(assignmentRepository.save(any(Assignment.class))).willReturn(assignment);
		
			
		AssignmentDTO assignmentDTO = new AssignmentDTO();
		assignmentDTO.assignmentId = TEST_ASSIGNMENT_ID;
		assignmentDTO.assignmentName = TEST_ASSIGNMENT_NAME;
		assignmentDTO.dueDate = new java.sql.Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
		assignmentDTO.needsGrading = 1;
		
		
		
		response = mvc.perform(MockMvcRequestBuilders.post("/instructor/delete").accept(MediaType.APPLICATION_JSON)
				.content(asJsonString(assignment)).contentType(MediaType.APPLICATION_JSON))
		.andReturn().getResponse();
		
//		verify(assignmentRepository, times(1)).save(any());
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
