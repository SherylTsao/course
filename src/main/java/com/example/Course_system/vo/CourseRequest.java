package com.example.Course_system.vo;

import java.time.LocalTime;
import java.util.List;

import com.example.Course_system.entity.Course;



public class CourseRequest {

	private int courSeq;
	
	private String courName;
	
	private String courDay;
	
	private LocalTime startTime;
	
	private LocalTime endTime;
	
	private List <Course> course;
	
	private int courCredit;

	public int getCourSeq() {
		return courSeq;
	}

	public void setCourSeq(int courSeq) {
		this.courSeq = courSeq;
	}

	public String getCourName() {
		return courName;
	}

	public void setCourName(String courName) {
		this.courName = courName;
	}

	public String getCourDay() {
		return courDay;
	}

	public void setCourDay(String courDay) {
		this.courDay = courDay;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public int getCourCredit() {
		return courCredit;
	}

	public void setCourCredit(int courCredit) {
		this.courCredit = courCredit;
	}

	public List<Course> getCourse() {
		return course;
	}

	public void setCourse(List<Course> course) {
		this.course = course;
	}

	

}
