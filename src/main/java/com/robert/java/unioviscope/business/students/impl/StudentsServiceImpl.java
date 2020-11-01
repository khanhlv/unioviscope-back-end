package com.robert.java.unioviscope.business.students.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.robert.java.unioviscope.business.students.StudentsService;
import com.robert.java.unioviscope.business.students.importer.StudentsImporter;
import com.robert.java.unioviscope.business.students.validator.StudentsValidator;
import com.robert.java.unioviscope.model.Student;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.model.exception.StudentsManagementException;
import com.robert.java.unioviscope.persistence.StudentRepository;

/**
 * Clase que implementa la interfaz StudentsService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.students.StudentsService
 */
@Service
public class StudentsServiceImpl implements StudentsService {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private StudentsValidator validator;
	@Autowired
	private StudentsImporter studentsImporter;

	@Override
	public Student find(Long id) throws StudentsManagementException {
		validator.findById(id);
		return studentRepository.findOne(id);
	}

	@Override
	public List<Student> findAll() {
		return IteratorUtils.toList(studentRepository.findAll().iterator());
	}

	@Override
	public Student save(Student student) throws StudentsManagementException {
		validator.save(student);
		return studentRepository.save(student);
	}

	@Override
	public Student update(Student student) throws StudentsManagementException {
		validator.update(student);
		return studentRepository.save(student);
	}

	@Override
	public Boolean deleteById(Long id) throws StudentsManagementException {
		validator.deleteById(id);
		try {
			studentRepository.delete(id);
			return true;
		} catch (DataAccessException e) {
			throw new StudentsManagementException("id", "students.delete.id.exception");
		}
	}

	@Override
	public OutputStream importStudents(InputStream file) throws ImportFileException {
		try {
			return studentsImporter.importFile(file);
		} catch (RuntimeException e) {
			throw new ImportFileException("file", "api.import.file.runtime.exception");
		} catch (IOException e) {
			throw new ImportFileException("file", "api.import.file.io.exception");
		}
	}
}
