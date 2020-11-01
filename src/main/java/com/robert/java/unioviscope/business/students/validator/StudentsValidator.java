package com.robert.java.unioviscope.business.students.validator;

import com.robert.java.unioviscope.business.common.validator.GenericValidator;
import com.robert.java.unioviscope.model.Student;
import com.robert.java.unioviscope.model.exception.StudentsManagementException;

/**
 * Interfaz que extiende la interfaz GenericValidator y define las validaciones
 * de las operaciones específicas para la entidad "Student" (estudiante).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.validator.GenericValidator
 */
public interface StudentsValidator extends GenericValidator<Student, Long, StudentsManagementException> {

}
