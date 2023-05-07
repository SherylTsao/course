package com.example.Course_system.vo;

import java.util.List;
import java.util.Optional;

import com.example.Course_system.entity.Course;

public class CourseResponse {
	private List<Course> course;

	private Course myCourse;

	
	
	private String message;
	
	

	public CourseResponse() {
		super();

	}

	public CourseResponse(String message) {
		super();
		this.message = message;
	}

	public CourseResponse(List<Course> course) {
		super();
		this.course = course;
	}



	public CourseResponse(Course myCourse, String message) {
		super();
		this.myCourse = myCourse;
		this.message = message;
	}

	public CourseResponse(List<Course> course, String message) {
		super();
		this.course = course;
		this.message = message;
	}

	

	public List<Course> getCourse() {
		return course;
	}

	public void setCourse(List<Course> course) {
		this.course = course;
	}



	public Course getMyCourse() {
		return myCourse;
	}

	public void setMyCourse(Course myCourse) {
		this.myCourse = myCourse;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

}
