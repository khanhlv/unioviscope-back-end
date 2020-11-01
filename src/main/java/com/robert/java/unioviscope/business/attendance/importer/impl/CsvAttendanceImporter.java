package com.robert.java.unioviscope.business.attendance.importer.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.attendance.importer.AttendanceImporter;
import com.robert.java.unioviscope.business.common.importer.AbstractImporter;
import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.Student;
import com.robert.java.unioviscope.model.types.key.AttendanceKey;
import com.robert.java.unioviscope.persistence.AttendanceRepository;
import com.robert.java.unioviscope.persistence.GenericRepository;
import com.robert.java.unioviscope.persistence.SessionRepository;
import com.robert.java.unioviscope.persistence.StudentRepository;

/**
 * Clase que extiende la clase AbstractImporter e implementa la interfaz
 * AttendanceImporter.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.AbstractImporter
 * @see com.robert.java.unioviscope.business.attendance.importer.AttendanceImporter
 */
@Component
public class CsvAttendanceImporter extends AbstractImporter<Attendance, AttendanceKey> implements AttendanceImporter {

	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private AttendanceRepository attendanceRepository;

	private DateFormat dateFormat;

	public CsvAttendanceImporter() {
		super();
		dateFormat = new SimpleDateFormat(DATE_FORMAT);
	}

	@Override
	public void processRecord(CSVRecord record, CSVPrinter printer) throws IOException {
		Date importDate = new Date();
		Student student = validateStudent(record.get(USERNAME), printer);
		Session session = validateSession(record.get(SESSION_ID), printer);
		Date sessionDate = session != null ? session.getStart() : null;
		Attendance attendance = validateAttendance(new Attendance(student, session, sessionDate), printer);

		if (attendance != null) {
			attendance.setComment(
					getMessageResolver().getMessage("attendance.import.comment", dateFormat.format(importDate)));
			addEntity(attendance);
		}
	}

	@Override
	public String[] getHeaders() {
		return HEADERS;
	}

	@Override
	public GenericRepository<Attendance, AttendanceKey> getRepository() {
		return attendanceRepository;
	}

	private Student validateStudent(String userName, CSVPrinter printer) throws IOException {
		if (userName == null || userName.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("attendance.import.userName.file.required"));
			return null;
		}

		Student student = studentRepository.findByUserName(userName);

		if (student == null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("attendance.import.userName.db.invalid", userName));
			return null;
		}

		return student;
	}

	private Session validateSession(String sessionId, CSVPrinter printer) throws IOException {
		if (sessionId == null || sessionId.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("attendance.import.sessionId.file.required"));
			return null;
		}

		Long id = null;

		try {
			id = Long.parseLong(sessionId);
		} catch (NumberFormatException nfe) {
			setImportError(true);
			printer.printComment(
					getMessageResolver().getMessage("attendance.import.sessionId.file.invalid", sessionId));
			return null;
		}

		Session session = sessionRepository.findOne(id);

		if (session == null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("attendance.import.sessionId.db.invalid", sessionId));
			return null;
		}

		if (session.getStart().after(new Date())) {
			setImportError(true);
			printer.printComment(
					getMessageResolver().getMessage("attendance.import.session.start.after.now.file.invalid", sessionId,
							dateFormat.format(session.getStart()), dateFormat.format(session.getEnd())));
			return null;
		}

		return session;
	}

	private Attendance validateAttendance(Attendance attendance, CSVPrinter printer) throws IOException {
		Student student = attendance.getStudent();
		Session session = attendance.getSession();

		if (student == null || session == null)
			return null;

		for (Attendance a : getEntities()) {
			if (a.getSession().equals(session) && a.getStudent().equals(student)) {
				setImportError(true);
				printer.printComment(getMessageResolver().getMessage("attendance.import.attendance.file.repeated",
						student.getUserName(), session.getId()));
				return null;
			}
		}

		if (attendanceRepository.findByStudentIdAndSessionId(student.getId(), session.getId()) != null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("attendance.import.attendance.db.repeated",
					student.getUserName(), session.getId()));
			return null;
		}

		return attendance;
	}
}
