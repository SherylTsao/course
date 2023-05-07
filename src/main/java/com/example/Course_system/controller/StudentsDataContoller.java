package com.example.Course_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Course_system.service.ifs.StudentsDataService;

import com.example.Course_system.vo.StudentsDataResponse;
import com.example.Course_system.vo.StudentsDataRequest;

@RestController
public class StudentsDataContoller {
	
	@Autowired // 指定託管類別
	private StudentsDataService studentsDataService;

	@PostMapping(value = "create") // 修改課程:連結外部
	public StudentsDataResponse create(@RequestBody StudentsDataRequest request) {
		// 通常跟Service方法一樣名稱,直接copy
		return studentsDataService.create(request);
		// 連結內部
	}
	@PostMapping(value = "delete") // 修改課程:連結外部
	public StudentsDataResponse delete(@RequestBody StudentsDataRequest request) {
		// 通常跟Service方法一樣名稱,直接copy
		return studentsDataService.delete(request);
		// 連結內部
	}
	@PostMapping(value = "search") // 修改課程:連結外部
	public StudentsDataResponse search(@RequestBody StudentsDataRequest request) {
		// 通常跟Service方法一樣名稱,直接copy
		return studentsDataService.search(request);
		// 連結內部
	}
	@PostMapping(value = "deleteStudents") // 修改課程:連結外部
	public StudentsDataResponse deleteStudents(@RequestBody StudentsDataRequest request) {
		// 通常跟Service方法一樣名稱,直接copy
		return studentsDataService.deleteStudents(request);
		// 連結內部
	}
	
	

}
