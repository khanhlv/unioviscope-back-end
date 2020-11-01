package com.robert.java.unioviscope.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TSession")
public class Session extends GenericEntity<Long> {

	private static final long serialVersionUID = 6328918576556232344L;

	@NotNull(message = "{session.start.null}")
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;

	@NotNull(message = "{session.end.null}")
	@Temporal(TemporalType.TIMESTAMP)
	private Date end;

	@NotNull(message = "{session.location.null}")
	@NotEmpty(message = "{session.location.empty}")
	private String location;
	
	@NotNull(message = "{session.description.null}")
	@NotEmpty(message = "{session.description.empty}")
	private String description;
 
	@NotNull(message = "{session.group.null}")
	@ManyToOne
	private Group group;

	@JsonIgnore
	@NotNull(message = "{session.attendances.null}")
	@OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
	private Set<Attendance> attendances = new HashSet<>();

	@JsonIgnore
	@NotNull(message = "{session.tokens.null}")
	@OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
	private Set<SessionToken> tokens = new HashSet<>();
	
	Session() {

	}

	public Session(Date start, String location) {
		this.start = start;
		this.location = location;
	}

	public Date getStart() {
		return start != null ? (Date) start.clone() : null;
	}
	
	public Date getEnd() {
		return end != null ? (Date) end.clone() : null;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getLocation() {
		return location;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		if (group != null)
			group.addSession(this);
	}

	public Set<Attendance> getAttendances() {
		return attendances;
	}
	
	public Set<SessionToken> getTokens() {
		return Collections.unmodifiableSet(tokens);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		Session other = (Session) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Session [start=" + start + ", location=" + location + "]";
	}

	// -------------- RELATIONS --------------

	void _setGroup(Group group) {
		this.group = group;
	}

	Set<Attendance> _getAttendances() {
		return attendances;
	}
	
	public void addToken(SessionToken t) {
		t._setSession(this);
		tokens.add(t);
	}

	public void removeToken(SessionToken t) {
		tokens.remove(t);
		t._setSession(null);
	}
}
