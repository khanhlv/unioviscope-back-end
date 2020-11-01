package com.robert.java.unioviscope.rest.courseSubject;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.robert.java.unioviscope.model.CourseSubject;
import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz que define las operaciones REST específicas para la entidad
 * "CourseSubject" (curso académico).
 * 
 * @author Robert Ene
 */
public interface CourseSubjectRestService {

	/**
	 * Método que crea el siguiente curso académico para todas las asignaturas
	 * pertenecientes a todos los planes de estudios del sistema.
	 */
	@PostMapping("createNextForAll")
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
	@DeleteMapping("deleteByYearAndSyllabus")
	@ResponseBody
	Boolean deleteLastCourseByYearAndSyllabus(Long year, Long syllabusId) throws BusinessException;

	/**
	 * Método que devuelve todos los cursos académicos asociados al último curso
	 * y todos los planes de estudios que hayan desarrollado un curso académico.
	 * 
	 * @return la lista con los cursos académicos.
	 */
	@GetMapping("findAllWithSyllabus")
	@ResponseBody
	List<CourseSubject> findAllWithSyllabus();
}
