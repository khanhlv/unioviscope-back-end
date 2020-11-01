package com.robert.java.unioviscope.business.subject.validator.impl;

import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.subject.validator.SubjectValidator;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.exception.SubjectManagementException;

/**
 * Clase que implementa la interfaz SubjectValidator.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.subject.validator.SubjectValidator
 */
@Component
public class SubjectValidatorImpl implements SubjectValidator {

	@Override
	public void findById(Long id) throws SubjectManagementException {
		if (id == null)
			throw new SubjectManagementException("id", "subject.find.id.required");
	}

	@Override
	public void save(Subject subject) throws SubjectManagementException {
		if (subject == null)
			throw new SubjectManagementException("subject", "subject.save.subject.required");
	}

	@Override
	public void update(Subject subject) throws SubjectManagementException {
		if (subject == null)
			throw new SubjectManagementException("subject", "subject.update.subject.required");
	}

	@Override
	public void deleteById(Long id) throws SubjectManagementException {
		if (id == null)
			throw new SubjectManagementException("id", "subject.delete.id.required");
	}
}
