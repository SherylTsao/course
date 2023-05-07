package com.example.Course_system.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.Course_system.contants.RtnCode;
import com.example.Course_system.entity.Students;
import com.example.Course_system.entity.StudentsData;
import com.example.Course_system.respository.StudentsDao;
import com.example.Course_system.service.ifs.StudentsService;
import com.example.Course_system.vo.StudentsDataRequest;
import com.example.Course_system.vo.StudentsDataResponse;

@Service
public class StudentsServiceImpl implements StudentsService {

	@Autowired
	private StudentsDao studentsDao;

	@Override
	public StudentsDataResponse create(StudentsDataRequest request) {

		// 要求外部傳入的學生名字的防呆(不得為空)
		if (!StringUtils.hasText(request.getStuName())) {
			return new StudentsDataResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}

		// 要求外部傳入的學生學號的防呆(不得為空)
		if (request.getStuNumber() <= 0) {
			return new StudentsDataResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		// 用Entity型態new出建構方法
		Students students = new Students(request.getStuName(), request.getStuNumber());
		// 存入資料庫
		studentsDao.save(students);

		return new StudentsDataResponse(students, RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public StudentsDataResponse update(StudentsDataRequest request) {

		// 要求外部傳入的學生名字的防呆(不得為空)
		if (!StringUtils.hasText(request.getStuName())) {
			return new StudentsDataResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}

		// 要求外部傳入的學生學號的防呆(不得為空)
		if (request.getStuNumber() <= 0) {
			return new StudentsDataResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}

		// 從資料庫撈是否有該名學生
		if (!studentsDao.existsById(request.getStuNumber())) {
			return new StudentsDataResponse(RtnCode.NOT_FOUND.getMessage());
		}

		
		
		// 用Entity型態new出建構方法
		Students students = new Students(request.getStuName(), request.getStuNumber());

		// 存入資料庫
		studentsDao.save(students);
		return new StudentsDataResponse(students, RtnCode.SUCCESSFUL.getMessage());
	}

}
