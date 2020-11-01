package com.robert.java.unioviscope.business.syllabus.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.robert.java.unioviscope.business.syllabus.SyllabusService;
import com.robert.java.unioviscope.business.syllabus.importer.SyllabusImporter;
import com.robert.java.unioviscope.business.syllabus.validator.SyllabusValidator;
import com.robert.java.unioviscope.model.Syllabus;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.model.exception.SyllabusManagementException;
import com.robert.java.unioviscope.persistence.SyllabusRepository;

/**
 * Clase que implementa la interfaz SyllabusService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.syllabus.SyllabusService
 */
@Service
public class SyllabusServiceImpl implements SyllabusService {

	@Autowired
	private SyllabusRepository syllabusRepository;
	@Autowired
	private SyllabusValidator validator;
	@Autowired
	private SyllabusImporter syllabusImporter;

	@Override
	public Syllabus find(Long id) throws SyllabusManagementException {
		validator.findById(id);
		return syllabusRepository.findOne(id);
	}

	@Override
	public List<Syllabus> findAll() {
		return IteratorUtils.toList(syllabusRepository.findAll().iterator());
	}

	@Override
	public Syllabus save(Syllabus syllabus) throws SyllabusManagementException {
		validator.save(syllabus);
		return syllabusRepository.save(syllabus);
	}

	@Override
	public Syllabus update(Syllabus syllabus) throws SyllabusManagementException {
		validator.update(syllabus);
		return syllabusRepository.save(syllabus);
	}

	@Override
	public Boolean deleteById(Long id) throws SyllabusManagementException {
		validator.deleteById(id);
		try {
			syllabusRepository.delete(id);
			return true;
		} catch (DataAccessException e) {
			throw new SyllabusManagementException("id", "syllabus.delete.id.exception");
		}
	}

	@Override
	public OutputStream importSyllabuses(InputStream file) throws ImportFileException {
		try {
			return syllabusImporter.importFile(file);
		} catch (RuntimeException e) {
			throw new ImportFileException("file", "api.import.file.runtime.exception");
		} catch (IOException e) {
			throw new ImportFileException("file", "api.import.file.io.exception");
		}
	}
}
