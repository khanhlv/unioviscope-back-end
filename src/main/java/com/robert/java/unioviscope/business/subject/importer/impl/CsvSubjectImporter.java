package com.robert.java.unioviscope.business.subject.importer.impl;

import java.io.IOException;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.common.importer.AbstractImporter;
import com.robert.java.unioviscope.business.subject.importer.SubjectImporter;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.Syllabus;
import com.robert.java.unioviscope.model.types.SubjectType;
import com.robert.java.unioviscope.model.types.TemporalitySubjectType;
import com.robert.java.unioviscope.persistence.GenericRepository;
import com.robert.java.unioviscope.persistence.SubjectRepository;
import com.robert.java.unioviscope.persistence.SyllabusRepository;

/**
 * Clase que extiende la clase AbstractImporter e implementa la interfaz
 * SubjectImporter.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.AbstractImporter
 * @see com.robert.java.unioviscope.business.subject.importer.SubjectImporter
 */
@Component
public class CsvSubjectImporter extends AbstractImporter<Subject, Long> implements SubjectImporter {

	@Autowired
	private SyllabusRepository syllabusRepository;
	@Autowired
	private SubjectRepository subjectRepository;

	@Override
	public void processRecord(CSVRecord record, CSVPrinter printer) throws IOException {
		Subject subject = new Subject(validateCode(record.get(CODE), printer));
		subject.setDenomination(validateDenomination(record.get(DENOMINATION), printer));
		subject.setCourse(validateCourse(record.get(COURSE), printer));
		subject.setTemporality(validateTemporality(record.get(TEMPORALIY), printer));
		subject.setType(validateType(record.get(TYPE), printer));
		subject.setCredits(validateCredits(record.get(CREDITS), printer));
		subject.setSyllabus(validateSyllabus(record.get(SYLLABUS_CODE), printer));

		addEntity(subject);
	}

	@Override
	public String[] getHeaders() {
		return HEADERS;
	}

	@Override
	public GenericRepository<Subject, Long> getRepository() {
		return subjectRepository;
	}

	private String validateCode(String code, CSVPrinter printer) throws IOException {
		if (code == null || code.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("subject.import.code.file.required"));
			return null;
		}

		for (Subject s : getEntities()) {
			if (s.getCode() != null && s.getCode().equals(code)) {
				setImportError(true);
				printer.printComment(getMessageResolver().getMessage("subject.import.code.file.unique", code));
				return null;
			}
		}

		if (subjectRepository.findByCode(code) != null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("subject.import.code.db.unique", code));
			return null;
		}

		return code;
	}

	private String validateDenomination(String denomination, CSVPrinter printer) throws IOException {
		if (denomination == null || denomination.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("subject.import.denomination.file.required"));
			return null;
		}

		return denomination;
	}

	private Integer validateCourse(String numCourse, CSVPrinter printer) throws IOException {
		if (numCourse == null || numCourse.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("subject.import.numCourse.file.required"));
			return null;
		}

		try {
			Integer course = Integer.parseInt(numCourse);

			if (course <= 0)
				throw new NumberFormatException();

			return course;
		} catch (NumberFormatException nfe) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("subject.import.numCourse.file.invalid", numCourse));
			return null;
		}
	}

	private TemporalitySubjectType validateTemporality(String temporality, CSVPrinter printer) throws IOException {
		if (temporality == null || temporality.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("subject.import.temporality.file.required"));
			return null;
		}

		TemporalitySubjectType temporalitySubjectType = TemporalitySubjectType.fromString(temporality);

		if (temporalitySubjectType == null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("subject.import.temporality.file.invalid", temporality,
					TemporalitySubjectType.stringValues()));
			return null;
		}

		return temporalitySubjectType;
	}

	private SubjectType validateType(String type, CSVPrinter printer) throws IOException {
		if (type == null || type.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("subject.import.type.file.required"));
			return null;
		}

		SubjectType subjectType = SubjectType.fromString(type);

		if (subjectType == null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("subject.import.type.file.invalid", type,
					SubjectType.stringValues()));
			return null;
		}

		return subjectType;
	}

	private Double validateCredits(String numCredits, CSVPrinter printer) throws IOException {
		if (numCredits == null || numCredits.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("subject.import.numCredits.file.required"));
			return null;
		}

		try {
			Double credits = Double.parseDouble(numCredits);

			if (credits <= 0)
				throw new NumberFormatException();

			return credits;
		} catch (NumberFormatException nfe) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("subject.import.numCredits.file.invalid", numCredits));
			return null;
		}
	}

	private Syllabus validateSyllabus(String syllabusCode, CSVPrinter printer) throws IOException {
		if (syllabusCode == null || syllabusCode.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("subject.import.syllabusCode.file.required"));
			return null;
		}

		Syllabus syllabus = syllabusRepository.findByCode(syllabusCode);
		if (syllabus == null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("subject.import.syllabusCode.db.invalid", syllabusCode));
		}

		return syllabus;
	}
}
