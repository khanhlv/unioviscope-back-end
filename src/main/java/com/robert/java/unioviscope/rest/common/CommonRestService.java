package com.robert.java.unioviscope.rest.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.robert.java.unioviscope.model.User;
import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz que define las operaciones REST comunes a todos los roles.
 * 
 * @author Robert Ene
 */
@RequestMapping("common")
public interface CommonRestService {

	/**
	 * Método que devuelve los datos de un usuario tras haber iniciado sesión en
	 * el sistema.
	 * 
	 * @param userName
	 *            el nombre de usuario para el que se solicita los datos.
	 * @return el usuario que contiene los detalles asociados al nombre de
	 *         usuario establecido.
	 */
	@GetMapping("findUserDetails")
	@ResponseBody
	User findUserDetails(@RequestParam("userName") String userName) throws BusinessException;
}
