package com.robert.java.unioviscope.business.teacherGroup;

import java.io.InputStream;
import java.io.OutputStream;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.model.TeacherGroup;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.types.key.TeacherGroupKey;

/**
 * Interfaz que extiende la interfaz GenericService y define los servicios
 * específicos para la entidad "TeacherGroup" (docente asignado a grupo).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.GenericService
 */
public interface TeacherGroupService extends GenericService<TeacherGroup, TeacherGroupKey> {

	/**
	 * Método que importa las asignaciones de docentes a grupos de un fichero de
	 * importación y que, si se da el caso, devuelve otro fichero que contiene
	 * los errores de formato detectados en dicho fichero de importación.
	 * 
	 * @param file
	 *            el fichero que se importa.
	 * @return el fichero que contiene los errores de formato detectados en el
	 *         fichero de importación.
	 */
	OutputStream importTeacherGroups(InputStream file) throws BusinessException;
}
