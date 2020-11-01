package com.robert.java.unioviscope.business.group.importer.impl;

import java.io.IOException;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.common.importer.AbstractImporter;
import com.robert.java.unioviscope.business.group.importer.GroupImporter;
import com.robert.java.unioviscope.model.CourseSubject;
import com.robert.java.unioviscope.model.Group;
import com.robert.java.unioviscope.model.types.GroupType;
import com.robert.java.unioviscope.persistence.CourseSubjectRepository;
import com.robert.java.unioviscope.persistence.GenericRepository;
import com.robert.java.unioviscope.persistence.GroupRepository;

/**
 * Clase que extiende la clase AbstractImporter e implementa la interfaz
 * GroupImporter.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.AbstractImporter
 * @see com.robert.java.unioviscope.business.group.importer.GroupImporter
 */
@Component
public class CsvGroupImporter extends AbstractImporter<Group, Long> implements GroupImporter {

	@Autowired
	private CourseSubjectRepository courseSubjectRepository;
	@Autowired
	private GroupRepository groupRepository;

	@Override
	public void processRecord(CSVRecord record, CSVPrinter printer) throws IOException {
		Group group = new Group(validateCode(record.get(CODE), printer),
				validateSubjectAndCourse(record.get(SUBJECT_CODE), record.get(COURSE_START_YEAR), printer));
		group.setType(validateType(record.get(TYPE), printer));

		addEntity(group);
	}

	@Override
	public String[] getHeaders() {
		return HEADERS;
	}

	@Override
	public GenericRepository<Group, Long> getRepository() {
		return groupRepository;
	}

	private String validateCode(String code, CSVPrinter printer) throws IOException {
		if (code == null || code.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("group.import.code.file.required"));
			return null;
		}

		return code;
	}

	private CourseSubject validateSubjectAndCourse(String subjectCode, String courseYear, CSVPrinter printer)
			throws IOException {
		if (subjectCode == null || subjectCode.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("group.import.subjectCode.file.required"));
			return null;
		}

		if (courseYear == null || courseYear.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("group.import.courseYear.file.required"));
			return null;
		}

		Long year = null;

		try {
			year = Long.parseLong(courseYear);

			if (year <= 0)
				throw new NumberFormatException();

		} catch (NumberFormatException nfe) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("group.import.courseYear.file.invalid", courseYear));
			return null;
		}

		CourseSubject subject = courseSubjectRepository.findBySubjectCodeAndCourseYear(subjectCode, year);

		if (subject == null) {
			setImportError(true);
			printer.printComment(
					getMessageResolver().getMessage("group.import.courseSubject.db.invalid", subjectCode, courseYear));
			return null;
		}

		return subject;
	}

	private GroupType validateType(String type, CSVPrinter printer) throws IOException {
		if (type == null || type.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("group.import.type.file.required"));
			return null;
		}

		GroupType groupType = GroupType.fromString(type);

		if (groupType == null) {
			setImportError(true);
			printer.printComment(
					getMessageResolver().getMessage("group.import.type.file.invalid", type, GroupType.stringValues()));
			return null;
		}

		return groupType;
	}
}
