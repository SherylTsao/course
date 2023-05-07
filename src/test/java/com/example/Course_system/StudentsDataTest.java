package com.example.Course_system;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.example.Course_system.service.ifs.StudentsDataService;
import com.example.Course_system.vo.StudentsDataRequest;
import com.example.Course_system.vo.StudentsDataResponse;

@SpringBootTest(classes = CourseSystemApplication.class)
public class StudentsDataTest {

	@Autowired
	private StudentsDataService studentsDataService;

	@Test
	public void createTest() {
		StudentsDataRequest request = new StudentsDataRequest();
		request.setStuName("慈慈");
		request.setStuNumber(107189218);
		List<Integer> courSeqList = new ArrayList<>();
//		courSeqList.add(4);
//		courSeqList.add(1);
		courSeqList.add(1);
		courSeqList.add(10000);
		courSeqList.add(2);
		request.setCourSeqList(courSeqList);
		StudentsDataResponse o = studentsDataService.create(request);
		System.out.println(o.getMessage());

	}
	@Test
	public void deleteTest() {
		StudentsDataRequest request = new StudentsDataRequest();
		request.setStuName("王曉");
		request.setStuNumber(107189220);
		request.setCourSeq(7);;
		StudentsDataResponse q = studentsDataService.delete(request);
		System.out.println(q.getMessage());
	}
	@Test
	public void searchTest() {
		StudentsDataRequest request = new StudentsDataRequest();
		request.setStuName("王曉");
		request.setStuNumber(107189220);
		StudentsDataResponse search = studentsDataService.search(request);
		System.out.println(search.getMessage());
	}
	@Test
	public void deleteStudentsTest() {
		StudentsDataRequest request = new StudentsDataRequest();
		request.setStuName("曉曉");
		request.setStuNumber(107189215);
		StudentsDataResponse search = studentsDataService.deleteStudents(request);
		System.out.println(search.getMessage());
	}
	

}
