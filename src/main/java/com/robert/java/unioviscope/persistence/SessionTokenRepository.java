package com.robert.java.unioviscope.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.robert.java.unioviscope.model.SessionToken;

/**
 * Interfaz que extiende la interfaz GenericRepository y define las operaciones
 * de acceso a datos específicas para la entidad "SessionToken" (token de
 * sesión).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.persistence.GenericRepository
 */
@Repository
@Transactional(readOnly = true)
public interface SessionTokenRepository extends GenericRepository<SessionToken, Long> {

	/**
	 * Método que devuelve el token de una sesión asociado a un determinado
	 * token.
	 * 
	 * @param token
	 *            el token de la sesión.
	 * @return el token asociado a la sesión.
	 */
	SessionToken findByToken(String token);

	/**
	 * Método que devuelve los tokens asociados a una determinada sesión.
	 * 
	 * @param sessionId
	 *            el id de la sesión.
	 * @return el token asociado a la sesión.
	 */
	List<SessionToken> findBySessionId(Long sessionId);

	/**
	 * Método que elimina un token asociado a una determinada sesión.
	 * 
	 * @param sessionId
	 *            el id de la sesión.
	 */
	@Transactional
	void deleteBySessionId(Long sessionId);
}
