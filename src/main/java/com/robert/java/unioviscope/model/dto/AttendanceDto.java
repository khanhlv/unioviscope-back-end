package com.robert.java.unioviscope.model.dto;

import java.io.Serializable;
import java.util.Date;

public class AttendanceDto implements Serializable {

	private static final long serialVersionUID = -40796788712998855L;

	private Boolean confirmed;
	private Date date;
	private String comment;
	private Boolean faceRecognitionOff;
	private Long student;
	private Long session;
	
	AttendanceDto() {
		
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getFaceRecognitionOff() {
		return faceRecognitionOff;
	}

	public void setFaceRecognitionOff(Boolean faceRecognitionOff) {
		this.faceRecognitionOff = faceRecognitionOff;
	}

	public Long getStudent() {
		return student;
	}

	public void setStudent(Long student) {
		this.student = student;
	}

	public Long getSession() {
		return session;
	}

	public void setSession(Long session) {
		this.session = session;
	}
}
