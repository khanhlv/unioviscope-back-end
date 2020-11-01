package com.robert.java.unioviscope.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.robert.java.unioviscope.model.User;

/**
 * Interfaz que extiende la interfaz GenericRepository y define las operaciones
 * de acceso a datos específicas para la entidad "User" (usuario).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.persistence.GenericRepository
 */
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends GenericRepository<User, Long> {

	/**
	 * Método que devuelve los datos de un usuario asociados a un determinado
	 * nombre de usuario.
	 * 
	 * @param userName
	 *            el nombre de usuario.
	 * @return los datos del usuario asociado.
	 */
	User findByUserName(String userName);
}