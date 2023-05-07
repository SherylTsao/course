package com.example.Course_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Course_system.service.ifs.CourseService;
import com.example.Course_system.vo.CourseRequest;
import com.example.Course_system.vo.CourseResponse;

@RestController
public class CourseController {

	@Autowired // 指定託管類別
	private CourseService courseService;

	@PostMapping(value = "createCourse") // 建立(新增)課程:連結外部
	public CourseResponse createCourse(@RequestBody CourseRequest request) {

		// 通常跟Service方法一樣名稱,直接copy
		return courseService.createCourse(request);
		// 連結內部
	}

	@PostMapping(value = "updateCourse") // 修改課程:連結外部
	public CourseResponse updateCourse(@RequestBody CourseRequest request) {
		// 通常跟Service方法一樣名稱,直接copy
		return courseService.updateCourse(request);
		// 連結內部
	}

	

	@PostMapping(value = "deleteCourse") // 刪除課程:連結外部
	public CourseResponse deleteCourse(@RequestBody CourseRequest request) {
		// 通常跟Service方法一樣名稱,直接copy
		return courseService.deleteCourse(request);
		// 連結內部
	}
	@PostMapping(value = "readCourse") // 查詢課程:連結外部
	public CourseResponse readCourse(@RequestBody CourseRequest request) {
		// 通常跟Service方法一樣名稱,直接copy
		return courseService.readCourse(request);
		// 連結內部
	}

}
