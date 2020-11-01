package com.robert.java.unioviscope.business;

import java.io.Serializable;
import java.util.List;

import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz genérica de los servicios de la aplicación que define las
 * operaciones más comunes para el mantenimiento de las entidades del dominio.
 * 
 * @author Robert Ene
 * 
 * @param <T>
 *            el tipo de la entidad
 * @param <ID>
 *            el tipo del id de la entidad
 */
public interface GenericService<T, ID extends Serializable> {

	/**
	 * Método que devuelve una entidad del sistema dado su id.
	 * 
	 * @param id
	 *            el id de la entidad.
	 * @return la entidad con el id establecido.
	 */
	T find(ID id) throws BusinessException;

	/**
	 * Método que devuelve todas las entidades de un determinado tipo.
	 * 
	 * @return todas las entidades del tipo establecido.
	 */
	List<T> findAll();

	/**
	 * Método que guarda en el sistema una entidad.
	 * 
	 * @param entity
	 *            la entidad a guardar.
	 * @return la entidad guardada en el sistema.
	 */
	T save(T entity) throws BusinessException;

	/**
	 * Método que modifica los datos de una entidad del sistema.
	 * 
	 * @param entity
	 *            la entidad a modificar.
	 * @return la entidad modificada en el sistema.
	 */
	T update(T entity) throws BusinessException;

	/**
	 * Método que elimina una entidad del sistema dado su id.
	 * 
	 * @param id
	 *            el id de la entidad.
	 * @return true si la entidad ha sido localizada y eliminada, false de lo
	 *         contrario.
	 */
	Boolean deleteById(ID id) throws BusinessException;
}
