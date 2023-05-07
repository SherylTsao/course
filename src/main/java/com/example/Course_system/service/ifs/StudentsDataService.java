package com.example.Course_system.service.ifs;


import com.example.Course_system.vo.StudentsDataRequest;
import com.example.Course_system.vo.StudentsDataResponse;

public interface StudentsDataService {

	public StudentsDataResponse create(StudentsDataRequest request);
	
	public StudentsDataResponse delete(StudentsDataRequest request);
	
	public StudentsDataResponse search(StudentsDataRequest request);
	
	public StudentsDataResponse deleteStudents(StudentsDataRequest request);
	
	
	
}
