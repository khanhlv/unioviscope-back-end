package com.robert.java.unioviscope.rest.teacherGroup.impl;

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
import com.robert.java.unioviscope.business.teacherGroup.TeacherGroupService;
import com.robert.java.unioviscope.model.TeacherGroup;
import com.robert.java.unioviscope.model.dto.editor.TeacherGroupEditor;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.model.types.key.TeacherGroupKey;
import com.robert.java.unioviscope.rest.GenericRestServiceImpl;
import com.robert.java.unioviscope.rest.teacherGroup.TeacherGroupRestService;
import com.robert.java.unioviscope.rest.util.HttpUtil;

/**
 * Clase que extiende la clase GenericRestServiceImpl e implementa la interfaz
 * TeacherGroupRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.GenericRestServiceImpl
 * @see com.robert.java.unioviscope.rest.teacherGroup.TeacherGroupRestService
 */
@RestController
@RequestMapping("teacherGroup")
public class TeacherGroupRestServiceImpl extends GenericRestServiceImpl<TeacherGroup, TeacherGroupKey>
		implements TeacherGroupRestService {

	@Autowired
	private TeacherGroupService teacherGroupService;

	@Override
	public GenericService<TeacherGroup, TeacherGroupKey> getService() {
		return teacherGroupService;
	}

	@Override
	public ResponseEntity<byte[]> importTeacherGroups(MultipartFile teacherGroups) throws BusinessException {
		if (teacherGroups == null)
			throw new ImportFileException("teacherGroups", "api.import.file.required");

		InputStream file;
		try {
			file = teacherGroups.getInputStream();
		} catch (IOException e) {
			throw new ImportFileException("teacherGroups", "api.import.file.io.exception");
		}

		ByteArrayOutputStream exportFile = (ByteArrayOutputStream) teacherGroupService.importTeacherGroups(file);

		return new ResponseEntity<byte[]>(exportFile.toByteArray(),
				HttpUtil.getImportHeaders(exportFile.size(), teacherGroups.getName()), HttpStatus.OK);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(TeacherGroupKey.class, new TeacherGroupEditor());
	}
}
