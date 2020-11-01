package com.robert.java.unioviscope.business.subject.importer;

import com.robert.java.unioviscope.business.common.importer.Importer;
import com.robert.java.unioviscope.model.Subject;

/**
 * Interfaz que extiende la interfaz Importer y define las operaciones de
 * importación específicas para la entidad "Subject" (asignatura). También
 * define la cabecera de los ficheros de importación.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.Importer
 */
public interface SubjectImporter extends Importer<Subject> {

	static final String CODE = "code";
	static final String DENOMINATION = "denomination";
	static final String COURSE = "course";
	static final String TEMPORALIY = "temporality";
	static final String TYPE = "type";
	static final String CREDITS = "credits";
	static final String SYLLABUS_CODE = "syllabus-code";

	static final String[] HEADERS = { CODE, DENOMINATION, COURSE, TEMPORALIY, TYPE, CREDITS, SYLLABUS_CODE };
}