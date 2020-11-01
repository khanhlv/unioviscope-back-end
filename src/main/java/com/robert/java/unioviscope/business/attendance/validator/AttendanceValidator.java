package com.robert.java.unioviscope.business.attendance.validator;

import com.robert.java.unioviscope.business.common.validator.GenericValidator;
import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.exception.AttendanceManagementException;
import com.robert.java.unioviscope.model.types.key.AttendanceKey;

/**
 * Interfaz que extiende la interfaz GenericValidator y define las validaciones
 * de las operaciones específicas para la entidad "Attendance" (asistencia).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.validator.GenericValidator
 */
public interface AttendanceValidator
		extends GenericValidator<Attendance, AttendanceKey, AttendanceManagementException> {

}
