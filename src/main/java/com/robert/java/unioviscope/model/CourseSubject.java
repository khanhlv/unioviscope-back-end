package com.robert.java.unioviscope.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robert.java.unioviscope.model.types.key.CourseSubjectKey;

@IdClass(CourseSubjectKey.class)
@Entity
@Table(name = "TCourseSubject")
public class CourseSubject implements Serializable {

	private static final long serialVersionUID = 2069664024797548431L;

	@NotNull(message = "{courseSubject.subject.null}")
	@Id
	@ManyToOne
	private Subject subject;

	@NotNull(message = "{courseSubject.course.null}")
	@Id
	@ManyToOne
	private Course course;

	@NotNull(message = "{courseSubject.groups.null}")
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	private Set<Group> groups = new HashSet<>();

	CourseSubject() {

	}

	public CourseSubject(Subject subject, Course course) {
		this.subject = subject;
		this.course = course;

		if (subject != null)
			subject._getCourseSubjects().add(this);
		if (course != null)
			course._getCourseSubjects().add(this);
	}

	public Subject getSubject() {
		return subject;
	}

	public Course getCourse() {
		return course;
	}

	@JsonIgnore
	public Set<Group> getGroups() {
		return Collections.unmodifiableSet(groups);
	}

	@Override
	public String toString() {
		return "CourseSubject [subject=" + subject + ", course=" + course + "]";
	}

	// -------------- RELATIONS --------------

	public void unlink() {
		subject._getCourseSubjects().remove(this);
		course._getCourseSubjects().remove(this);

		this.subject = null;
		this.course = null;
	}

	public void addGroup(Group g) {
		g._setCourseSubject(this);
		groups.add(g);
	}

	public void removeGroup(Group g) {
		groups.remove(g);
		g._setCourseSubject(null);
	}
}
