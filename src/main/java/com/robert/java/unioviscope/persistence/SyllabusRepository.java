package com.robert.java.unioviscope.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.robert.java.unioviscope.model.Syllabus;

/**
 * Interfaz que extiende la interfaz GenericRepository y define las operaciones
 * de acceso a datos específicas para la entidad "Syllabus" (plan de estudio).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.persistence.GenericRepository
 */
@Repository
@Transactional(readOnly = true)
public interface SyllabusRepository extends GenericRepository<Syllabus, Long> {

	/**
	 * Método que devuelve el plan de estudios asociado a un determinado código
	 * único.
	 * 
	 * @param code
	 *            el código del plan de estudios.
	 * @return el plan de estudios asociado.
	 */
	Syllabus findByCode(String code);

	/**
	 * Método que devuelve los planes de estudios asociados a un determinado
	 * curso.
	 * 
	 * @param courseId
	 *            el id del curso.
	 * @return la lista de planes de estudios.
	 */
	List<Syllabus> findByLastCourseId(Long courseId);

	/**
	 * Método que elimina el plan de estudios asociado a un determinado código
	 * único.
	 * 
	 * @param code
	 *            el código del plan de estudios.
	 */
	@Transactional
	void deleteByCode(String code);
}