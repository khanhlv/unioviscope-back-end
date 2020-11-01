package com.robert.java.unioviscope.model.exception;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = -6573985284535012219L;

	private String field;
	private String code;
	private String exception;
	private String message;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private static ExceptionResponse of(ConstraintViolation<?> constraintViolation) {
		String field = StringUtils.substringAfter(constraintViolation.getPropertyPath().toString(), ".");
		
		if (field == null || field.isEmpty())
			field = constraintViolation.getPropertyPath().toString();
		
		ExceptionResponse eR = new ExceptionResponse();
		eR.setField(field);
		eR.setCode(HttpStatus.BAD_REQUEST.toString());
		eR.setException(constraintViolation.getClass().getSimpleName());
		eR.setMessage(constraintViolation.getMessage());
		return eR;
	}

	public static List<ExceptionResponse> getErrors(Set<ConstraintViolation<?>> constraintViolations) {
		return constraintViolations.stream().map(ExceptionResponse::of).collect(Collectors.toList());
	}
}
