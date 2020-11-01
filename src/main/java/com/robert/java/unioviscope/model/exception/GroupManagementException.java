package com.robert.java.unioviscope.model.exception;

public class GroupManagementException extends BusinessException {

	private static final long serialVersionUID = 5588225899070120650L;

	public GroupManagementException(String field, String key) {
		super(field, key);
	}
	
	public GroupManagementException(String field, String key, Object... args) {
		super(field, key, args);
	}
}
