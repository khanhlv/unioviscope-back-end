package com.robert.java.unioviscope.business.teacherGroup.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.robert.java.unioviscope.business.teacherGroup.TeacherGroupService;
import com.robert.java.unioviscope.business.teacherGroup.importer.TeacherGroupImporter;
import com.robert.java.unioviscope.business.teacherGroup.validator.TeacherGroupValidator;
import com.robert.java.unioviscope.model.TeacherGroup;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.model.exception.TeacherGroupManagementException;
import com.robert.java.unioviscope.model.types.key.TeacherGroupKey;
import com.robert.java.unioviscope.persistence.TeacherGroupRepository;

/**
 * Clase que implementa la interfaz TeacherGroupService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.teacherGroup.TeacherGroupService
 */
@Service
public class TeacherGroupServiceImpl implements TeacherGroupService {

	@Autowired
	private TeacherGroupRepository teacherGroupRepository;
	@Autowired
	private TeacherGroupValidator validator;
	@Autowired
	private TeacherGroupImporter teacherGroupImporter;

	@Override
	public TeacherGroup find(TeacherGroupKey id) throws TeacherGroupManagementException {
		validator.findById(id);
		return teacherGroupRepository.findOne(id);
	}

	@Override
	public List<TeacherGroup> findAll() {
		return IteratorUtils.toList(teacherGroupRepository.findAll().iterator());
	}

	@Override
	public TeacherGroup save(TeacherGroup teacherGroup) throws TeacherGroupManagementException {
		validator.save(teacherGroup);
		return teacherGroupRepository.save(teacherGroup);
	}

	@Override
	public TeacherGroup update(TeacherGroup teacherGroup) throws TeacherGroupManagementException {
		validator.update(teacherGroup);
		return teacherGroupRepository.save(teacherGroup);
	}

	@Override
	public Boolean deleteById(TeacherGroupKey id) throws TeacherGroupManagementException {
		validator.deleteById(id);
		try {
			teacherGroupRepository.delete(id);
			return true;
		} catch (DataAccessException e) {
			throw new TeacherGroupManagementException("id", "teacherGroup.delete.id.exception");
		}
	}

	@Override
	public OutputStream importTeacherGroups(InputStream file) throws ImportFileException {
		try {
			return teacherGroupImporter.importFile(file);
		} catch (RuntimeException e) {
			throw new ImportFileException("file", "api.import.file.runtime.exception");
		} catch (IOException e) {
			throw new ImportFileException("file", "api.import.file.io.exception");
		}
	}
}
