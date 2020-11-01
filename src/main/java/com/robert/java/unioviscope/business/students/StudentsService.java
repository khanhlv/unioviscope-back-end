package com.robert.java.unioviscope.business.students;

import java.io.InputStream;
import java.io.OutputStream;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.model.Student;
import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz que extiende la interfaz GenericService y define los servicios
 * específicos para la entidad "Student" (estudiante).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.GenericService
 */
public interface StudentsService extends GenericService<Student, Long> {

	/**
	 * Método que importa los estudiantes de un fichero de importación y que, si
	 * se da el caso, devuelve otro fichero que contiene los errores de formato
	 * detectados en dicho fichero de importación.
	 * 
	 * @param file
	 *            el fichero que se importa.
	 * @return el fichero que contiene los errores de formato detectados en el
	 *         fichero de importación.
	 */
	OutputStream importStudents(InputStream file) throws BusinessException;
}
