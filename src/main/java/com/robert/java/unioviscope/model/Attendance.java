package com.robert.java.unioviscope.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.robert.java.unioviscope.model.types.key.AttendanceKey;

@IdClass(AttendanceKey.class)
@Entity
@Table(name = "TAttendance")
public class Attendance implements Serializable {

	private static final long serialVersionUID = -4953948577789912093L;

	@NotNull(message = "{attendance.confirmed.null}")
	private Boolean confirmed;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@NotNull(message = "{attendance.comment.null}")
	private String comment;

	@NotNull(message = "{attendance.faceRecognitionOff.null}")
	private Boolean faceRecognitionOff;

	@NotNull(message = "{attendance.student.null}")
	@Id
	@ManyToOne
	private Student student;

	@NotNull(message = "{attendance.session.null}")
	@Id
	@ManyToOne
	private Session session;

	Attendance() {

	}

	public Attendance(Student student, Session session, Date date) {
		this.student = student;
		this.session = session;
		this.date = date;
		this.confirmed = true;
		this.faceRecognitionOff = false;
		this.comment = "";

		if (student != null)
			student._getAttendances().add(this);
		if (session != null)
			session._getAttendances().add(this);
	}

	public Boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Date getDate() {
		return date != null ? (Date) date.clone() : null;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean isFaceRecognitionOff() {
		return faceRecognitionOff;
	}

	public void setFaceRecognitionOff(Boolean faceRecognitionOff) {
		this.faceRecognitionOff = faceRecognitionOff;
	}

	public Student getStudent() {
		return student;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}

	public Session getSession() {
		return session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((session == null) ? 0 : session.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
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
		Attendance other = (Attendance) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (session == null) {
			if (other.session != null)
				return false;
		} else if (!session.equals(other.session))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Attendance [date=" + date + ", student=" + student + ", session=" + session + "]";
	}

	// -------------- RELATIONS --------------

	public void unlink() {
		student._getAttendances().remove(this);
		session._getAttendances().remove(this);

		this.student = null;
		this.session = null;
	}
}
