package com.robert.java.unioviscope.model.types.key;

import java.io.Serializable;

public class TeacherGroupKey implements Serializable {

	private static final long serialVersionUID = 3625784495223510383L;

	private Long teacher;
	private Long group;

	public TeacherGroupKey() {

	}

	public Long getTeacher() {
		return teacher;
	}

	public void setTeacher(Long teacher) {
		this.teacher = teacher;
	}

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long group) {
		this.group = group;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
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
		TeacherGroupKey other = (TeacherGroupKey) obj;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (teacher == null) {
			if (other.teacher != null)
				return false;
		} else if (!teacher.equals(other.teacher))
			return false;
		return true;
	}
}
