package com.robert.java.unioviscope.business.group.importer;

import com.robert.java.unioviscope.business.common.importer.Importer;
import com.robert.java.unioviscope.model.Group;

/**
 * Interfaz que extiende la interfaz Importer y define las operaciones de
 * importación específicas para la entidad "Group" (grupo). También
 * define la cabecera de los ficheros de importación.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.Importer
 */
public interface GroupImporter extends Importer<Group> {

	static final String CODE = "code";
	static final String TYPE = "type";
	static final String SUBJECT_CODE = "subject-code";
	static final String COURSE_START_YEAR = "course-start-year";
	
	static final String[] HEADERS = { CODE, TYPE, SUBJECT_CODE, COURSE_START_YEAR };
}
