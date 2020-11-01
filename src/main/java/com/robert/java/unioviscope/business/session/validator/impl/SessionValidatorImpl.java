package com.robert.java.unioviscope.business.session.validator.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.session.validator.SessionValidator;
import com.robert.java.unioviscope.business.util.DateUtil;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.exception.SessionManagementException;
import com.robert.java.unioviscope.persistence.SessionRepository;

/**
 * Clase que implementa la interfaz SessionValidator.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.session.validator.SessionValidator
 */
@Component
public class SessionValidatorImpl implements SessionValidator {

	@Autowired
	private SessionRepository sessionRepository;

	public void findById(Long id) throws SessionManagementException {
		if (id == null)
			throw new SessionManagementException("id", "session.find.id.required");
	}

	public void save(Session session) throws SessionManagementException {
		if (session == null)
			throw new SessionManagementException("session", "session.save.session.required");

		session.setId(null);

		if (session.getStart() != null
				&& (session.getStart().equals(session.getEnd()) || session.getStart().after(session.getEnd()))) {
			throw new SessionManagementException("end", "session.end.before.invalid");
		}

		if (session.getLocation() != null) {
			List<Session> sessions = sessionRepository.findByLocation(session.getLocation());

			for (Session s : sessions) {
				if (s.getLocation() != null && s.getLocation().equals(session.getLocation())
						&& DateUtil.overlaps(session.getStart(), session.getEnd(), s.getStart(), s.getEnd())) {
					throw new SessionManagementException("location", "session.session.overlap");
				}
			}
		}

		if (session.getGroup() != null) {
			List<Session> sessions = sessionRepository.findByGroupIdOrderByStartAsc(session.getGroup().getId());

			for (Session s : sessions) {
				if (DateUtil.overlaps(session.getStart(), session.getEnd(), s.getStart(), s.getEnd())) {
					throw new SessionManagementException("start", "session.group.overlap");
				}
			}
		}
	}

	public void update(Session session) throws SessionManagementException {
		if (session == null)
			throw new SessionManagementException("session", "session.update.session.required");

		if (session.getStart() != null
				&& (session.getStart().equals(session.getEnd()) || session.getStart().after(session.getEnd()))) {
			throw new SessionManagementException("end", "session.end.before.invalid");
		}

		if (session.getLocation() != null) {
			List<Session> sessions = sessionRepository.findByLocation(session.getLocation());

			for (Session s : sessions) {
				if (s.getId() != session.getId() && s.getLocation() != null
						&& s.getLocation().equals(session.getLocation())
						&& DateUtil.overlaps(session.getStart(), session.getEnd(), s.getStart(), s.getEnd())) {
					throw new SessionManagementException("location", "session.session.overlap");
				}
			}
		}

		if (session.getGroup() != null) {
			List<Session> sessions = sessionRepository.findByGroupIdOrderByStartAsc(session.getGroup().getId());

			for (Session s : sessions) {
				if (s.getId() != session.getId()
						&& DateUtil.overlaps(session.getStart(), session.getEnd(), s.getStart(), s.getEnd())) {
					throw new SessionManagementException("start", "session.group.overlap");
				}
			}
		}
	}

	public void deleteById(Long id) throws SessionManagementException {
		if (id == null)
			throw new SessionManagementException("id", "session.delete.id.required");
	}
}