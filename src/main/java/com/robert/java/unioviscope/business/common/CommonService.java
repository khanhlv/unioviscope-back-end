package com.robert.java.unioviscope.business.common;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.robert.java.unioviscope.model.User;
import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz extiende la interfaz UserDetailsService y que define los servicios
 * comunes a todos los roles.
 * 
 * @author Robert Ene
 * @see org.springframework.security.core.userdetails.UserDetailsService
 */
public interface CommonService extends UserDetailsService {

	/**
	 * Método que devuelve los datos de un usuario tras haber iniciado sesión en
	 * el sistema.
	 * 
	 * @param userName
	 *            el nombre de usuario para el que se solicita los datos.
	 * @return el usuario que contiene los detalles asociados al nombre de
	 *         usuario establecido.
	 */
	User findUserDetails(String userName) throws BusinessException;
}
