package com.robert.java.unioviscope.rest;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz genérica de los servicios REST que define las operaciones más
 * comunes para el mantenimiento de las entidades del dominio.
 * 
 * @author Robert Ene
 * 
 * @param <T>
 *            el tipo de la entidad
 * @param <ID>
 *            el tipo del id de la entidad
 */
public interface GenericRestService<T, ID extends Serializable> {

	/**
	 * Método que devuelve una entidad del sistema dado su id.
	 * 
	 * @param id
	 *            el id de la entidad.
	 * @return la entidad con el id establecido.
	 */
	@GetMapping("findById")
	@ResponseBody
	T findById(ID id) throws BusinessException;

	/**
	 * Método que devuelve todas las entidades de un determinado tipo.
	 * 
	 * @return todas las entidades del tipo establecido.
	 */
	@GetMapping("findAll")
	@ResponseBody
	List<T> findAll();

	/**
	 * Método que guarda en el sistema una entidad.
	 * 
	 * @param entity
	 *            la entidad a guardar.
	 * @return la entidad guardada en el sistema.
	 */
	@PostMapping("save")
	@ResponseBody
	T save(@RequestBody T entity) throws BusinessException;

	/**
	 * Método que modifica los datos de una entidad del sistema.
	 * 
	 * @param entity
	 *            la entidad a modificar.
	 * @return la entidad modificada en el sistema.
	 */
	@PutMapping("update")
	@ResponseBody
	T update(@RequestBody T entity) throws BusinessException;

	/**
	 * Método que elimina una entidad del sistema dado su id.
	 * 
	 * @param id
	 *            el id de la entidad.
	 * @return true si la entidad ha sido localizada y eliminada, false de lo
	 *         contrario.
	 */
	@DeleteMapping("deleteById")
	@ResponseBody
	Boolean deleteById(ID id) throws BusinessException;
}
