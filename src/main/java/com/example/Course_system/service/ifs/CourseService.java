package com.example.Course_system.service.ifs;

import java.util.List;

import com.example.Course_system.entity.Course;
import com.example.Course_system.vo.CourseRequest;
import com.example.Course_system.vo.CourseResponse;

public interface CourseService {

	// 老師建立課程資料
	public CourseResponse createCourse(CourseRequest request);

	// 老師更新修改課程資料(課程名稱、上課星期、上下課時間、學分數)
	public CourseResponse updateCourse(CourseRequest request);

	// 刪除課程資料
	public CourseResponse deleteCourse(CourseRequest request);

	// 依課程名稱(查出多筆)
	public CourseResponse readCourse(CourseRequest request);

	

}
