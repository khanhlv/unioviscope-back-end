package com.robert.java.unioviscope.model.types.key;

import java.io.Serializable;

public class AttendanceKey implements Serializable {

	private static final long serialVersionUID = -8956994119970384896L;

	private Long student;
	private Long session;

	public AttendanceKey() {

	}

	public AttendanceKey(Long student, Long session) {
		this.student = student;
		this.session = session;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((session == null) ? 0 : session.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
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
		AttendanceKey other = (AttendanceKey) obj;
		if (session == null) {
			if (other.session != null)
				return false;
		} else if (!session.equals(other.session))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}
}
