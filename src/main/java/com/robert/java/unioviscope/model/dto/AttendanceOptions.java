package com.robert.java.unioviscope.model.dto;

import java.io.Serializable;

public class AttendanceOptions implements Serializable {

	private static final long serialVersionUID = 7354115112781968279L;

	private String format;
	private Long teacherId;
	private Long subjectId;
	private Long groupId;
	private Long sessionId;
	
	public AttendanceOptions() {

	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}
}
