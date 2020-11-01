package com.robert.java.unioviscope.business.courseSubject;

import java.util.List;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.model.CourseSubject;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.types.key.CourseSubjectKey;

/**
 * Interfaz que extiende la interfaz GenericService y define los servicios
 * específicos para la entidad "CourseSubject" (curso académico).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.GenericService
 */
public interface CourseSubjectService extends GenericService<CourseSubject, CourseSubjectKey> {

	/**
	 * Método que crea el siguiente curso académico para todas las asignaturas
	 * pertenecientes a todos los planes de estudios del sistema.
	 */
	void createNextCourseSubjectForAll();

	/**
	 * Método que elimina el último curso académico asociado a un determinado
	 * plan de estudios.
	 * 
	 * @param year
	 *            el año de inicio del curso.
	 * @param syllabusId
	 *            el id del plan de estudios.
	 * @return true si la operación ha tenido éxito, false de lo contrario.
	 */
	Boolean deleteLastCourseByYearAndSyllabus(Long year, Long syllabusId) throws BusinessException;

	/**
	 * Método que devuelve todos los cursos académicos asociados al último curso
	 * y todos los planes de estudios que hayan desarrollado un curso académico.
	 * 
	 * @return la lista con los cursos académicos.
	 */
	List<CourseSubject> findAllWithSyllabus();
}
