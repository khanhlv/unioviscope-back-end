package com.robert.java.unioviscope.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@DiscriminatorValue("TEACHER")
public class Teacher extends User {

	private static final long serialVersionUID = -8965276324950843160L;

	@NotNull(message = "{teacher.teacherGroups.null}")
	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	private Set<TeacherGroup> teacherGroups = new HashSet<>();

	Teacher() {

	}

	public Teacher(String userName) {
		super(userName);
	}

	@JsonBackReference("teacher")
	public Set<TeacherGroup> getGroups() {
		return Collections.unmodifiableSet(teacherGroups);
	}

	@Override
	public String toString() {
		return "Teacher [userName=" + getUserName() + ", role=" + getRole() + "]";
	}

	// -------------- RELATIONS --------------

	Set<TeacherGroup> _getTeacherGroups() {
		return teacherGroups;
	}
}
