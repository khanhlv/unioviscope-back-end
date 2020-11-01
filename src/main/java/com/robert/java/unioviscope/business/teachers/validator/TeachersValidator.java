package com.robert.java.unioviscope.business.teachers.validator;

import com.robert.java.unioviscope.business.common.validator.GenericValidator;
import com.robert.java.unioviscope.model.Teacher;
import com.robert.java.unioviscope.model.exception.TeachersManagementException;

/**
 * Interfaz que extiende la interfaz GenericValidator y define las validaciones
 * de las operaciones específicas para la entidad "Teacher" (docente).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.validator.GenericValidator
 */
public interface TeachersValidator extends GenericValidator<Teacher, Long, TeachersManagementException> {

}
