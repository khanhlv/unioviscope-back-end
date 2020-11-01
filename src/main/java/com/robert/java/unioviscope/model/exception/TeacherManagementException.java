package com.robert.java.unioviscope.model.exception;

public class TeacherManagementException extends BusinessException {

	private static final long serialVersionUID = 5588225899070120650L;

	public TeacherManagementException(String field, String key) {
		super(field, key);
	}
	
	public TeacherManagementException(String field, String key, Object... args) {
		super(field, key, args);
	}
}
