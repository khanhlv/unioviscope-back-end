package com.robert.java.unioviscope.business.attendance.importer;

import com.robert.java.unioviscope.business.common.importer.Importer;
import com.robert.java.unioviscope.model.Attendance;

/**
 * Interfaz que extiende la interfaz Importer y define las operaciones de
 * importación específicas para la entidad "Attendance" (asistencia). También
 * define la cabecera de los ficheros de importación.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.Importer
 */
public interface AttendanceImporter extends Importer<Attendance> {

	static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";

	static final String USERNAME = "user-name";
	static final String SESSION_ID = "session-id";

	static final String[] HEADERS = { USERNAME, SESSION_ID };
}
