package com.example.Course_system;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.Course_system.entity.Course;
import com.example.Course_system.service.ifs.CourseService;
import com.example.Course_system.vo.CourseRequest;
import com.example.Course_system.vo.CourseResponse;

@SpringBootTest(classes = CourseSystemApplication.class)
public class CourseTest {

	@Autowired
	private CourseService courseService;

	@Test
	public void createCourseTest() {
		CourseRequest request = new CourseRequest();
		
		request.setCourName("lll");
		request.setCourCredit(2);
		request.setCourDay("五");
		request.setStartTime(LocalTime.of(10, 30));
		request.setEndTime(LocalTime.of(10, 00));
		CourseResponse res = courseService.createCourse(request);
		System.out.println(res.getMessage());
	}

	@Test
	public void updateCourseTest() {
		CourseRequest request = new CourseRequest();
		Course course = new Course();
		course.setCourSeq(1);
		course.setCourName("ABCD");
//		course.setStartTime(LocalTime.of(8, 00));
		course.setEndTime(LocalTime.of(7, 00));
		List<Course> courseList = new ArrayList<>();
		courseList.add(course);
		request.setCourse(courseList);
		CourseResponse res = courseService.updateCourse(request);
		System.out.println(res.getMessage());
	}

	@Test
	public void deleteCourseTest() {
		CourseRequest request = new CourseRequest();
		request.setCourSeq(9);
//		System.out.println(request);
		CourseResponse res = courseService.deleteCourse(request);
		System.out.println(res.getMessage());
	}

	@Test
	public void readCourseTest() {
		CourseRequest request = new CourseRequest();
		request.setCourSeq(1);
//		request.setCourName("math");
		CourseResponse res = courseService.readCourse(request);
		System.out.println(res.getMessage());
		System.out.println(res.getMyCourse().getCourName());
		
	}

}
