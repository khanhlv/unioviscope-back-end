package com.robert.java.unioviscope.business.course.validator;

import com.robert.java.unioviscope.business.common.validator.GenericValidator;
import com.robert.java.unioviscope.model.Course;
import com.robert.java.unioviscope.model.exception.CourseManagementException;

/**
 * Interfaz que extiende la interfaz GenericValidator y define las validaciones
 * de las operaciones específicas para la entidad "Course" (curso).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.validator.GenericValidator
 */
public interface CourseValidator extends GenericValidator<Course, Long, CourseManagementException> {

	/**
	 * Método que valida la operación de encontrar y devolver un curso del
	 * sistema dado su año de inicio.
	 * 
	 * @param year
	 *            el año de inicio del curso.
	 */
	void findByYear(Long year) throws CourseManagementException;

	/**
	 * Método que valida la operación de eliminar un curso del sistema dado su
	 * año de inicio.
	 * 
	 * @param year
	 *            el año de inicio del curso.
	 */
	void deleteByYear(Long year) throws CourseManagementException;
}
