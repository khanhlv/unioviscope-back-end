package com.robert.java.unioviscope.business.common.validator;

import java.io.Serializable;

import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz genérica del validador de entidades del dominio de la aplicación que
 * define las validaciones de las operaciones más comunes para el mantenimiento
 * de las entidades del dominio.
 * 
 * @author Robert Ene
 * 
 * @param <T>
 *            el tipo de la entidad.
 * @param <ID>
 *            el tipo del id de la entidad.
 * @param <E>
 *            el tipo de excepción lanzada en las operaciones de la entidad.
 */
public interface GenericValidator<T, ID extends Serializable, E extends BusinessException> {

	/**
	 * Método que valida la operación de encontrar y devolver una entidad dado
	 * su id.
	 * 
	 * @param id
	 *            el id de la entidad.
	 */
	void findById(ID id) throws E;

	/**
	 * Método que valida la operación de guardar una entidad en el sistema.
	 * 
	 * @param entity
	 *            la entidad que se guarda.
	 */
	void save(T entity) throws E;

	/**
	 * Método que valida la operación de modificar los datos de una entidad del
	 * sistema.
	 * 
	 * @param entity
	 *            la entidad que se modifica.
	 */
	void update(T entity) throws E;

	/**
	 * Método que valida la operación de eliminar una entidad del sistema dado
	 * su id.
	 * 
	 * @param id
	 *            el id de la entidad.
	 */
	void deleteById(ID id) throws E;
}
