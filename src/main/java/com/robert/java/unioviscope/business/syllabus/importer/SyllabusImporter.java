package com.robert.java.unioviscope.business.syllabus.importer;

import com.robert.java.unioviscope.business.common.importer.Importer;
import com.robert.java.unioviscope.model.Syllabus;

/**
 * Interfaz que extiende la interfaz Importer y define las operaciones de
 * importación específicas para la entidad "Syllabus" (plan de estudio). También
 * define la cabecera de los ficheros de importación.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.Importer
 */
public interface SyllabusImporter extends Importer<Syllabus> {

	static final String CODE = "code";
	static final String DENOMINATION = "denomination";
	static final String STATE = "state";
	static final String RESPONSIBLE_CENTER = "responsibleCenter";
	static final String IMPLANTATION_YEAR = "implantationYear";
	static final String TYPE = "type";
	static final String NUM_COURSES = "numCourses";
	static final String NUM_ECTS = "numECTS";

	static final String[] HEADERS = { CODE, DENOMINATION, STATE, RESPONSIBLE_CENTER, IMPLANTATION_YEAR, TYPE,
			NUM_COURSES, NUM_ECTS };
}
