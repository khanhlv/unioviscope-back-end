package com.robert.java.unioviscope.business.common.importer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interfaz genérica que define las operaciones de importación más comunes a las
 * entidades del dominio de la aplicación.
 * 
 * @author Robert Ene
 *
 * @param <T>
 *            el tipo de la entidad
 */
public interface Importer<T> {

	/**
	 * Método que importa las entidades de un fichero de importación y que, si
	 * se da el caso, devuelve otro fichero que contiene los errores de formato
	 * detectados en dicho fichero de importación.
	 * 
	 * @param file
	 *            el fichero que se importa.
	 * @return el fichero que contiene los errores de formato detectados en el
	 *         fichero de importación.
	 */
	OutputStream importFile(InputStream file) throws IOException;
}
