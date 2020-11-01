package com.robert.java.unioviscope.rest.session;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz que define las operaciones REST específicas para la entidad
 * "Session" (sesión).
 * 
 * @author Robert Ene
 */
public interface SessionRestService {

	/**
	 * Método que importa las sesiones de un fichero de importación y que, si se
	 * da el caso, devuelve otro fichero que contiene los errores de formato
	 * detectados en dicho fichero de importación.
	 * 
	 * @param sessions
	 *            el fichero que se importa.
	 * @return el fichero que contiene los errores de formato detectados en el
	 *         fichero de importación.
	 */
	@PostMapping("import")
	@ResponseBody
	ResponseEntity<byte[]> importSessions(@RequestPart("sessions") MultipartFile sessions) throws BusinessException;
}
