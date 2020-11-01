package com.robert.java.unioviscope.rest.students.impl;

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
import com.robert.java.unioviscope.business.students.StudentsService;
import com.robert.java.unioviscope.model.Student;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.rest.GenericRestServiceImpl;
import com.robert.java.unioviscope.rest.students.StudentsRestService;
import com.robert.java.unioviscope.rest.util.HttpUtil;

/**
 * Clase que extiende la clase GenericRestServiceImpl e implementa la interfaz
 * StudentsRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.GenericRestServiceImpl
 * @see com.robert.java.unioviscope.rest.students.StudentsRestService
 */
@RestController
@RequestMapping("students")
public class StudentsRestServiceImpl extends GenericRestServiceImpl<Student, Long> implements StudentsRestService {

	@Autowired
	private StudentsService studentsService;

	@Override
	public GenericService<Student, Long> getService() {
		return studentsService;
	}

	@Override
	public ResponseEntity<byte[]> importStudents(MultipartFile students) throws BusinessException {
		if (students == null)
			throw new ImportFileException("students", "api.import.file.required");

		InputStream file;
		try {
			file = students.getInputStream();
		} catch (IOException e) {
			throw new ImportFileException("students", "api.import.file.io.exception");
		}

		ByteArrayOutputStream exportFile = (ByteArrayOutputStream) studentsService.importStudents(file);

		return new ResponseEntity<byte[]>(exportFile.toByteArray(),
				HttpUtil.getImportHeaders(exportFile.size(), students.getName()), HttpStatus.OK);
	}
}
