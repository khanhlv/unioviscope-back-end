package com.robert.java.unioviscope.business.teachers.importer.impl;

import java.io.IOException;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.common.importer.AbstractImporter;
import com.robert.java.unioviscope.business.teachers.importer.TeachersImporter;
import com.robert.java.unioviscope.model.Teacher;
import com.robert.java.unioviscope.model.types.Role;
import com.robert.java.unioviscope.persistence.GenericRepository;
import com.robert.java.unioviscope.persistence.TeacherRepository;
import com.robert.java.unioviscope.persistence.UserRepository;

/**
 * Clase que extiende la clase AbstractImporter e implementa la interfaz
 * TeachersImporter.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.AbstractImporter
 * @see com.robert.java.unioviscope.business.teachers.importer.TeachersImporter
 */
@Component
public class CsvTeachersImporter extends AbstractImporter<Teacher, Long> implements TeachersImporter {

	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public void processRecord(CSVRecord record, CSVPrinter printer) throws IOException {
		Teacher teacher = new Teacher(validateUserName(record.get(USERNAME), printer));
		teacher.setPassword(validatePassword(record.get(PASSWORD), printer));
		teacher.setRole(Role.TEACHER);
		teacher.setDni(validateDni(record.get(DNI), printer));
		teacher.setFirstName(validateFirstName(record.get(FIRST_NAME), printer));
		teacher.setLastName(validateLastName(record.get(LAST_NAME), printer));

		addEntity(teacher);
	}

	@Override
	public String[] getHeaders() {
		return HEADERS;
	}

	@Override
	public GenericRepository<Teacher, Long> getRepository() {
		return teacherRepository;
	}

	private String validateUserName(String userName, CSVPrinter printer) throws IOException {
		if (userName == null || userName.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("teachers.import.userName.file.required"));
			return null;
		}

		for (Teacher t : getEntities()) {
			if (t.getUserName() != null && t.getUserName().equals(userName)) {
				setImportError(true);
				printer.printComment(getMessageResolver().getMessage("teachers.import.userName.file.unique", userName));
				return null;
			}
		}

		if (userRepository.findByUserName(userName) != null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("teachers.import.userName.db.unique", userName));
			return null;
		}

		return userName;
	}

	private String validatePassword(String password, CSVPrinter printer) throws IOException {
		if (password == null || password.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("teachers.import.password.file.required"));
			return null;
		}

		return password;
	}

	private String validateDni(String dni, CSVPrinter printer) throws IOException {
		if (dni == null || dni.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("teachers.import.dni.file.required"));
			return null;
		}

		return dni;
	}

	private String validateFirstName(String firstName, CSVPrinter printer) throws IOException {
		if (firstName == null || firstName.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("teachers.import.firstName.file.required"));
			return null;
		}

		return firstName;
	}

	private String validateLastName(String lastName, CSVPrinter printer) throws IOException {
		if (lastName == null || lastName.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("teachers.import.lastName.file.required"));
			return null;
		}

		return lastName;
	}
}
