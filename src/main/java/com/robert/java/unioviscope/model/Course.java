package com.robert.java.unioviscope.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TCourse")
public class Course extends GenericEntity<Long> {

	private static final long serialVersionUID = 8314107893393918656L;

	@NotNull(message = "{course.year.null}")
	@Min(value = 1, message = "{course.year.min}")
	@Column(unique = true)
	private Long year;

	@NotNull(message = "{course.courseSubjects.null}")
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	private Set<CourseSubject> courseSubjects = new HashSet<>();

	Course() {

	}

	public Course(Long year) {
		this.year = year;
	}

	public Long getYear() {
		return year;
	}

	@JsonIgnore
	public Set<CourseSubject> getCourseSubjects() {
		return courseSubjects;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((year == null) ? 0 : year.hashCode());
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
		Course other = (Course) obj;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Course [year=" + year + "]";
	}

	// -------------- RELATIONS --------------

	Set<CourseSubject> _getCourseSubjects() {
		return courseSubjects;
	}
}
