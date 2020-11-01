package com.robert.java.unioviscope.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.robert.java.unioviscope.model.Course;

/**
 * Interfaz que extiende la interfaz GenericRepository y define las operaciones
 * de acceso a datos específicas para la entidad "Course" (curso).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.persistence.GenericRepository
 */
@Repository
@Transactional(readOnly = true)
public interface CourseRepository extends GenericRepository<Course, Long> {

	/**
	 * Método que devuelve el curso asociado a un año determinado.
	 * 
	 * @param year
	 *            el año de inicio del curso.
	 * @return el curso asociado.
	 */
	Course findByYear(Long year);

	/**
	 * Método que devuelve el último curso creado.
	 * 
	 * @return el último curso creado.
	 */
	Course findByLastYear();

	/**
	 * Método que elimina el curso asociado a un determinado año.
	 * 
	 * @param year
	 *            el año de inicio del curso.
	 */
	@Transactional
	void deleteByYear(Long year);
}