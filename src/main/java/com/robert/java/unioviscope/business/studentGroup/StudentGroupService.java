package com.robert.java.unioviscope.business.studentGroup;

import java.io.InputStream;
import java.io.OutputStream;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.model.StudentGroup;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.types.GroupType;
import com.robert.java.unioviscope.model.types.key.StudentGroupKey;

/**
 * Interfaz que extiende la interfaz GenericService y define los servicios
 * específicos para la entidad "StudentGroup" (estudiante asignado a grupo).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.GenericService
 */
public interface StudentGroupService extends GenericService<StudentGroup, StudentGroupKey> {

	/**
	 * Método que importa las asignaciones de estudiantes a grupos de un fichero
	 * de importación y que, si se da el caso, devuelve otro fichero que
	 * contiene los errores de formato detectados en dicho fichero de
	 * importación.
	 * 
	 * @param file
	 *            el fichero que se importa.
	 * @return el fichero que contiene los errores de formato detectados en el
	 *         fichero de importación.
	 */
	OutputStream importStudentGroups(InputStream file) throws BusinessException;

	/**
	 * Método que devuelve la asignación de un estudiante a un grupo asociada a
	 * un determinado estudiante, un determinado tipo de grupo, un determinado
	 * curso y una determinada asignatura.
	 * 
	 * @param studentId
	 *            el id del estudiante.
	 * @param groupType
	 *            el tipo de grupo.
	 * @param courseId
	 *            el id del curso.
	 * @param subjectId
	 *            el id de la asignatura.
	 * @return la asignación del estudiante al grupo.
	 */
	StudentGroup findByStudentAndGroupTypeAndCourseSubject(Long studentId, GroupType groupType, Long courseId,
			Long subjectId);
}
