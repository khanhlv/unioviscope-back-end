package com.robert.java.unioviscope.business.subject.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.robert.java.unioviscope.business.subject.SubjectService;
import com.robert.java.unioviscope.business.subject.importer.SubjectImporter;
import com.robert.java.unioviscope.business.subject.validator.SubjectValidator;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.model.exception.SubjectManagementException;
import com.robert.java.unioviscope.persistence.SubjectRepository;

/**
 * Clase que implementa la interfaz SubjectService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.subject.SubjectService
 */
@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private SubjectValidator validator;
	@Autowired
	private SubjectImporter subjectImporter;

	@Override
	public Subject find(Long id) throws SubjectManagementException {
		validator.findById(id);
		return subjectRepository.findOne(id);
	}

	@Override
	public List<Subject> findAll() {
		return IteratorUtils.toList(subjectRepository.findAll().iterator());
	}

	@Override
	public Subject save(Subject subject) throws SubjectManagementException {
		validator.save(subject);
		return subjectRepository.save(subject);
	}

	@Override
	public Subject update(Subject subject) throws SubjectManagementException {
		validator.update(subject);
		return subjectRepository.save(subject);
	}

	@Override
	public Boolean deleteById(Long id) throws SubjectManagementException {
		validator.deleteById(id);
		try {
			subjectRepository.delete(id);
			return true;
		} catch (DataAccessException e) {
			throw new SubjectManagementException("id", "subject.delete.id.exception");
		}
	}

	@Override
	public OutputStream importSubjects(InputStream file) throws ImportFileException {
		try {
			return subjectImporter.importFile(file);
		} catch (RuntimeException e) {
			throw new ImportFileException("file", "api.import.file.runtime.exception");
		} catch (IOException e) {
			throw new ImportFileException("file", "api.import.file.io.exception");
		}
	}
}
