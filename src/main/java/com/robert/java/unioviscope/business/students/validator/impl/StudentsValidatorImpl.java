package com.robert.java.unioviscope.business.students.validator.impl;

import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.students.validator.StudentsValidator;
import com.robert.java.unioviscope.model.Student;
import com.robert.java.unioviscope.model.exception.StudentsManagementException;

/**
 * Clase que implementa la interfaz StudentsValidator.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.students.validator.StudentsValidator
 */
@Component
public class StudentsValidatorImpl implements StudentsValidator {

	@Override
	public void findById(Long id) throws StudentsManagementException {
		if (id == null)
			throw new StudentsManagementException("id", "students.find.id.required");
	}

	@Override
	public void save(Student student) throws StudentsManagementException {
		if (student == null)
			throw new StudentsManagementException("student", "students.save.student.required");
	}

	@Override
	public void update(Student student) throws StudentsManagementException {
		if (student == null)
			throw new StudentsManagementException("student", "students.update.student.required");
	}

	@Override
	public void deleteById(Long id) throws StudentsManagementException {
		if (id == null)
			throw new StudentsManagementException("id", "students.delete.id.required");
	}

}
