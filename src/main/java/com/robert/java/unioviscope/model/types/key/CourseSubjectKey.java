package com.robert.java.unioviscope.model.types.key;

import java.io.Serializable;

public class CourseSubjectKey implements Serializable {

	private static final long serialVersionUID = -6409478762896792628L;

	private Long subject;
	private Long course;

	public CourseSubjectKey() {

	}

	public Long getSubject() {
		return subject;
	}

	public void setSubject(Long subject) {
		this.subject = subject;
	}

	public Long getCourse() {
		return course;
	}

	public void setCourse(Long course) {
		this.course = course;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseSubjectKey other = (CourseSubjectKey) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}
}
