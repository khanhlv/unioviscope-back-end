package com.robert.java.unioviscope.rest;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Clase que implementa la interfaz GenericRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.GenericRestService
 * 
 * @param <T>
 *            el tipo de la entidad
 * @param <ID>
 *            el tipo del id de la entidad
 */
public abstract class GenericRestServiceImpl<T, ID extends Serializable> implements GenericRestService<T, ID> {

	/**
	 * Método que devuelve el servicio concreto de la entidad en tiempo de
	 * ejecución.
	 * 
	 * @return el servicio de la entidad.
	 */
	public abstract GenericService<T, ID> getService();

	@Override
	public T findById(@RequestParam("id") ID id) throws BusinessException {
		return getService().find(id);
	}

	@Override
	public List<T> findAll() {
		return getService().findAll();
	}

	@Override
	public T save(@RequestBody T entity) throws BusinessException {
		return getService().save(entity);
	}

	@Override
	public T update(@RequestBody T entity) throws BusinessException {
		return getService().update(entity);
	}

	@Override
	public Boolean deleteById(@RequestParam("id") ID id) throws BusinessException {
		return getService().deleteById(id);
	}
}
