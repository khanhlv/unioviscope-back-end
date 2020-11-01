package com.robert.java.unioviscope.business.teachers.validator.impl;

import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.teachers.validator.TeachersValidator;
import com.robert.java.unioviscope.model.Teacher;
import com.robert.java.unioviscope.model.exception.TeachersManagementException;

/**
 * Clase que implementa la interfaz TeachersValidator.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.teachers.validator.TeachersValidator
 */
@Component
public class TeachersValidatorImpl implements TeachersValidator {

	@Override
	public void findById(Long id) throws TeachersManagementException {
		if (id == null)
			throw new TeachersManagementException("id", "teachers.find.id.required");
	}

	@Override
	public void save(Teacher teacher) throws TeachersManagementException {
		if (teacher == null)
			throw new TeachersManagementException("teacher", "teachers.save.teacher.required");
	}

	@Override
	public void update(Teacher teacher) throws TeachersManagementException {
		if (teacher == null)
			throw new TeachersManagementException("teacher", "teachers.update.teacher.required");
	}

	@Override
	public void deleteById(Long id) throws TeachersManagementException {
		if (id == null)
			throw new TeachersManagementException("id", "teachers.delete.id.required");
	}
}
