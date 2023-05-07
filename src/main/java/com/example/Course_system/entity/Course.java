package com.example.Course_system.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class Course {

	@GeneratedValue(strategy = GenerationType.IDENTITY) // 流水編號遞增
	@Id
	@Column(name = "cour_seq")
	private int courSeq;// 打建構方法可省略,因為系統自動遞增,所以自己輸入的無效
	

	@Column(name = "cour_name")
	private String courName;

	@Column(name = "cour_day")
	private String courDay;

	@Column(name = "start_time")
	private LocalTime startTime;

	@Column(name = "end_time")
	private LocalTime endTime;

	@Column(name = "cour_credit")
	private int courCredit;

	public Course() {
		super();
	}

	

	public Course(String courName, String courDay, LocalTime startTime, LocalTime endTime, int courCredit) {
		super();
		this.courName = courName;
		this.courDay = courDay;
		this.startTime = startTime;
		this.endTime = endTime;
		this.courCredit = courCredit;
	}

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

}
