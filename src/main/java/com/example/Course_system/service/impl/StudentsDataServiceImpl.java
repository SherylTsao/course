package com.example.Course_system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.Course_system.contants.RtnCode;
import com.example.Course_system.entity.Course;
import com.example.Course_system.entity.Students;
import com.example.Course_system.entity.StudentsData;
import com.example.Course_system.respository.CourseDao;
import com.example.Course_system.respository.StudentsDao;
import com.example.Course_system.respository.StudentsDataDao;
import com.example.Course_system.service.ifs.StudentsDataService;
import com.example.Course_system.vo.StudentsDataRequest;
import com.example.Course_system.vo.StudentsDataResponse;

@Service
public class StudentsDataServiceImpl implements StudentsDataService {
	// 如果有些課程已存在有些是未新增過的 要有另一個
	@Autowired
	private StudentsDataDao studentsDataDao;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private StudentsDao studentsDao;

	// 新增學生資訊在table3

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

		// <防呆:確認學校有此學生,才可選>從第三張table(學生姓名&學號的資訊)裡找該名學生學號
		Optional<Students> studentsExist = studentsDao.findById(request.getStuNumber());
		// 若有在第三張table找到學生資訊,代表學校是有此名學生,才可以退選!
		if (!studentsExist.isPresent()) {
			return new StudentsDataResponse(RtnCode.NOT_FOUND.getMessage());
		}

		List<Integer> courSeqList = new ArrayList<>();

		// 取出要求外部傳入的(該學生要選的)多筆課程流水號,用List接回來外部傳入的多筆流水號
		courSeqList = request.getCourSeqList();
		// (從資料庫中)所有設置的課程裡找出學生要選的課程流水號們

		// 從資料庫找出該學生之前選的課程資料(用外部傳入的學號找)
		List<StudentsData> stuAlreadySelect = studentsDataDao.findAllByStuNumber(request.getStuNumber());
		List<Integer> oldCourseList = new ArrayList<>();

		// 每堂課只能三個人選修
		// 從table2用課程代碼找裡面是否有該名學生要選的多筆課程流水號
		List<StudentsData> selcNumber = studentsDataDao.findAllByCourSeqIn(courSeqList);
        //+課程防呆
		if(selcNumber.isEmpty()) {
			return new StudentsDataResponse(RtnCode.NOT_FOUND.getMessage());
		}
        //資料庫的流水號跟學生這次要選的流水號單筆單筆比較
		//新選課程:1.2	
		for (int index = 0; index < courSeqList.size(); index++) {
			int totalSeq = 0;
			//資料庫已選課程:1.2.1.2.1.2
			for (int nextIndex = 0; nextIndex < selcNumber.size(); nextIndex++) {
				StudentsData fir = selcNumber.get(nextIndex);
				Integer res = courSeqList.get(index);
                
				if (fir.getCourSeq() == res) {

					totalSeq++;
					if (totalSeq == 3) {
						return new StudentsDataResponse(RtnCode.OUTOF_STUDENTS.getMessage());
					}
				}
			}
		}

		// 衝堂:從學生資料庫找到已選的課程資料,跟這次要選的課程資料,比較
		// 一筆一筆拿出該學生之前所選的課程資料
		for (StudentsData item : stuAlreadySelect) {
			// 如果這次學生要選的課程流水號們(courSeqList)有包含之前所選的課程流水號們(item.getCourSeq()),就continue掉,不要讓該筆以前選的資料放進去List
			if (courSeqList.contains(item.getCourSeq())) {
				// 將之前選的課程代碼放進去oldCourseList(放舊的課程代碼的清單)
				oldCourseList.add(item.getCourSeq());
				continue;
			}
			// 將之前選的課程加進去新選的課程的List
			courSeqList.add(item.getCourSeq());

		}

		// 從課程資料庫的流水號裡找(新舊課程資料List的流水號們)
		List<Course> courseList = courseDao.findAllById(courSeqList);

		// 防呆:是否有這門課,比List長度:(新舊課程資料List的流水號們)的長度不等於(從資料庫的找到的新舊流水號們),代表有些課程沒有在資料庫裡,查無課程資訊,不能選課
		if (courSeqList.size() != courseList.size()) {
			// 這樣如果一次選多門,只有一堂找不到,全部都不能找了
			return new StudentsDataResponse(RtnCode.NOT_FOUND.getMessage());
		}

		// 防呆:衝堂-前(課程)跟後(課程)比較
		for (int time = 0; time < courseList.size(); time++) {
			for (int nextTime = time + 1; nextTime < courseList.size(); nextTime++) {
				// 取出
				Course course = courseList.get(time);
				Course newCourse = courseList.get(nextTime);
				// 衝堂判斷式:上課星期相同&A堂上課時間早於B堂結束時間&A堂結束時間晚於B堂開始時間
				if (course.getCourDay().equals(newCourse.getCourDay())
						&& course.getStartTime().isBefore(newCourse.getEndTime())
						&& course.getEndTime().isAfter(newCourse.getStartTime())) {
					return new StudentsDataResponse(RtnCode.EXISTS.getMessage());
				}
				// 防呆:無法修習相同課程名稱的課程
				if (course.getCourName().equals(newCourse.getCourName())) {
					return new StudentsDataResponse(RtnCode.EXISTS.getMessage());
				}
			}
		}

