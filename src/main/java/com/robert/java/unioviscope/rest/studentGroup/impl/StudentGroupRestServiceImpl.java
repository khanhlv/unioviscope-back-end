package com.robert.java.unioviscope.rest.studentGroup.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.business.studentGroup.StudentGroupService;
import com.robert.java.unioviscope.model.StudentGroup;
import com.robert.java.unioviscope.model.dto.editor.StudentGroupEditor;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.model.types.GroupType;
import com.robert.java.unioviscope.model.types.key.StudentGroupKey;
import com.robert.java.unioviscope.rest.GenericRestServiceImpl;
import com.robert.java.unioviscope.rest.studentGroup.StudentGroupRestService;
import com.robert.java.unioviscope.rest.util.HttpUtil;

/**
 * Clase que extiende la clase GenericRestServiceImpl e implementa la interfaz
 * StudentGroupRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.GenericRestServiceImpl
 * @see com.robert.java.unioviscope.rest.studentGroup.StudentGroupRestService
 */
@RestController
@RequestMapping("studentGroup")
public class StudentGroupRestServiceImpl extends GenericRestServiceImpl<StudentGroup, StudentGroupKey>
		implements StudentGroupRestService {

	@Autowired
	private StudentGroupService studentGroupService;

	@Override
	public GenericService<StudentGroup, StudentGroupKey> getService() {
		return studentGroupService;
	}

	@Override
	public ResponseEntity<byte[]> importStudentGroups(MultipartFile studentGroups) throws BusinessException {
		if (studentGroups == null)
			throw new ImportFileException("studentGroups", "api.import.file.required");

		InputStream file;
		try {
			file = studentGroups.getInputStream();
		} catch (IOException e) {
			throw new ImportFileException("sessions", "api.import.file.io.exception");
		}

		ByteArrayOutputStream exportFile = (ByteArrayOutputStream) studentGroupService.importStudentGroups(file);

		return new ResponseEntity<byte[]>(exportFile.toByteArray(),
				HttpUtil.getImportHeaders(exportFile.size(), studentGroups.getName()), HttpStatus.OK);
	}

	@Override
	public StudentGroup findByStudentAndGroupTypeAndCourseSubject(Long studentId, GroupType groupType, Long courseId,
			Long subjectId) {
		return studentGroupService.findByStudentAndGroupTypeAndCourseSubject(studentId, groupType, courseId, subjectId);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(StudentGroupKey.class, new StudentGroupEditor());
	}
}
