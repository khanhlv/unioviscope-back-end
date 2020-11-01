package com.robert.java.unioviscope.business.course;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.model.Course;
import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz que extiende la interfaz GenericService y define los servicios
 * específicos para la entidad "Course" (curso).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.GenericService
 */
public interface CourseService extends GenericService<Course, Long> {

	/**
	 * Método que devuelve un curso del sistema dado su año de inicio.
	 * 
	 * @param year
	 *            el año de inicio del curso.
	 * @return el curso con el año de inicio establecido.
	 */
	Course findByYear(Long year) throws BusinessException;

	/**
	 * Método que devuelve el último curso creado en el sistema.
	 * 
	 * @return el último curso creado.
	 */
	Course findLast();

	/**
	 * Método que elimina un curso del sistema dado su año de inicio.
	 * 
	 * @param year
	 *            el año de inicio del curso.
	 * @return true si el curso ha sido localizado y eliminado, false de lo
	 *         contrario.
	 */
	Boolean deleteByYear(Long year) throws BusinessException;
}
