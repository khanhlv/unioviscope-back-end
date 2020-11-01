package com.robert.java.unioviscope.model.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1058931814311702510L;

	private String field;
	private String key;
	private Object[] args;

	public BusinessException() {
		super();
	}

	public BusinessException(String field, String key, Object... args) {
		this.field = field;
		this.key = key;
		this.args = args;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Override
	public String getMessage() {
		return getKey();
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String message) {
		this.key = message;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
}