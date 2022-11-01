package com.cst438.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Assignment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course;
	
	@OneToMany(mappedBy="assignment")
	private List<AssignmentGrade> assignmentGrades;
	
	private String name;
	private Date dueDate;
	private int needsGrading;  // 0 = false,  1= true (past due date and not all students have grades)
	
	public Assignment(int id, String name, int course_id, Date dueDate, int needsGrading) {
		super();
		this.id = id;
		this.name = name;
		this.dueDate = dueDate;
		this.needsGrading = needsGrading;
	}
	
	public Assignment(AssignmentListDTO.AssignmentDTO adto) {
		super();
		this.id = adto.assignmentId;
		this.name = adto.name;
		this.dueDate = adto.dueDate;
	}
	
	public Assignment() {
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Course getCourse() {
		// TODO Auto-generated method stub
		return this.course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public int getNeedsGrading() {
		return needsGrading;
	}
	public void setNeedsGrading(int needsGrading) {
		this.needsGrading = needsGrading;
	}
	
	@Override
	public String toString() {
		return "Assignment [id=" + id + ", name=" + name + ", dueDate=" + dueDate + ", needsGrading=" + needsGrading + "]";
	}
	public Assignment orElse(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}