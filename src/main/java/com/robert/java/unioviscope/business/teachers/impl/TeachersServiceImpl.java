package com.robert.java.unioviscope.business.teachers.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.robert.java.unioviscope.business.teachers.TeachersService;
import com.robert.java.unioviscope.business.teachers.importer.TeachersImporter;
import com.robert.java.unioviscope.business.teachers.validator.TeachersValidator;
import com.robert.java.unioviscope.model.Teacher;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.model.exception.TeachersManagementException;
import com.robert.java.unioviscope.persistence.TeacherRepository;

/**
 * Clase que implementa la interfaz TeachersService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.teachers.TeachersService
 */
@Service
public class TeachersServiceImpl implements TeachersService {

	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private TeachersValidator validator;
	@Autowired
	private TeachersImporter teachersImporter;

	@Override
	public Teacher find(Long id) throws TeachersManagementException {
		validator.findById(id);
		return teacherRepository.findOne(id);
	}

	@Override
	public List<Teacher> findAll() {
		return IteratorUtils.toList(teacherRepository.findAll().iterator());
	}

	@Override
	public Teacher save(Teacher teacher) throws TeachersManagementException {
		validator.save(teacher);
		return teacherRepository.save(teacher);
	}

	@Override
	public Teacher update(Teacher teacher) throws TeachersManagementException {
		validator.update(teacher);
		return teacherRepository.save(teacher);
	}

	@Override
	public Boolean deleteById(Long id) throws TeachersManagementException {
		validator.deleteById(id);
		try {
			teacherRepository.delete(id);
			return true;
		} catch (DataAccessException e) {
			throw new TeachersManagementException("id", "teachers.delete.id.exception");
		}
	}

	@Override
	public OutputStream importTeachers(InputStream file) throws ImportFileException {
		try {
			return teachersImporter.importFile(file);
		} catch (RuntimeException e) {
			throw new ImportFileException("file", "api.import.file.runtime.exception");
		} catch (IOException e) {
			throw new ImportFileException("file", "api.import.file.io.exception");
		}
	}
}