		int totalCredit = 0;
		List<StudentsData> students = new ArrayList<>();
		for (Course course : courseList) {
			// 該學生限修10學分(從資料庫找出的學分數不可以超過10)
			totalCredit += course.getCourCredit();
			if (totalCredit > 10) {
				return new StudentsDataResponse(RtnCode.OUTOF_SELECT.getMessage());
			}
			 StudentsData studentsData = new StudentsData (request.getStuNumber(),request.getStuName(),course.getCourSeq(),course.getCourCredit());
			students.add(studentsData);
		}
		
		

		// 存學生名字&學號&選的課進學生Entity資料庫(用List包再取出要的資料)
		// 存課程確認課程已有學生選
		studentsDataDao.saveAll(students);
		return new StudentsDataResponse(students, RtnCode.SUCCESSFUL.getMessage());
	}

	/*
	 * <退選>外部輸入:學生姓名、學號、要退選的課程名稱 從資料庫裡查該名學生是否有選那些課，若有則從資料庫刪除
	 * 
	 */
	@Override
	public StudentsDataResponse delete(StudentsDataRequest request) {
		// 防呆:
		// 要求外部傳入的學生「名字」的防呆(不得為空)
		if (!StringUtils.hasText(request.getStuName())) {
			return new StudentsDataResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}

		// 要求外部傳入的學生「學號」的防呆(不得為空)
		if (request.getStuNumber() <= 0) {
			return new StudentsDataResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		// 要求外部傳入的學生所選的「課程代碼」的防呆(不得為空)
		if (request.getCourSeq() == 0) {
			return new StudentsDataResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}

		// <防呆:確認學校有此學生,才可退選>從第三張table(學生姓名&學號的資訊)裡找該名學生學號+	
		// 若有在第三張table找到學生資訊,代表學校是有此名學生,才可以退選!
		if (!studentsDao.existsById(request.getStuNumber())) {
			return new StudentsDataResponse(RtnCode.NOT_FOUND.getMessage());
		}

		// 從資料庫撈出學生已選的課程(透過自定義的方法)+透過學號就好了+
		StudentsData studentsData = studentsDataDao.findByStuNumber(request.getStuNumber());
		// 若資料庫找不到課程資料+
		if (studentsData == null) {
			return new StudentsDataResponse(RtnCode.NOT_FOUND.getMessage());
		}
		// 刪除資料庫該筆資料
		studentsDataDao.delete(studentsData);
		return new StudentsDataResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public StudentsDataResponse search(StudentsDataRequest request) {
		// 防呆:
		// 要求外部傳入的學生「名字」的防呆(不得為空)
		if (!StringUtils.hasText(request.getStuName())) {
			return new StudentsDataResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		// 要求外部傳入的學生「學號」的防呆(不得為空)
		if (request.getStuNumber() <= 0) {
			return new StudentsDataResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		List<StudentsData> studentsData = studentsDataDao.findAllByStuNameAndStuNumber(request.getStuName(),
				request.getStuNumber());
		// 若資料庫找不到該名學生的課程資料==null+
		if (studentsData.isEmpty()) {
			return new StudentsDataResponse(RtnCode.NOT_FOUND.getMessage());
		}

		return new StudentsDataResponse(studentsData, RtnCode.SUCCESSFUL.getMessage());
	}

	/*
	 * 刪除學生，要先將學生選的課程退選
	 */
	@Override
	public StudentsDataResponse deleteStudents(StudentsDataRequest request) {
		// 防呆:
		// 要求外部傳入的學生「名字」的防呆(不得為空)
		if (!StringUtils.hasText(request.getStuName())) {
			return new StudentsDataResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		// 要求外部傳入的學生「學號」的防呆(不得為空)
		if (request.getStuNumber() <= 0) {
			return new StudentsDataResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		// 從第三張table(學生姓名&學號的資訊)裡找該名學生學號
		Optional<Students> students = studentsDao.findById(request.getStuNumber());
		//若資料庫裡沒有該學生
		if(!students.isPresent()) {
			return new StudentsDataResponse(RtnCode.NOT_FOUND.getMessage());
		}
		// 藉由學生學號,從資料庫找出該名學生所有選的課程
		List<StudentsData> StudentSelc = studentsDataDao.findAllByStuNumber(request.getStuNumber());
		// 若找到該名學生所選的課程,代表不能刪除該名學生,因為他還有課還沒退選,退選完才可以刪除學生
		if (!StudentSelc.isEmpty()) {
			return new StudentsDataResponse(RtnCode.DATA_ERROR.getMessage());
		}
		
		// 若有在第三張table找到學生資訊,即達成:將學生資訊刪除!
		studentsDao.delete(students.get());

		return new StudentsDataResponse(RtnCode.SUCCESSFUL.getMessage());
	}

}
