package com.robert.java.unioviscope.business.attendance.validator.impl;

import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.attendance.validator.AttendanceValidator;
import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.exception.AttendanceManagementException;
import com.robert.java.unioviscope.model.types.key.AttendanceKey;

/**
 * Clase que implementa la interfaz AttendanceValidator.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.attendance.validator.AttendanceValidator
 */
@Component
public class AttendanceValidatorImpl implements AttendanceValidator {

	@Override
	public void findById(AttendanceKey id) throws AttendanceManagementException {
		if (id == null || id.getStudent() == null || id.getSession() == null)
			throw new AttendanceManagementException("id", "attendance.find.id.required");
	}

	@Override
	public void save(Attendance attendance) throws AttendanceManagementException {
		if (attendance == null)
			throw new AttendanceManagementException("attendance", "attendance.save.attendance.required");
	}

	@Override
	public void update(Attendance attendance) throws AttendanceManagementException {
		if (attendance == null)
			throw new AttendanceManagementException("attendance", "attendance.update.attendance.required");
	}

	@Override
	public void deleteById(AttendanceKey id) throws AttendanceManagementException {
		if (id == null || id.getStudent() == null || id.getSession() == null)
			throw new AttendanceManagementException("id", "attendance.delete.id.required");
	}
}
