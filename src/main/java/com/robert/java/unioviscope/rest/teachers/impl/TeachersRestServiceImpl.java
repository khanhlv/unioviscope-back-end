package com.robert.java.unioviscope.rest.teachers.impl;

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
import com.robert.java.unioviscope.business.teachers.TeachersService;
import com.robert.java.unioviscope.model.Teacher;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.rest.GenericRestServiceImpl;
import com.robert.java.unioviscope.rest.teachers.TeachersRestService;
import com.robert.java.unioviscope.rest.util.HttpUtil;

/**
 * Clase que extiende la clase GenericRestServiceImpl e implementa la interfaz
 * TeachersRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.GenericRestServiceImpl
 * @see com.robert.java.unioviscope.rest.teachers.TeachersRestService
 */
@RestController
@RequestMapping("teachers")
public class TeachersRestServiceImpl extends GenericRestServiceImpl<Teacher, Long> implements TeachersRestService {

	@Autowired
	private TeachersService teachersService;

	@Override
	public GenericService<Teacher, Long> getService() {
		return teachersService;
	}

	@Override
	public ResponseEntity<byte[]> importTeachers(MultipartFile teachers) throws BusinessException {
		if (teachers == null)
			throw new ImportFileException("teachers", "api.import.file.required");

		InputStream file;
		try {
			file = teachers.getInputStream();
		} catch (IOException e) {
			throw new ImportFileException("teachers", "api.import.file.io.exception");
		}

		ByteArrayOutputStream exportFile = (ByteArrayOutputStream) teachersService.importTeachers(file);

		return new ResponseEntity<byte[]>(exportFile.toByteArray(),
				HttpUtil.getImportHeaders(exportFile.size(), teachers.getName()), HttpStatus.OK);
	}
}
