package com.robert.java.unioviscope.business.syllabus.importer.impl;

import java.io.IOException;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.common.importer.AbstractImporter;
import com.robert.java.unioviscope.business.syllabus.importer.SyllabusImporter;
import com.robert.java.unioviscope.model.Syllabus;
import com.robert.java.unioviscope.model.types.SyllabusState;
import com.robert.java.unioviscope.model.types.SyllabusTeachingType;
import com.robert.java.unioviscope.persistence.GenericRepository;
import com.robert.java.unioviscope.persistence.SyllabusRepository;

/**
 * Clase que extiende la clase AbstractImporter e implementa la interfaz
 * SyllabusImporter.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.AbstractImporter
 * @see com.robert.java.unioviscope.business.syllabus.importer.SyllabusImporter
 */
@Component
public class CsvSyllabusImporter extends AbstractImporter<Syllabus, Long> implements SyllabusImporter {

	@Autowired
	private SyllabusRepository syllabusRepository;

	@Override
	public void processRecord(CSVRecord record, CSVPrinter printer) throws IOException {
		Syllabus syllabus = new Syllabus(validateCode(record.get(CODE), printer));
		syllabus.setDenomination(validateDenomination(record.get(DENOMINATION), printer));
		syllabus.setState(validateState(record.get(STATE), printer));
		syllabus.setResponsibleCenter(validateResponsibleCenter(record.get(RESPONSIBLE_CENTER), printer));
		syllabus.setImplantationYear(validateImplantationYear(record.get(IMPLANTATION_YEAR), printer));
		syllabus.setType(validateType(record.get(TYPE), printer));
		syllabus.setNumCourses(validateNumCourses(record.get(NUM_COURSES), printer));
		syllabus.setNumECTS(validateNumECTS(record.get(NUM_ECTS), printer));

		addEntity(syllabus);
	}

	@Override
	public String[] getHeaders() {
		return HEADERS;
	}

	@Override
	public GenericRepository<Syllabus, Long> getRepository() {
		return syllabusRepository;
	}

	private String validateCode(String code, CSVPrinter printer) throws IOException {
		if (code == null || code.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("syllabus.import.code.file.required"));
			return null;
		}

		for (Syllabus s : getEntities()) {
			if (s.getCode() != null && s.getCode().equals(code)) {
				setImportError(true);
				printer.printComment(getMessageResolver().getMessage("syllabus.import.code.file.unique", code));
				return null;
			}
		}

		if (syllabusRepository.findByCode(code) != null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("syllabus.import.code.db.unique", code));
			return null;
		}

		return code;
	}

	private String validateDenomination(String denomination, CSVPrinter printer) throws IOException {
		if (denomination == null || denomination.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("syllabus.import.denomination.file.required"));
			return null;
		}

		return denomination;
	}

	private SyllabusState validateState(String state, CSVPrinter printer) throws IOException {
		if (state == null || state.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("syllabus.import.state.file.required"));
			return null;
		}

		SyllabusState syllabusState = SyllabusState.fromString(state);

		if (syllabusState == null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("syllabus.import.state.file.invalid", state,
					SyllabusState.stringValues()));
			return null;
		}

		return syllabusState;
	}

	private String validateResponsibleCenter(String responsibleCenter, CSVPrinter printer) throws IOException {
		if (responsibleCenter == null || responsibleCenter.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("syllabus.import.responsibleCenter.file.required"));
			return null;
		}

		return responsibleCenter;
	}

	private Long validateImplantationYear(String implantationYear, CSVPrinter printer) throws IOException {
		if (implantationYear == null || implantationYear.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("syllabus.import.implantationYear.file.required"));
			return null;
		}

		try {
			Long year = Long.parseLong(implantationYear);

			if (year <= 0)
				throw new NumberFormatException();

			return year;
		} catch (NumberFormatException nfe) {
			setImportError(true);
			printer.printComment(
					getMessageResolver().getMessage("syllabus.import.implantationYear.file.invalid", implantationYear));
			return null;
		}
	}

	private SyllabusTeachingType validateType(String type, CSVPrinter printer) throws IOException {
		if (type == null || type.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("syllabus.import.type.file.required"));
			return null;
		}

		SyllabusTeachingType syllabusTeachingType = SyllabusTeachingType.fromString(type);

		if (syllabusTeachingType == null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("syllabus.import.type.file.invalid", type,
					SyllabusTeachingType.stringValues()));
			return null;
		}

		return syllabusTeachingType;
	}

	private Integer validateNumCourses(String numCourses, CSVPrinter printer) throws IOException {
		if (numCourses == null || numCourses.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("syllabus.import.numCourses.file.required"));
			return null;
		}

		try {
			Integer courses = Integer.parseInt(numCourses);

			if (courses <= 0)
				throw new NumberFormatException();

			return courses;
		} catch (NumberFormatException nfe) {
			setImportError(true);
			printer.printComment(
					getMessageResolver().getMessage("syllabus.import.numCourses.file.invalid", numCourses));
			return null;
		}
	}

	private Double validateNumECTS(String numECTS, CSVPrinter printer) throws IOException {
		if (numECTS == null || numECTS.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("syllabus.import.numECTS.file.required"));
			return null;
		}

		try {
			Double ects = Double.parseDouble(numECTS);

			if (ects <= 0)
				throw new NumberFormatException();

			return ects;
		} catch (NumberFormatException nfe) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("syllabus.import.numECTS.file.invalid", numECTS));
			return null;
		}
	}
}
