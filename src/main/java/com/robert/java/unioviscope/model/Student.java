package com.robert.java.unioviscope.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {

	private static final long serialVersionUID = -5139125084320105873L;

	@NotNull(message = "{student.studentGroups.null}")
	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	private Set<StudentGroup> studentGroups = new HashSet<>();

	@JsonIgnore
	@NotNull(message = "{student.attendances.null}")
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
	private Set<Attendance> attendances = new HashSet<>();

	Student() {

	}

	public Student(String userName) {
		super(userName);
	}

	@JsonBackReference("student")
	public Set<StudentGroup> getGroups() {
		return studentGroups;
	}

	public Set<Attendance> getAttendances() {
		return attendances;
	}

	@Override
	public String toString() {
		return "Student [userName=" + getUserName() + ", role=" + getRole() + "]";
	}

	// -------------- RELATIONS --------------

	Set<StudentGroup> _getStudentGroups() {
		return studentGroups;
	}

	Set<Attendance> _getAttendances() {
		return attendances;
	}
}
