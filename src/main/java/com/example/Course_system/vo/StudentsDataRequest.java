package com.example.Course_system.vo;

import java.util.List;

import com.example.Course_system.entity.StudentsData;

public class StudentsDataRequest {

	private List<StudentsData> studentsData;

	private List<Integer> courSeqList;

	private int stuSeq;

	private int stuNumber;

	private String stuName;

	private int courSeq;

	private int stuCredit;


	private String message;

	public StudentsDataRequest() {
		super();
	}

	public StudentsDataRequest(List<StudentsData> studentsData, String message) {
		super();
		this.studentsData = studentsData;
		this.message = message;
	}

	public List<StudentsData> getStudentsData() {
		return studentsData;
	}

	public void setStudentsData(List<StudentsData> studentsData) {
		this.studentsData = studentsData;
	}

	public int getStuSeq() {
		return stuSeq;
	}

	public void setStuSeq(int stuSeq) {
		this.stuSeq = stuSeq;
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

	public int getCourSeq() {
		return courSeq;
	}

	public void setCourSeq(int courSeq) {
		this.courSeq = courSeq;
	}

	public int getStuCredit() {
		return stuCredit;
	}

	public void setStuCredit(int stuCredit) {
		this.stuCredit = stuCredit;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Integer> getCourSeqList() {
		return courSeqList;
	}

	public void setCourSeqList(List<Integer> courSeqList) {
		this.courSeqList = courSeqList;
	}

}
