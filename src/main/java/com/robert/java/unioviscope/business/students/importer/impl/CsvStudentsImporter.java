package com.robert.java.unioviscope.business.students.importer.impl;

import java.io.IOException;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.common.importer.AbstractImporter;
import com.robert.java.unioviscope.business.students.importer.StudentsImporter;
import com.robert.java.unioviscope.model.Student;
import com.robert.java.unioviscope.model.types.Role;
import com.robert.java.unioviscope.persistence.GenericRepository;
import com.robert.java.unioviscope.persistence.StudentRepository;
import com.robert.java.unioviscope.persistence.UserRepository;

/**
 * Clase que extiende la clase AbstractImporter e implementa la interfaz
 * StudentsImporter.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.AbstractImporter
 * @see com.robert.java.unioviscope.business.students.importer.StudentsImporter
 */
@Component
public class CsvStudentsImporter extends AbstractImporter<Student, Long> implements StudentsImporter {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public void processRecord(CSVRecord record, CSVPrinter printer) throws IOException {
		Student student = new Student(validateUserName(record.get(USERNAME), printer));
		student.setPassword(validatePassword(record.get(PASSWORD), printer));
		student.setRole(Role.STUDENT);
		student.setDni(validateDni(record.get(DNI), printer));
		student.setFirstName(validateFirstName(record.get(FIRST_NAME), printer));
		student.setLastName(validateLastName(record.get(LAST_NAME), printer));

		addEntity(student);
	}

	@Override
	public String[] getHeaders() {
		return HEADERS;
	}

	@Override
	public GenericRepository<Student, Long> getRepository() {
		return studentRepository;
	}

	private String validateUserName(String userName, CSVPrinter printer) throws IOException {
		if (userName == null || userName.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("students.import.userName.file.required"));
			return null;
		}

		for (Student s : getEntities()) {
			if (s.getUserName() != null && s.getUserName().equals(userName)) {
				setImportError(true);
				printer.printComment(getMessageResolver().getMessage("students.import.userName.file.unique", userName));
				return null;
			}
		}

		if (userRepository.findByUserName(userName) != null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("students.import.userName.db.unique", userName));
			return null;
		}

		return userName;
	}

	private String validatePassword(String password, CSVPrinter printer) throws IOException {
		if (password == null || password.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("students.import.password.file.required"));
			return null;
		}

		return password;
	}

	private String validateDni(String dni, CSVPrinter printer) throws IOException {
		if (dni == null || dni.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("students.import.dni.file.required"));
			return null;
		}

		return dni;
	}

	private String validateFirstName(String firstName, CSVPrinter printer) throws IOException {
		if (firstName == null || firstName.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("students.import.firstName.file.required"));
			return null;
		}

		return firstName;
	}

	private String validateLastName(String lastName, CSVPrinter printer) throws IOException {
		if (lastName == null || lastName.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("students.import.lastName.file.required"));
			return null;
		}

		return lastName;
	}
}
