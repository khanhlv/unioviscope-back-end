package com.robert.java.unioviscope.business.syllabus.validator;

import com.robert.java.unioviscope.business.common.validator.GenericValidator;
import com.robert.java.unioviscope.model.Syllabus;
import com.robert.java.unioviscope.model.exception.SyllabusManagementException;

/**
 * Interfaz que extiende la interfaz GenericValidator y define las validaciones
 * de las operaciones específicas para la entidad "Syllabus" (plan de estudio).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.validator.GenericValidator
 */
public interface SyllabusValidator extends GenericValidator<Syllabus, Long, SyllabusManagementException> {

}
