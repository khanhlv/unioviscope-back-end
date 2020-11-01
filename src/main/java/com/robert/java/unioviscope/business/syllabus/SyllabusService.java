package com.robert.java.unioviscope.business.syllabus;

import java.io.InputStream;
import java.io.OutputStream;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.model.Syllabus;
import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz que extiende la interfaz GenericService y define los servicios
 * específicos para la entidad "Syllabus" (plan de estudio).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.GenericService
 */
public interface SyllabusService extends GenericService<Syllabus, Long> {

	/**
	 * Método que importa los planes de estudio de un fichero de importación y
	 * que, si se da el caso, devuelve otro fichero que contiene los errores de
	 * formato detectados en dicho fichero de importación.
	 * 
	 * @param file
	 *            el fichero que se importa.
	 * @return el fichero que contiene los errores de formato detectados en el
	 *         fichero de importación.
	 */
	OutputStream importSyllabuses(InputStream file) throws BusinessException;
}
