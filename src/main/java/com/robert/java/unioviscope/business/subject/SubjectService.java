package com.robert.java.unioviscope.business.subject;

import java.io.InputStream;
import java.io.OutputStream;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz que extiende la interfaz GenericService y define los servicios
 * específicos para la entidad "Subject" (asignatura).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.GenericService
 */
public interface SubjectService extends GenericService<Subject, Long> {

	/**
	 * Método que importa las asignaturas de un fichero de importación y que, si
	 * se da el caso, devuelve otro fichero que contiene los errores de formato
	 * detectados en dicho fichero de importación.
	 * 
	 * @param file
	 *            el fichero que se importa.
	 * @return el fichero que contiene los errores de formato detectados en el
	 *         fichero de importación.
	 */
	OutputStream importSubjects(InputStream file) throws BusinessException;
}
