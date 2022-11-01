package com.cst438.domain;

import java.sql.Date;
import java.util.ArrayList;

/*
 * a transfer object that is a list of assignment details
 */
public class AssignmentListDTO {

	public static class AssignmentDTO {
		public int assignmentId;
		public String name;
		public Date dueDate;
		public String courseTitle;
		public int courseId;
		public int needsGrading;

		public AssignmentDTO(int assignmentId, int courseId, String assignmentName, Date dueDate,
				String courseTitle, int needsGrading) {
			this.assignmentId = assignmentId;
			this.courseId = courseId;
			this.name = assignmentName;
			this.dueDate = dueDate;
			this.courseTitle = courseTitle;
			this.needsGrading = needsGrading;
		}

		public AssignmentDTO() {
			super();
		}

		@Override
		public String toString() {
			return "[assignmentId=" + assignmentId + ", assignmentName=" + name + ", dueDate="
					+ dueDate + ", courseTitle=" + courseTitle + ", courseId=" + courseId + "]";
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AssignmentDTO other = (AssignmentDTO) obj;
			if (assignmentId != other.assignmentId)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (courseId != other.courseId)
				return false;
			if (courseTitle == null) {
				if (other.courseTitle != null)
					return false;
			} else if (!courseTitle.equals(other.courseTitle))
				return false;
			if (dueDate == null) {
				if (other.dueDate != null)
					return false;
			} else if (!dueDate.equals(other.dueDate))
				return false;
			return true;
		}

		public int getAssignmentID() {
			// TODO Auto-generated method stub
			return this.assignmentId;
		}
		public void setAssignmentID(int ID)
		{
			this.assignmentId = ID;
		}
		public String getAssignmentName() {
			return this.name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Date getDueDate() {
			return this.dueDate;
		}
		public void setDueDate(Date date) {
			this.dueDate = date;
		}
		public String getCourseTitle() {
			return this.courseTitle;
		}
		public void setCourseTitle(String title) {
			this.courseTitle = title;
		}
		public int getCourseId() {
			return this.courseId;
		}
		public void setCourseID(int id) {
			this.courseId = id;
		}
		public int getNeedsGrading() {
			return this.needsGrading;
		}
		public void setNeedsGrading(int needsGrading) {
			this.needsGrading = needsGrading;
		}
		
	}

	public ArrayList<AssignmentDTO> assignments = new ArrayList<>();

	@Override
	public String toString() {
		return "AssignmentListDTO " + assignments ;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssignmentListDTO other = (AssignmentListDTO) obj;
		if (assignments == null) {
			if (other.assignments != null)
				return false;
		} else if (!assignments.equals(other.assignments))
			return false;
		return true;
	}
	
	
}