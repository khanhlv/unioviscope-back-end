package com.robert.java.unioviscope.rest.course;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.robert.java.unioviscope.model.Course;
import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz que define las operaciones REST específicas para la entidad "Course"
 * (curso).
 * 
 * @author Robert Ene
 */
public interface CourseRestService {

	/**
	 * Método que devuelve un curso del sistema dado su año de inicio.
	 * 
	 * @param year
	 *            el año de inicio del curso.
	 * @return el curso con el año de inicio establecido.
	 */
	@GetMapping("findByYear")
	@ResponseBody
	Course findByYear(@RequestParam("year") Long year) throws BusinessException;

	/**
	 * Método que devuelve el último curso creado en el sistema.
	 * 
	 * @return el último curso creado.
	 */
	@GetMapping("findLast")
	@ResponseBody
	Course findLast();

	/**
	 * Método que elimina un curso del sistema dado su año de inicio.
	 * 
	 * @param year
	 *            el año de inicio del curso.
	 * @return true si el curso ha sido localizado y eliminado, false de lo
	 *         contrario.
	 */
	@DeleteMapping("deleteByYear")
	Boolean deleteByYear(@RequestParam("year") Long year) throws BusinessException;
}
