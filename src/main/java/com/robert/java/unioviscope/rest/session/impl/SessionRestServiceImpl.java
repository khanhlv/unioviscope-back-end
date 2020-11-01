package com.robert.java.unioviscope.rest.session.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.business.session.SessionService;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.rest.GenericRestServiceImpl;
import com.robert.java.unioviscope.rest.session.SessionRestService;
import com.robert.java.unioviscope.rest.util.HttpUtil;

/**
 * Clase que extiende la clase GenericRestServiceImpl e implementa la interfaz
 * SessionRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.GenericRestServiceImpl
 * @see com.robert.java.unioviscope.rest.session.SessionRestService
 */
@RestController
@RequestMapping("session")
public class SessionRestServiceImpl extends GenericRestServiceImpl<Session, Long> implements SessionRestService {

	@Autowired
	private SessionService sessionService;

	public GenericService<Session, Long> getService() {
		return sessionService;
	}

	@Override
	public ResponseEntity<byte[]> importSessions(MultipartFile sessions) throws BusinessException {
		if (sessions == null)
			throw new ImportFileException("sessions", "api.import.file.required");

		InputStream file;
		try {
			file = sessions.getInputStream();
		} catch (IOException e) {
			throw new ImportFileException("sessions", "api.import.file.io.exception");
		}

		ByteArrayOutputStream exportFile = (ByteArrayOutputStream) sessionService.importSessions(file);

		return new ResponseEntity<byte[]>(exportFile.toByteArray(),
				HttpUtil.getImportHeaders(exportFile.size(), sessions.getName()), HttpStatus.OK);
	}
}
