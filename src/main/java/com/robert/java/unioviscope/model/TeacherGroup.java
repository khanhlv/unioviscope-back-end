package com.robert.java.unioviscope.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.robert.java.unioviscope.model.types.key.TeacherGroupKey;

@IdClass(TeacherGroupKey.class)
@Entity
@Table(name = "TTeacherGroup")
public class TeacherGroup implements Serializable {

	private static final long serialVersionUID = -1198357151607492679L;

	@NotNull(message = "{teacherGroup.teacher.null}")
	@Id
	@ManyToOne
	private Teacher teacher;
	
	@NotNull(message = "{teacherGroup.group.null}")
	@Id
	@ManyToOne
	private Group group;

	TeacherGroup() {

	}

	public TeacherGroup(Teacher teacher, Group group) {
		this.teacher = teacher;
		this.group = group;

		if (teacher != null)
			teacher._getTeacherGroups().add(this);
		if (group != null)
			group._getTeacherGroups().add(this);
	}

	@JsonManagedReference
	public Teacher getTeacher() {
		return teacher;
	}

	@JsonManagedReference
	public Group getGroup() {
		return group;
	}

	@Override
	public String toString() {
		return "TeacherGroup [teacher=" + teacher + ", group=" + group + "]";
	}

	// -------------- RELATIONS --------------

	public void unlink() {
		teacher._getTeacherGroups().remove(this);
		group._getTeacherGroups().remove(this);

		this.teacher = null;
		this.group = null;
	}
}
