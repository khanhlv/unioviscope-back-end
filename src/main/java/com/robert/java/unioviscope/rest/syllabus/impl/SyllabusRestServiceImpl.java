package com.robert.java.unioviscope.rest.syllabus.impl;

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
import com.robert.java.unioviscope.business.syllabus.SyllabusService;
import com.robert.java.unioviscope.model.Syllabus;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.rest.GenericRestServiceImpl;
import com.robert.java.unioviscope.rest.syllabus.SyllabusRestService;
import com.robert.java.unioviscope.rest.util.HttpUtil;

/**
 * Clase que extiende la clase GenericRestServiceImpl e implementa la interfaz
 * SyllabusRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.GenericRestServiceImpl
 * @see com.robert.java.unioviscope.rest.syllabus.SyllabusRestService
 */
@RestController
@RequestMapping("syllabus")
public class SyllabusRestServiceImpl extends GenericRestServiceImpl<Syllabus, Long> implements SyllabusRestService {

	@Autowired
	private SyllabusService syllabusService;

	@Override
	public GenericService<Syllabus, Long> getService() {
		return syllabusService;
	}

	@Override
	public ResponseEntity<byte[]> importSyllabuses(MultipartFile syllabuses) throws BusinessException {

		if (syllabuses == null)
			throw new ImportFileException("syllabuses", "api.import.file.required");

		InputStream file;
		try {
			file = syllabuses.getInputStream();
		} catch (IOException e) {
			throw new ImportFileException("syllabuses", "api.import.file.io.exception");
		}

		ByteArrayOutputStream exportFile = (ByteArrayOutputStream) syllabusService.importSyllabuses(file);

		return new ResponseEntity<byte[]>(exportFile.toByteArray(),
				HttpUtil.getImportHeaders(exportFile.size(), syllabuses.getName()), HttpStatus.OK);
	}
}
