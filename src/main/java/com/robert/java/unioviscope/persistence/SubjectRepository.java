package com.robert.java.unioviscope.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.robert.java.unioviscope.model.Subject;

/**
 * Interfaz que extiende la interfaz GenericRepository y define las operaciones
 * de acceso a datos específicas para la entidad "Subject" (asignatura).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.persistence.GenericRepository
 */
@Repository
@Transactional(readOnly = true)
public interface SubjectRepository extends GenericRepository<Subject, Long> {

	/**
	 * Método que devuelve la asignatura asociada a un código único.
	 * 
	 * @param code
	 *            el código de la asignatura.
	 * @return la asignatura asociada.
	 */
	Subject findByCode(String code);

	/**
	 * Método que devuelve las asignaturas asociadas a un determinado curso y un
	 * determinado estudiante (en las que se encuentra matriculado en dicho
	 * curso académico).
	 * 
	 * @param year
	 *            el año de inicio del curso.
	 * @param id
	 *            el id del estudiante.
	 * @return la lista de asignaturas.
	 */
	List<Subject> findByStudentYearAndId(Long year, Long id);

	/**
	 * Método que devuelve las asignaturas asociadas a un determinado curso y un
	 * determinado docente (en las que está impartiendo clases en dicho curso
	 * académico).
	 * 
	 * @param year
	 *            el año de inicio del curso.
	 * @param id
	 *            el id del docente.
	 * @return la lista de asignaturas.
	 */
	List<Subject> findByTeacherYearAndId(Long year, Long id);

	/**
	 * Método que devuelve las asignaturas asociadas a un determinado docente,
	 * para el último curso académico (en las que está impartiendo clases en el
	 * último curso académico).
	 * 
	 * @param id
	 *            el id del docente.
	 * @return la lista de asignaturas.
	 */
	List<Subject> findLastYearByTeacherId(Long id);

	/**
	 * Método que devuelve las asignaturas asociadas a un determinado
	 * estudiante, para el último curso académico (en las que se encuentra
	 * matriculado en el último curso académico).
	 * 
	 * @param id
	 *            el id del estudiante.
	 * @return la lista de asignaturas.
	 */
	List<Subject> findLastYearByStudentId(Long id);

	/**
	 * Método que elimina la asignatura asociada a un determinado código único.
	 * 
	 * @param code
	 *            el código de la asignatura.
	 */
	@Transactional
	void deleteByCode(String code);
}