package com.robert.java.unioviscope.business.syllabus.validator.impl;

import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.syllabus.validator.SyllabusValidator;
import com.robert.java.unioviscope.model.Syllabus;
import com.robert.java.unioviscope.model.exception.SyllabusManagementException;

/**
 * Clase que implementa la interfaz SyllabusValidator.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.syllabus.validator.SyllabusValidator
 */
@Component
public class SyllabusValidatorImpl implements SyllabusValidator {

	@Override
	public void findById(Long id) throws SyllabusManagementException {
		if (id == null)
			throw new SyllabusManagementException("id", "syllabus.find.id.required");
	}

	@Override
	public void save(Syllabus syllabus) throws SyllabusManagementException {
		if (syllabus == null)
			throw new SyllabusManagementException("syllabus", "syllabus.save.syllabus.required");
	}

	@Override
	public void update(Syllabus syllabus) throws SyllabusManagementException {
		if (syllabus == null)
			throw new SyllabusManagementException("syllabus", "syllabus.update.syllabus.required");
	}

	@Override
	public void deleteById(Long id) throws SyllabusManagementException {
		if (id == null)
			throw new SyllabusManagementException("id", "syllabus.delete.id.required");
	}
}
