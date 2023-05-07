package com.example.Course_system.vo;

import java.util.List;

import com.example.Course_system.entity.Course;
import com.example.Course_system.entity.Students;
import com.example.Course_system.entity.StudentsData;



public class StudentsDataResponse {

	List<Course>course;
	
	List<StudentsData>students;
	
	 Students student;
	
	String message;
	

	public StudentsDataResponse() {
		super();
		
	}
	public StudentsDataResponse(String message) {
		super();
		this.message = message;
	}
	public StudentsDataResponse(List<StudentsData> students, String message) {
		super();
		this.students = students;
		this.message = message;
	}
	

	public StudentsDataResponse(Students student, String message) {
		super();
		this.student = student;
		this.message = message;
	}
	public List<Course> getCourse() {
		return course;
	}

	public void setCourse(List<Course> course) {
		this.course = course;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<StudentsData> getStudents() {
		return students;
	}

	public void setStudentsDate(List<StudentsData> studentsDate) {
		this.students = studentsDate;
	}
	public Students getStudent() {
		return student;
	}
	public void setStudent(Students student) {
		this.student = student;
	}
	public void setStudents(List<StudentsData> students) {
		this.students = students;
	}
	
	
}
