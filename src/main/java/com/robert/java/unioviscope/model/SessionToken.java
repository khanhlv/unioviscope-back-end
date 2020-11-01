package com.robert.java.unioviscope.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TSessionToken")
public class SessionToken extends GenericEntity<Long> {

	private static final long serialVersionUID = -6888316587835555405L;

	@NotNull(message = "{sessionToken.token.null}")
	@NotEmpty(message = "{sessionToken.token.empty}")
	@Column(unique = true)
	private String token;

	@NotNull(message = "{sessionToken.generated.null}")
	@Temporal(TemporalType.TIMESTAMP)
	private Date generated;

	@JsonIgnore
	@Valid
	@NotNull(message = "{sessionToken.session.null}")
	@ManyToOne
	private Session session;

	SessionToken() {

	}

	public SessionToken(String token) {
		this.token = token;
		this.generated = new Date();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getGenerated() {
		return generated != null ? (Date) generated.clone() : null;
	}

	public void setGenerated(Date generated) {
		this.generated = generated;
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
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		SessionToken other = (SessionToken) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Token [token=" + token + ", generated=" + generated + ", session=" + session + "]";
	}

	// -------------- RELATIONS --------------

	void _setSession(Session session) {
		this.session = session;
	}
}
