package com.robert.java.unioviscope.business.teacherGroup.validator.impl;

import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.teacherGroup.validator.TeacherGroupValidator;
import com.robert.java.unioviscope.model.TeacherGroup;
import com.robert.java.unioviscope.model.exception.TeacherGroupManagementException;
import com.robert.java.unioviscope.model.types.key.TeacherGroupKey;

/**
 * Clase que implementa la interfaz TeacherGroupValidator.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.teacherGroup.validator.TeacherGroupValidator
 */
@Component
public class TeacherGroupValidatorImpl implements TeacherGroupValidator {

	@Override
	public void findById(TeacherGroupKey id) throws TeacherGroupManagementException {
		if (id == null || id.getTeacher() == null || id.getGroup() == null)
			throw new TeacherGroupManagementException("id", "teacherGroup.find.id.required");
	}

	@Override
	public void save(TeacherGroup teacherGroup) throws TeacherGroupManagementException {
		if (teacherGroup == null)
			throw new TeacherGroupManagementException("teacherGroup", "teacherGroup.save.teacherGroup.required");
	}

	@Override
	public void update(TeacherGroup teacherGroup) throws TeacherGroupManagementException {
		if (teacherGroup == null)
			throw new TeacherGroupManagementException("teacherGroup", "teacherGroup.update.teacherGroup.required");
	}

	@Override
	public void deleteById(TeacherGroupKey id) throws TeacherGroupManagementException {
		if (id == null || id.getTeacher() == null || id.getGroup() == null)
			throw new TeacherGroupManagementException("id", "teacherGroup.delete.id.required");
	}
}
