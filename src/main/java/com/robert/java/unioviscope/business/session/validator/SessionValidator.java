package com.robert.java.unioviscope.business.session.validator;

import com.robert.java.unioviscope.business.common.validator.GenericValidator;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.exception.SessionManagementException;

/**
 * Interfaz que extiende la interfaz GenericValidator y define las validaciones
 * de las operaciones específicas para la entidad "Session" (sesión).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.validator.GenericValidator
 */
public interface SessionValidator extends GenericValidator<Session, Long, SessionManagementException> {

}
