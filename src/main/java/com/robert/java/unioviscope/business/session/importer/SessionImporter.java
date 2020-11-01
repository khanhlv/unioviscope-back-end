package com.robert.java.unioviscope.business.session.importer;

import com.robert.java.unioviscope.business.common.importer.Importer;
import com.robert.java.unioviscope.model.Session;

/**
 * Interfaz que extiende la interfaz Importer y define las operaciones de
 * importación específicas para la entidad "Session" (sesión). También define la
 * cabecera de los ficheros de importación.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.Importer
 */
public interface SessionImporter extends Importer<Session> {

	static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";

	static final String START = "start(" + DATE_FORMAT + ")";
	static final String END = "end(" + DATE_FORMAT + ")";
	static final String LOCATION = "location";
	static final String DESCRIPTION = "description";
	static final String GROUP_ID = "group-id";

	static final String[] HEADERS = { START, END, LOCATION, DESCRIPTION, GROUP_ID };
}
