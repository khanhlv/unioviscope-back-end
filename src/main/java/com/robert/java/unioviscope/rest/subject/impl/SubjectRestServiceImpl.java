package com.robert.java.unioviscope.rest.subject.impl;

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
import com.robert.java.unioviscope.business.subject.SubjectService;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.rest.GenericRestServiceImpl;
import com.robert.java.unioviscope.rest.subject.SubjectRestService;
import com.robert.java.unioviscope.rest.util.HttpUtil;

/**
 * Clase que extiende la clase GenericRestServiceImpl e implementa la interfaz
 * SubjectRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.GenericRestServiceImpl
 * @see com.robert.java.unioviscope.rest.subject.SubjectRestService
 */
@RestController
@RequestMapping("subject")
public class SubjectRestServiceImpl extends GenericRestServiceImpl<Subject, Long> implements SubjectRestService {

	@Autowired
	private SubjectService subjectService;

	public GenericService<Subject, Long> getService() {
		return subjectService;
	}

	@Override
	public ResponseEntity<byte[]> importSubjects(MultipartFile subjects) throws BusinessException {

		if (subjects == null)
			throw new ImportFileException("subjects", "api.import.file.required");

		InputStream file;
		try {
			file = subjects.getInputStream();
		} catch (IOException e) {
			throw new ImportFileException("subjects", "api.import.file.io.exception");
		}

		ByteArrayOutputStream exportFile = (ByteArrayOutputStream) subjectService.importSubjects(file);

		return new ResponseEntity<byte[]>(exportFile.toByteArray(),
				HttpUtil.getImportHeaders(exportFile.size(), subjects.getName()), HttpStatus.OK);
	}
}
