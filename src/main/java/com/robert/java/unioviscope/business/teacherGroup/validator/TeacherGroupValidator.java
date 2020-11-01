package com.robert.java.unioviscope.business.teacherGroup.validator;

import com.robert.java.unioviscope.business.common.validator.GenericValidator;
import com.robert.java.unioviscope.model.TeacherGroup;
import com.robert.java.unioviscope.model.exception.TeacherGroupManagementException;
import com.robert.java.unioviscope.model.types.key.TeacherGroupKey;

/**
 * Interfaz que extiende la interfaz GenericValidator y define las validaciones
 * de las operaciones específicas para la entidad "TeacherGroup" (docente
 * asignado a grupo).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.validator.GenericValidator
 */
public interface TeacherGroupValidator
		extends GenericValidator<TeacherGroup, TeacherGroupKey, TeacherGroupManagementException> {

}
