package com.robert.java.unioviscope.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robert.java.unioviscope.model.types.SubjectType;
import com.robert.java.unioviscope.model.types.TemporalitySubjectType;

@Entity
@Table(name = "TSubject")
public class Subject extends GenericEntity<Long> {

	private static final long serialVersionUID = 7344544060380624750L;
	
	@NotNull(message = "{subject.code.null}")
	@NotEmpty(message = "{subject.code.empty}")
	@Column(unique = true)
	private String code;

	@NotNull(message = "{subject.denomination.null}")
	@NotEmpty(message = "{subject.denomination.empty}")
	private String denomination;

	@NotNull(message = "{subject.course.null}")
	@Min(value = 1, message = "{subject.course.min}")
	private Integer course;

	@NotNull(message = "{subject.temporality.null}")
	@Enumerated(EnumType.STRING)
	private TemporalitySubjectType temporality;

	@NotNull(message = "{subject.type.null}")
	@Enumerated(EnumType.STRING)
	private SubjectType type;

	@NotNull(message = "{subject.credits.null}")
	@Min(value = 0, message = "{subject.credits.min}")
	private Double credits;

	@NotNull(message = "{subject.syllabus.null}")
	@ManyToOne
	private Syllabus syllabus;

	@NotNull(message = "{subject.courseSubjects.null}")
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	private Set<CourseSubject> courseSubjects = new HashSet<>();

	Subject() {

	}

	public Subject(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public Integer getCourse() {
		return course;
	}

	public void setCourse(Integer course) {
		this.course = course;
	}

	public TemporalitySubjectType getTemporality() {
		return temporality;
	}

	public void setTemporality(TemporalitySubjectType temporality) {
		this.temporality = temporality;
	}

	public SubjectType getType() {
		return type;
	}

	public void setType(SubjectType type) {
		this.type = type;
	}

	public Double getCredits() {
		return credits;
	}

	public void setCredits(Double credits) {
		this.credits = credits;
	}

	public Syllabus getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(Syllabus syllabus) {
		if (syllabus != null)
			syllabus.addSubject(this);
	}

	@JsonIgnore
	public Set<CourseSubject> getCourseSubjects() {
		return Collections.unmodifiableSet(courseSubjects);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		Subject other = (Subject) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subject [code=" + code + ", denomination=" + denomination + "]";
	}

	// -------------- RELATIONS --------------

	void _setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
	}

	Set<CourseSubject> _getCourseSubjects() {
		return courseSubjects;
	}
}
