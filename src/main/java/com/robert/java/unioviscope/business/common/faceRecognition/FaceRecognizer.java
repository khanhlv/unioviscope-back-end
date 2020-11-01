package com.robert.java.unioviscope.business.common.faceRecognition;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interfaz que define las operaciones de reconocimiento facial basándose en una
 * foto.
 * 
 * @author Robert Ene
 */
public interface FaceRecognizer {

	/**
	 * Método que reconoce un rostro a partir de una imagen y devuelve el
	 * resultado del proceso.
	 * 
	 * @param image
	 *            la imagen que contiene el rostro a reconocer.
	 * @return -1 si el rostro no se reconoce (es nuevo), y cualquier otro
	 *         número entero mayor que 0 (rostros ya reconocidos) de lo
	 *         contrario.
	 */
	Integer recognize(InputStream image) throws IOException;
}
