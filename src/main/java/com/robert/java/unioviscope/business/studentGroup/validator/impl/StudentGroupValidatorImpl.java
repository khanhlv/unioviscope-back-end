package com.robert.java.unioviscope.business.studentGroup.validator.impl;

import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.studentGroup.validator.StudentGroupValidator;
import com.robert.java.unioviscope.model.StudentGroup;
import com.robert.java.unioviscope.model.exception.StudentGroupManagementException;
import com.robert.java.unioviscope.model.types.key.StudentGroupKey;

/**
 * Clase que implementa la interfaz StudentGroupValidator.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.studentGroup.validator.StudentGroupValidator
 */
@Component
public class StudentGroupValidatorImpl implements StudentGroupValidator {

	@Override
	public void findById(StudentGroupKey id) throws StudentGroupManagementException {
		if (id == null || id.getStudent() == null || id.getGroup() == null)
			throw new StudentGroupManagementException("id", "studentGroup.find.id.required");
	}

	@Override
	public void save(StudentGroup studentGroup) throws StudentGroupManagementException {
		if (studentGroup == null)
			throw new StudentGroupManagementException("studentGroup", "studentGroup.save.studentGroup.required");
	}

	@Override
	public void update(StudentGroup studentGroup) throws StudentGroupManagementException {
		if (studentGroup == null)
			throw new StudentGroupManagementException("studentGroup", "studentGroup.update.studentGroup.required");
	}

	@Override
	public void deleteById(StudentGroupKey id) throws StudentGroupManagementException {
		if (id == null || id.getStudent() == null || id.getGroup() == null)
			throw new StudentGroupManagementException("id", "studentGroup.delete.id.required");
	}
}
