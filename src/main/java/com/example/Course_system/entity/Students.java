package com.example.Course_system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Students {

	@Id
	@Column(name = "stu_number")
	private int stuNumber;

	@Column(name = "stu_name")
	private String stuName;

	public Students() {
		super();

	}

	public Students(String stuName, int stuNumber) {
		super();
		this.stuName = stuName;
		this.stuNumber = stuNumber;

	}

	public int getStuNumber() {
		return stuNumber;
	}

	public void setStuNumber(int stuNumber) {
		this.stuNumber = stuNumber;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

}
