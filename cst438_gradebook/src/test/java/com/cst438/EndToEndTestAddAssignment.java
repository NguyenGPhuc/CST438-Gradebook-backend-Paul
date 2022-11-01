package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;

/*
 * This example shows how to use selenium testing using the web driver 
 * with Chrome browser.
 * 
 *  - Buttons, input, and anchor elements are located using XPATH expression.
 *  - onClick( ) method is used with buttons and anchor tags.
 *  - Input fields are located and sendKeys( ) method is used to enter test data.
 *  - Spring Boot JPA is used to initialize, verify and reset the database before
 *      and after testing.
 *      
 *  In SpringBootTest environment, the test program may use Spring repositories to 
 *  setup the database for the test and to verify the result.
 */

@SpringBootTest
public class EndToEndTestAddAssignment {

	public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver_win32/chromedriver.exe";

	public static final String URL = "http://localhost:3000";
	public static final int TEST_COURSE_ID = 7777777;
	public static final int SLEEP_DURATION = 1000; // 1 second.
	public static final String TEST_ASSIGNMENT_NAME = "ETE test assignment";
	public static final String TEST_DUE_DATE = "2023-02-21";
	public static final String TEST_INSTRUCTOR_NAME = "Paul Nguyen";
	public static final String TEST_SEMESTER = "Winter";
	public static final String TEST_COURSE_NAME = "Into to day seizing";
	public static final int TEST_YEAR = 2023;
	
	public static final String TEST_STUDENT_NAME = "ETE Test";
	public static final String TEST_USER_EMAIL = "ETE_test@csumb.edu";

	@Autowired
	EnrollmentRepository enrollmentRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	AssignmentGradeRepository assignnmentGradeRepository;

	@Autowired
	AssignmentRepository assignmentRepository;

	@Test
	public void addAssignment() throws Exception {

		// Add a course for testing
		Course c = new Course();
		c.setCourse_id(TEST_COURSE_ID);
		c.setSemester(TEST_SEMESTER);
		c.setTitle(TEST_COURSE_NAME);
		c.setYear(TEST_YEAR);
		c.setInstructor(TEST_INSTRUCTOR_NAME);
		
		// Add a new assignment for testing
		Assignment a = new Assignment();

		a.setNeedsGrading(1);

		// Add a new student to enrollment
		Enrollment e = new Enrollment();
		e.setCourse(c);
		e.setStudentEmail(TEST_USER_EMAIL);
		e.setStudentName(TEST_STUDENT_NAME);

		courseRepository.save(c);
		assignmentRepository.save(a);
		enrollmentRepository.save(e);


		// set the driver location and start driver
		//@formatter:off
		// browser	property name 				Java Driver Class
		// edge 	webdriver.edge.driver 		EdgeDriver
		// FireFox 	webdriver.firefox.driver 	FirefoxDriver
		// IE 		webdriver.ie.driver 		InternetExplorerDriver
		//@formatter:on
		
		/*
		 * initialize the WebDriver and get the home page. 
		 */

		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver driver = new ChromeDriver();
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get(URL);
		Thread.sleep(SLEEP_DURATION);
		

		try {
			// Automatically open add new assignment Toast
			driver.findElement(By.id("addAssignmentID")).click();
			Thread.sleep(SLEEP_DURATION);

			// Automatically set 
			driver.findElement(By.id("courseID")).sendKeys(Integer.toString(TEST_COURSE_ID));
			driver.findElement(By.id("assignmentNameID")).sendKeys(TEST_ASSIGNMENT_NAME);
			driver.findElement(By.id("dueDateID")).sendKeys(TEST_DUE_DATE);
			Thread.sleep(SLEEP_DURATION);
			
			// Automatically click add
			driver.findElement(By.id("addID")).click();
			Thread.sleep(SLEEP_DURATION);
			
			// Check automatic toast
			String toast_text = driver.findElement(By.cssSelector(".Toastify__toast-body div:nth-child(2)")).getText();
			assertEquals(toast_text, "Toast is working");
			Thread.sleep(SLEEP_DURATION);
			
		} catch (Exception ex) {
			throw ex;
		} finally {

			/*
			 *  clean up database so the test is repeatable.
			 */
		
			enrollmentRepository.delete(e);
			assignmentRepository.delete(a);
			enrollmentRepository.delete(e);
			

			driver.quit();
		}

	}
}
