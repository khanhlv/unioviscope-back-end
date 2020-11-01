package com.robert.java.unioviscope.business.teacher.exporter;

import java.io.IOException;
import java.io.OutputStream;

import com.robert.java.unioviscope.model.dto.AttendanceOptions;

/**
 * Interfaz que define las operaciones de exportación de asistencias.
 * 
 * @author Robert Ene
 */
public interface AttendanceExporter {

	static final String SESSION_DATE_FORMAT = "dd/mm/yyyy";

	/**
	 * Método que exporta las asistencias asociadas a unos determinados
	 * parámetros (formato del fichero de exportación, el id del docente, el id
	 * de la asignatura, el id del grupo y el id de la sesión).
	 * 
	 * @param attendanceOptions
	 *            los parámetros de las asistencias.
	 * @return el fichero que representa las asistencias solicitadas.
	 */
	OutputStream exportFile(AttendanceOptions attendanceOptions) throws IOException;
}
