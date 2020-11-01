package com.robert.java.unioviscope.business.session.importer.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.common.importer.AbstractImporter;
import com.robert.java.unioviscope.business.session.importer.SessionImporter;
import com.robert.java.unioviscope.business.util.DateUtil;
import com.robert.java.unioviscope.model.Group;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.persistence.GenericRepository;
import com.robert.java.unioviscope.persistence.GroupRepository;
import com.robert.java.unioviscope.persistence.SessionRepository;

/**
 * Clase que extiende la clase AbstractImporter e implementa la interfaz
 * SessionImporter.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.AbstractImporter
 * @see com.robert.java.unioviscope.business.session.importer.SessionImporter
 */
@Component
public class CsvSessionImporter extends AbstractImporter<Session, Long> implements SessionImporter {

	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private SessionRepository sessionRepository;

	private DateFormat dateFormat;

	public CsvSessionImporter() {
		super();
		dateFormat = new SimpleDateFormat(DATE_FORMAT);
	}

	@Override
	public void processRecord(CSVRecord record, CSVPrinter printer) throws IOException {
		Session session = new Session(validateStart(record.get(START), printer),
				validateLocation(record.get(LOCATION), printer));
		session.setEnd(validateEnd(record.get(END), session.getStart(), printer));
		session.setDescription(validateDescription(record.get(DESCRIPTION), printer));
		session.setGroup(validateGroupAndSession(record.get(GROUP_ID), session, printer));

		addEntity(session);
	}

	@Override
	public String[] getHeaders() {
		return HEADERS;
	}

	@Override
	public GenericRepository<Session, Long> getRepository() {
		return sessionRepository;
	}

	private Date validateStart(String start, CSVPrinter printer) throws IOException {
		if (start == null || start.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("session.import.start.file.required"));
			return null;
		}

		try {
			Date startDate = dateFormat.parse(start);

			if (startDate == null)
				throw new ParseException("Format error", 0);

			return startDate;
		} catch (ParseException pe) {
			setImportError(true);
			printer.printComment(
					getMessageResolver().getMessage("session.import.start.file.invalid", start, DATE_FORMAT));
			return null;
		}
	}

	private String validateLocation(String location, CSVPrinter printer) throws IOException {
		if (location == null || location.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("session.import.location.file.required"));
			return null;
		}

		return location;
	}

	private Date validateEnd(String end, Date startDate, CSVPrinter printer) throws IOException {
		if (end == null || end.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("session.import.end.file.required"));
			return null;
		}

		Date endDate = null;

		try {
			endDate = dateFormat.parse(end);

			if (endDate == null)
				throw new ParseException("", 0);
		} catch (ParseException pe) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("session.import.end.file.invalid", end, DATE_FORMAT));
			return null;
		}

		if (startDate != null && (startDate.equals(endDate) || startDate.after(endDate))) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("session.import.end.before.file.invalid"));
			return null;
		}

		return endDate;
	}

	private String validateDescription(String description, CSVPrinter printer) throws IOException {
		if (description == null || description.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("session.import.description.file.required"));
			return null;
		}

		return description;
	}

	private Group validateGroupAndSession(String groupId, Session session, CSVPrinter printer) throws IOException {
		if (groupId == null || groupId.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("session.import.groupId.file.required"));
			return null;
		}

		Long id = null;

		try {
			id = Long.parseLong(groupId);
		} catch (NumberFormatException nfe) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("session.import.groupId.file.invalid", groupId));
			return null;
		}

		Group group = groupRepository.findOne(id);

		if (group == null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("session.import.groupId.db.invalid", groupId));
			return null;
		}

		if (!validateFileSessions(session, group, printer))
			return null;

		if (!validateDbSessions(session, group, printer))
			return null;

		return group;
	}

	private Boolean validateFileSessions(Session session, Group group, CSVPrinter printer) throws IOException {
		if (session.getLocation() != null) {
			for (Session s : getEntities()) {
				if (s.getLocation() != null && s.getLocation().equals(session.getLocation())
						&& DateUtil.overlaps(session.getStart(), session.getEnd(), s.getStart(), s.getEnd())) {
					setImportError(true);
					printer.printComment(getMessageResolver().getMessage("session.import.session.file.overlap",
							session.getLocation()));
					return false;
				}
			}
		}

		for (Session s : getEntities()) {
			if (DateUtil.overlaps(session.getStart(), session.getEnd(), s.getStart(), s.getEnd())) {
				setImportError(true);
				printer.printComment(
						getMessageResolver().getMessage("session.import.group.file.overlap", group.getId()));
				return false;
			}
		}

		return true;
	}

	private Boolean validateDbSessions(Session session, Group group, CSVPrinter printer) throws IOException {
		if (session.getLocation() != null) {
			List<Session> sessions = sessionRepository.findByLocation(session.getLocation());

			for (Session s : sessions) {
				if (s.getLocation() != null && s.getLocation().equals(session.getLocation())
						&& DateUtil.overlaps(session.getStart(), session.getEnd(), s.getStart(), s.getEnd())) {
					setImportError(true);
					printer.printComment(getMessageResolver().getMessage("session.import.session.db.overlap", s.getId(),
							session.getLocation()));
					return false;
				}
			}
		}

		List<Session> sessions = sessionRepository.findByGroupIdOrderByStartAsc(group.getId());

		for (Session s : sessions) {
			if (DateUtil.overlaps(session.getStart(), session.getEnd(), s.getStart(), s.getEnd())) {
				setImportError(true);
				printer.printComment(getMessageResolver().getMessage("session.import.group.db.overlap", group.getId()));
				return false;
			}
		}

		return true;
	}
}
