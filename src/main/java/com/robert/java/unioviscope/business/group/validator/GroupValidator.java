package com.robert.java.unioviscope.business.group.validator;

import com.robert.java.unioviscope.business.common.validator.GenericValidator;
import com.robert.java.unioviscope.model.Group;
import com.robert.java.unioviscope.model.exception.GroupManagementException;

/**
 * Interfaz que extiende la interfaz GenericValidator y define las validaciones
 * de las operaciones específicas para la entidad "Group" (grupo).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.validator.GenericValidator
 */
public interface GroupValidator extends GenericValidator<Group, Long, GroupManagementException> {

}
