package com.robert.java.unioviscope.rest.group;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz que define las operaciones REST específicas para la entidad "Group"
 * (grupo).
 * 
 * @author Robert Ene
 */
public interface GroupRestService {

	/**
	 * Método que importa los grupos de un fichero de importación y que, si se
	 * da el caso, devuelve otro fichero que contiene los errores de formato
	 * detectados en dicho fichero de importación.
	 * 
	 * @param groups
	 *            el fichero que se importa.
	 * @return el fichero que contiene los errores de formato detectados en el
	 *         fichero de importación.
	 */
	@PostMapping("import")
	@ResponseBody
	ResponseEntity<byte[]> importGroups(@RequestPart("groups") MultipartFile groups) throws BusinessException;
}
