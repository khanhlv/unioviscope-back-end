package com.robert.java.unioviscope.business.teachers;

import java.io.InputStream;
import java.io.OutputStream;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.model.Teacher;
import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz que extiende la interfaz GenericService y define los servicios
 * específicos para la entidad "Teacher" (docente).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.GenericService
 */
public interface TeachersService extends GenericService<Teacher, Long> {

	/**
	 * Método que importa los docentes de un fichero de importación y que, si se
	 * da el caso, devuelve otro fichero que contiene los errores de formato
	 * detectados en dicho fichero de importación.
	 * 
	 * @param file
	 *            el fichero que se importa.
	 * @return el fichero que contiene los errores de formato detectados en el
	 *         fichero de importación.
	 */
	OutputStream importTeachers(InputStream file) throws BusinessException;
}
