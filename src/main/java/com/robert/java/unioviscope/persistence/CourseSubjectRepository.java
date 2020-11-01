package com.robert.java.unioviscope.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.robert.java.unioviscope.model.CourseSubject;
import com.robert.java.unioviscope.model.types.key.CourseSubjectKey;

/**
 * Interfaz que extiende la interfaz GenericRepository y define las operaciones
 * de acceso a datos específicas para la entidad "CourseSubject" (curso
 * académico).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.persistence.GenericRepository
 */
@Repository
@Transactional(readOnly = true)
public interface CourseSubjectRepository extends GenericRepository<CourseSubject, CourseSubjectKey> {

	/**
	 * Método que devuelve el curso académico asociado a un determinado código
	 * de la asignatura y el año del curso.
	 * 
	 * @param code
	 *            el código de la asignatura.
	 * @param year
	 *            el año de inicio del curso.
	 * @return el curso académico asociado.
	 */
	CourseSubject findBySubjectCodeAndCourseYear(String code, Long year);

	/**
	 * Método que devuelve el curso académico asociado a un determinado id de la
	 * asignatura y el año del curso.
	 * 
	 * @param subjectId
	 *            el id de la asignatura.
	 * @param courseId
	 *            el id del curso.
	 * @return el curso académico asociado.
	 */
	CourseSubject findBySubjectIdAndCourseId(Long subjectId, Long courseId);

	/**
	 * Método que elimina los cursos académicos asociados a un determinado curso
	 * y un determinado plan de estudios.
	 * 
	 * @param year
	 *            el año de inicio del curso.
	 * @param syllabusId
	 *            el id del plan de estudios.
	 */
	@Transactional
	void deleteByCourseYearAndSubjectSyllabusId(Long year, Long syllabusId);

	/**
	 * Método que devuelve todos los cursos académicos asociados a un
	 * determinado curso y todos los planes de estudios que hayan desarrollado
	 * un curso académico.
	 * 
	 * @param year
	 *            el año de inicio del curso.
	 * @return la lista con los cursos académicos.
	 */
	List<CourseSubject> findAllByCourseYear(Long year);
}