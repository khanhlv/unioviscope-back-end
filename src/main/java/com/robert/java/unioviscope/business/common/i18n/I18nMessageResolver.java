package com.robert.java.unioviscope.business.common.i18n;

/**
 * Interfaz que define las operaciones para el servicio de internacionalización
 * de los mensajes de error.
 * 
 * @author Robert Ene
 */
public interface I18nMessageResolver {

	/**
	 * Método que devuelve un mensaje de error internacionalizado en función del
	 * idioma por defecto del sistema.
	 * 
	 * @param key
	 *            la clave para obtener el mensaje internacionalizado.
	 * @param args
	 *            los parámetros que se deben sustituir en el mensaje
	 *            internacionalizado.
	 * @return el mensaje de error internacionalizado.
	 */
	String getMessage(String key, Object... args);
}
