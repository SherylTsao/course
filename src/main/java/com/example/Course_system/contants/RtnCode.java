package com.example.Course_system.contants;

public enum RtnCode {
	SUCCESSFUL("200","successful!!"),//表示多個
	CANNOT_EMPTY("400","It cannot be empty!!"),
	DATA_ERROR("400","error!!"),
	NOT_FOUND("404","Not found!!"),
	EXISTS("400"," Already exist!!"),
	TIME_ERROR("400","Time error!!"),
	STUDENT_SELECT("400","It cannot be delete!!"),
	OUTOF_SELECT("400","Out of available credits!"),
	OUTOF_STUDENTS("400","Out of available students!");
	
	private String code;
	
	private String message;
	
	//無空的建構方法!

	private RtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {//why 只需要get 不用set?因為在這裡設好了
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
