package com.example.Course_system.service.ifs;

import com.example.Course_system.vo.StudentsDataRequest;
import com.example.Course_system.vo.StudentsDataResponse;

public interface StudentsService {
    //新增學校的學生資訊:名字、學號
	public StudentsDataResponse create(StudentsDataRequest request);
	//修改學生名字++++++++++
	public StudentsDataResponse update(StudentsDataRequest request);
}
