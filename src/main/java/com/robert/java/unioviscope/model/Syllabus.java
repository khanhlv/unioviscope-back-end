package com.robert.java.unioviscope.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robert.java.unioviscope.model.types.SyllabusState;
import com.robert.java.unioviscope.model.types.SyllabusTeachingType;

@Entity
@Table(name = "TSyllabus")
public class Syllabus extends GenericEntity<Long> {

	private static final long serialVersionUID = 8216054270053087373L;

	@NotNull(message = "{syllabus.code.null}")
	@NotEmpty(message = "{syllabus.code.empty}")
	@Column(unique = true)
	private String code;

	@NotNull(message = "{syllabus.denomination.null}")
	@NotEmpty(message = "{syllabus.denomination.empty}")
	private String denomination;

	@Valid
	@NotNull(message = "{syllabus.state.null}")
	@Enumerated(EnumType.STRING)
	private SyllabusState state;

	@NotNull(message = "{syllabus.responsibleCenter.null}")
	@NotEmpty(message = "{syllabus.responsibleCenter.empty}")
	private String responsibleCenter;

	@NotNull(message = "{syllabus.implantationYear.null}")
	@Min(value = 1, message = "{syllabus.implantationYear.min}")
	private Long implantationYear;

	@Valid
	@NotNull(message = "{syllabus.type.null}")
	@Enumerated(EnumType.STRING)
	private SyllabusTeachingType type;

	@NotNull(message = "{syllabus.numCourses.null}")
	@Min(value = 1, message = "{syllabus.numCourses.min}")
	private Integer numCourses;

	@NotNull(message = "{syllabus.numECTS.null}")
	@Min(value = 0, message = "{syllabus.numECTS.min}")
	private Double numECTS;

	@NotNull(message = "{syllabus.subjects.null}")
	@OneToMany(mappedBy = "syllabus", fetch = FetchType.LAZY)
	private Set<Subject> subjects = new HashSet<>();

	Syllabus() {

	}

	public Syllabus(String code) {
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

	public SyllabusState getState() {
		return state;
	}

	public void setState(SyllabusState state) {
		this.state = state;
	}

	public String getResponsibleCenter() {
		return responsibleCenter;
	}

	public void setResponsibleCenter(String responsibleCenter) {
		this.responsibleCenter = responsibleCenter;
	}

	public Long getImplantationYear() {
		return implantationYear;
	}

	public void setImplantationYear(Long implantationYear) {
		this.implantationYear = implantationYear;
	}

	public SyllabusTeachingType getType() {
		return type;
	}

	public void setType(SyllabusTeachingType type) {
		this.type = type;
	}

	public Integer getNumCourses() {
		return numCourses;
	}

	public void setNumCourses(Integer numCourses) {
		this.numCourses = numCourses;
	}

	public Double getNumECTS() {
		return numECTS;
	}

	public void setNumECTS(Double numECTS) {
		this.numECTS = numECTS;
	}

	@JsonIgnore
	public Set<Subject> getSubjects() {
		return Collections.unmodifiableSet(subjects);
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
		Syllabus other = (Syllabus) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Syllabus [code=" + code + ", denomination=" + denomination + "]";
	}

	// -------------- RELATIONS --------------

	public void addSubject(Subject s) {
		s._setSyllabus(this);
		subjects.add(s);
	}

	public void removeSubject(Subject s) {
		subjects.remove(s);
		s._setSyllabus(null);
	}
}
