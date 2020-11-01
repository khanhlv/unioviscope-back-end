package com.robert.java.unioviscope.business.subject.validator;

import com.robert.java.unioviscope.business.common.validator.GenericValidator;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.exception.SubjectManagementException;

/**
 * Interfaz que extiende la interfaz GenericValidator y define las validaciones
 * de las operaciones específicas para la entidad "Subject" (asignatura).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.validator.GenericValidator
 */
public interface SubjectValidator extends GenericValidator<Subject, Long, SubjectManagementException> {

}
