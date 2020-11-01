package com.robert.java.unioviscope.business.students.importer;

import com.robert.java.unioviscope.business.common.importer.Importer;
import com.robert.java.unioviscope.model.Student;

/**
 * Interfaz que extiende la interfaz Importer y define las operaciones de
 * importación específicas para la entidad "Student" (estudiante). También
 * define la cabecera de los ficheros de importación.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.Importer
 */
public interface StudentsImporter extends Importer<Student> {

	static final String USERNAME = "user-name";
	static final String PASSWORD = "password";
	static final String DNI = "dni";
	static final String FIRST_NAME = "first-name";
	static final String LAST_NAME = "last-name";
	
	static final String[] HEADERS = { USERNAME, PASSWORD, DNI, FIRST_NAME, LAST_NAME };
}
