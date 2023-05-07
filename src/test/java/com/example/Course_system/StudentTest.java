package com.example.Course_system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.example.Course_system.service.ifs.StudentsService;
import com.example.Course_system.vo.StudentsDataRequest;
import com.example.Course_system.vo.StudentsDataResponse;

@SpringBootTest(classes = CourseSystemApplication.class)
public class StudentTest {
	@Autowired
	private StudentsService studentsService;

	@Test
	public void createTest() {
		StudentsDataRequest request = new StudentsDataRequest();
		request.setStuName("王曉明");
		request.setStuNumber(107189220);
		StudentsDataResponse res = studentsService.create(request);
		System.out.println(res.getMessage());
	}
	
	@Test
	public void updateTest() {
		StudentsDataRequest request = new StudentsDataRequest();
		request.setStuName("王曉");
		request.setStuNumber(107189220);
		StudentsDataResponse res = studentsService.update(request);
		System.out.println(res.getMessage());
	}
}
