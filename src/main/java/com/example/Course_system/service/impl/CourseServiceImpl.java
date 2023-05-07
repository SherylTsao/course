package com.example.Course_system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.Course_system.contants.RtnCode;
import com.example.Course_system.entity.Course;
import com.example.Course_system.entity.StudentsData;
import com.example.Course_system.respository.CourseDao;
import com.example.Course_system.respository.StudentsDataDao;
import com.example.Course_system.service.ifs.CourseService;
import com.example.Course_system.vo.CourseRequest;
import com.example.Course_system.vo.CourseResponse;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private StudentsDataDao studentsDataDao;

	/*
	 * 建立課程表 可能會有不同課程代碼但名稱相同 防呆:null或空(課程名稱,上課星期,學分數,上課下課時間)
	 */
	@Override
	public CourseResponse createCourse(CourseRequest request) {
		// 防呆:null或空
		if (!StringUtils.hasText(request.getCourName()) || !StringUtils.hasText(request.getCourDay())
				|| request.getCourCredit() == 0 || request.getStartTime() == null || request.getEndTime() == null) {
			return new CourseResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		// +before語法
		// 防呆:上課時間不可以晚於下課時間,反之亦然
		if (request.getStartTime().isAfter(request.getEndTime())) {
			return new CourseResponse(RtnCode.TIME_ERROR.getMessage());
		} // +
			// 防呆:學分數的設定不可以超過3
		if (request.getCourCredit() < 0 || request.getCourCredit() > 3) {
			return new CourseResponse(RtnCode.DATA_ERROR.getMessage());
		}
		// 防呆:上課星期不可以超過周一~周六

		List<String> day = Arrays.asList("一", "二", "三", "四", "五", "六");
		if (!day.contains(request.getCourDay())) {
			return new CourseResponse(RtnCode.TIME_ERROR.getMessage());
		}

		// 存入資料庫
		Course courseIn = new Course(request.getCourName(), request.getCourDay(), request.getStartTime(),
				request.getEndTime(), request.getCourCredit());
		courseDao.save(courseIn);
		return new CourseResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	/*
	 * 更新修改(編輯)舊的(無法新增資料庫未建立的)課程表的某些資料:更改課程名稱or上課星期or上下課時間or學分數 要先找到該筆資料
	 * 從資料庫取出要更改的資料,再存入資料庫
	 */
	@Override
	public CourseResponse updateCourse(CourseRequest request) {

		// 防呆:資料庫已有課程資料,才可以編輯
		// List放入修改過後的資料(加上List是因為可以依據使用者需求一次輸入單筆或多筆)+
		List<Integer> courSeqList = new ArrayList<>();
		List<Course> courseReq = request.getCourse();
		for (Course item : courseReq) {
			courSeqList.add(item.getCourSeq());
		}
		List<Course> course = courseDao.findAllByCourSeqIn(courSeqList);
		// 傳入的課程代碼為空||課程是資料庫找不到的(沒有先建立在create)
		if (CollectionUtils.isEmpty(courseReq) || CollectionUtils.isEmpty(course)
				|| course.size() != courseReq.size()) {
			return new CourseResponse(RtnCode.NOT_FOUND.getMessage());
		}
		for (Course daoItem : course) {
			for (Course reqItem : courseReq) {

				if (daoItem.getCourSeq() == reqItem.getCourSeq()) {
					daoItem.setCourName(
							StringUtils.hasText(reqItem.getCourName()) ? reqItem.getCourName() : daoItem.getCourName());
					// 防呆:上課星期不可以超過周一~周六
					List<String> day = Arrays.asList("一", "二", "三", "四", "五", "六");
					daoItem.setCourDay(StringUtils.hasText(reqItem.getCourDay()) && day.contains(reqItem.getCourDay())
							? reqItem.getCourDay()
							: daoItem.getCourDay());

					daoItem.setCourCredit(
							reqItem.getCourCredit() > 0 && reqItem.getCourCredit() <= 3 ? reqItem.getCourCredit()
									: daoItem.getCourCredit());

					boolean start0 = reqItem.getEndTime() != null
							&& daoItem.getStartTime().isBefore(reqItem.getEndTime());

					boolean start2 = reqItem.getStartTime() != null
							&& reqItem.getStartTime().isBefore(daoItem.getEndTime());

					boolean start4 = reqItem.getEndTime() != null && reqItem.getStartTime() != null
							&& reqItem.getStartTime().isBefore(reqItem.getEndTime());
					daoItem.setStartTime(start2 || start4 ? reqItem.getStartTime() : daoItem.getStartTime());
					daoItem.setEndTime(start0 || start4 ? reqItem.getEndTime() : daoItem.getEndTime());
				}
			}
		}
		// 存入資料庫
		courseDao.saveAll(course);
		return new CourseResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public CourseResponse deleteCourse(CourseRequest request) {
		// 要先檢查傳入的課程代碼是否有在"課程"的資料庫裡
		// 當課程代碼沒有在課程的table裡面時
		// 用exist判斷+
		Course courSeq = courseDao.findAllByCourSeq(request.getCourSeq());
		if (courSeq == null) {
			return new CourseResponse(RtnCode.STUDENT_SELECT.getMessage());
		}
		// 刪除課程，要先檢查是否有學生選修
		List<StudentsData> studentsSelc = studentsDataDao.findAllByCourSeq(request.getCourSeq());
		// 若該課程在第二張table有資料,則不可刪除課程
		if (!studentsSelc.isEmpty()) {
			return new CourseResponse(RtnCode.DATA_ERROR.getMessage());
		}
		// 刪除table1資料庫內容+ 
		courseDao.delete(courSeq);
		return new CourseResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public CourseResponse readCourse(CourseRequest request) {
		// 防呆:不可以同時用課程代碼跟課程名稱查詢+
		if (StringUtils.hasText(request.getCourName()) && request.getCourSeq() != 0) {
			return new CourseResponse(RtnCode.DATA_ERROR.getMessage());
		}
		// 若外部有傳入課程名稱(非null或空)
		if (StringUtils.hasText(request.getCourName())) {
			// 用課程名稱,查詢所有資料(查出單或多筆)
			List<Course> all = courseDao.findAllByCourName(request.getCourName());

			// 防呆:找不到資料
			if (CollectionUtils.isEmpty(all)) {

				return new CourseResponse(RtnCode.NOT_FOUND.getMessage());
			} else {

				return new CourseResponse(all, RtnCode.SUCCESSFUL.getMessage());
			}
		}

		// 依課程代碼(查出一筆)
		if (request.getCourSeq() != 0) {
			Optional<Course> op = courseDao.findById(request.getCourSeq());

			if (op.isEmpty()) {
				return new CourseResponse(RtnCode.NOT_FOUND.getMessage());
			} else {
				return new CourseResponse(op.get(), RtnCode.SUCCESSFUL.getMessage());
			}
		}
		// 若沒有輸入名稱或流水號查詢,回傳錯誤資訊
		return new CourseResponse(RtnCode.NOT_FOUND.getMessage());

	}

}
