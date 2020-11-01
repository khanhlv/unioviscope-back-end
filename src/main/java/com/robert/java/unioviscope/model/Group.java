package com.robert.java.unioviscope.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robert.java.unioviscope.model.types.GroupType;

@Entity
@Table(name = "TGroup")
public class Group extends GenericEntity<Long> {

	private static final long serialVersionUID = 5657889678145014122L;

	@NotNull(message = "{group.code.null}")
	@NotEmpty(message = "{group.code.empty}")
	private String code;

	@NotNull(message = "{group.type.null}")
	@Enumerated(EnumType.STRING)
	private GroupType type;

	@NotNull(message = "{group.subject.null}")
	@ManyToOne
	private CourseSubject subject;

	@NotNull(message = "{group.studentGroups.null}")
	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	private Set<StudentGroup> studentGroups = new HashSet<>();

	@NotNull(message = "{group.teacherGroups.null}")
	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	private Set<TeacherGroup> teacherGroups = new HashSet<>();

	@JsonIgnore
	@NotNull(message = "{group.sessions.null}")
	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	private Set<Session> sessions = new HashSet<>();

	Group() {

	}

	public Group(String code, CourseSubject subject) {
		this.code = code;
		if (subject != null)
			subject.addGroup(this);
	}

	public String getCode() {
		return code;
	}

	public GroupType getType() {
		return type;
	}

	public void setType(GroupType type) {
		this.type = type;
	}

	public CourseSubject getSubject() {
		return subject;
	}

	public void setSubject(CourseSubject subject) {
		this.subject = subject;
	}

	@JsonBackReference("studentGroups")
	public Set<StudentGroup> getStudentGroups() {
		return Collections.unmodifiableSet(studentGroups);
	}

	@JsonBackReference("teacherGroups")
	public Set<TeacherGroup> getTeacherGroups() {
		return Collections.unmodifiableSet(teacherGroups);
	}

	public Set<Session> getSessions() {
		return Collections.unmodifiableSet(sessions);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		Group other = (Group) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Group [code=" + code + ", type=" + type + ", subject=" + subject + "]";
	}

	// -------------- RELATIONS --------------

	void _setCourseSubject(CourseSubject courseSubject) {
		this.subject = courseSubject;
	}

	Set<StudentGroup> _getStudentGroups() {
		return studentGroups;
	}

	Set<TeacherGroup> _getTeacherGroups() {
		return teacherGroups;
	}

	public void addSession(Session s) {
		s._setGroup(this);
		sessions.add(s);
	}

	public void removeSession(Session s) {
		sessions.remove(s);
		s._setGroup(null);
	}
}
