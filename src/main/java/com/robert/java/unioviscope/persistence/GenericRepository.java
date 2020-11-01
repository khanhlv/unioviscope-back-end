package com.robert.java.unioviscope.persistence;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interfaz genérica de los repositorios que define las operaciones más comunes
 * para el mantenimiento de las entidades del dominio.
 * 
 * @author Robert Ene
 * 
 * @param <T>
 *            el tipo de la entidad
 * @param <ID>
 *            el tipo del id de la entidad
 */
@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

}
