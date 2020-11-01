package com.robert.java.unioviscope.business.studentGroup.validator;

import com.robert.java.unioviscope.business.common.validator.GenericValidator;
import com.robert.java.unioviscope.model.StudentGroup;
import com.robert.java.unioviscope.model.exception.StudentGroupManagementException;
import com.robert.java.unioviscope.model.types.key.StudentGroupKey;

/**
 * Interfaz que extiende la interfaz GenericValidator y define las validaciones
 * de las operaciones específicas para la entidad "StudentGroup" (estudiante
 * asignado a grupo).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.validator.GenericValidator
 */
public interface StudentGroupValidator
		extends GenericValidator<StudentGroup, StudentGroupKey, StudentGroupManagementException> {

}
