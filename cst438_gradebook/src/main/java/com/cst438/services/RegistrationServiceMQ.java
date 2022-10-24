package com.cst438.services;


import java.util.Optional;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.Course;
import com.cst438.domain.CourseDTOG;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;


public class RegistrationServiceMQ extends RegistrationService {

	@Autowired
	EnrollmentRepository enrollmentRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public RegistrationServiceMQ() {
		System.out.println("MQ registration service ");
	}

	// ----- configuration of message queues

	@Autowired
	Queue registrationQueue;


	// ----- end of configuration of message queue

	// receiver of messages from Registration service
	
	@RabbitListener(queues = "gradebook-queue")
	@Transactional
	public void receive(EnrollmentDTO enrollmentDTO) {
		
		System.out.println("Receive enrollment :" + enrollmentDTO);

	
//			Enrollment e = new Enrollment(enrollmentDTO);
		
			Optional<Course> c = courseRepository.findById(enrollmentDTO.course_id);
			
			
			if(c.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Course does not exist");
			}else {
				
//				Enrollment e = new Enrollment(enrollmentDTO);
				
				Enrollment e = new Enrollment();
				e.setId(enrollmentDTO.id);
				e.setStudentEmail(enrollmentDTO.studentEmail);
				e.setStudentName(enrollmentDTO.studentName);
//				e.setCourse(enrollmentDTO.course_id);
				
//				System.out.println("current enrollmentDTO course id: " + enrollmentDTO.courseId.getCourse_id());
//				System.out.println("current e course id: " + e.getCourse().getCourse_id());
				
				enrollmentRepository.save(e);
					
			}
	}

	// sender of messages to Registration Service
	@Override
	public void sendFinalGrades(int course_id, CourseDTOG courseDTO) {
		 
		System.out.println("Sending final grades through rabbitMQ" + course_id + " " + courseDTO);
		
		System.out.println("Current registration name: " + registrationQueue.getName());
		rabbitTemplate.convertAndSend(registrationQueue.getName(), courseDTO);
		System.out.println("After sending final grades through rabbitMQ:");
	}
	


}
