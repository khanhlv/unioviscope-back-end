package com.robert.java.unioviscope.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.robert.java.unioviscope.model.types.key.StudentGroupKey;

@IdClass(StudentGroupKey.class)
@Entity
@Table(name = "TStudentGroup")
public class StudentGroup implements Serializable {

	private static final long serialVersionUID = -6294193470261130269L;

	@NotNull(message = "{studentGroup.student.null}")
	@Id
	@ManyToOne
	private Student student;

	@NotNull(message = "{studentGroup.group.null}")
	@Id
	@ManyToOne
	private Group group;

	StudentGroup() {

	}

	public StudentGroup(Student student, Group group) {
		this.student = student;
		this.group = group;

		if (student != null)
			student._getStudentGroups().add(this);
		if (group != null)
			group._getStudentGroups().add(this);
	}

	@JsonManagedReference
	public Student getStudent() {
		return student;
	}

	@JsonManagedReference
	public Group getGroup() {
		return group;
	}

	@Override
	public String toString() {
		return "StudentGroup [student=" + student + ", group=" + group + "]";
	}

	// -------------- RELATIONS --------------

	public void unlink() {
		student._getStudentGroups().remove(this);
		group._getStudentGroups().remove(this);

		this.student = null;
		this.group = null;
	}
}
