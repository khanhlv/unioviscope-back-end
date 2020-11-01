package com.robert.java.unioviscope.business.attendance;

import java.io.InputStream;
import java.io.OutputStream;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.types.key.AttendanceKey;

/**
 * Interfaz que extiende la interfaz GenericService y define los servicios
 * específicos para la entidad "Attendance" (asistencia).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.GenericService
 */
public interface AttendanceService extends GenericService<Attendance, AttendanceKey> {

	/**
	 * Método que importa las asistencias de un fichero de importación y que, si
	 * se da el caso, devuelve otro fichero que contiene los errores de formato
	 * detectados en dicho fichero de importación.
	 * 
	 * @param file
	 *            el fichero que se importa.
	 * @return el fichero que contiene los errores de formato detectados en el
	 *         fichero de importación.
	 */
	OutputStream importAttendances(InputStream file) throws BusinessException;
}
