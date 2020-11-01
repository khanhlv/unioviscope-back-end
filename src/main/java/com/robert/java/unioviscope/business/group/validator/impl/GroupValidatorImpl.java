package com.robert.java.unioviscope.business.group.validator.impl;

import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.group.validator.GroupValidator;
import com.robert.java.unioviscope.model.Group;
import com.robert.java.unioviscope.model.exception.GroupManagementException;

/**
 * Clase que implementa la interfaz GroupValidator.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.group.validator.GroupValidator
 */
@Component
public class GroupValidatorImpl implements GroupValidator {

	@Override
	public void findById(Long id) throws GroupManagementException {
		if (id == null)
			throw new GroupManagementException("id", "group.find.id.required");
	}

	@Override
	public void save(Group group) throws GroupManagementException {
		if (group == null)
			throw new GroupManagementException("group", "group.save.group.required");
	}

	@Override
	public void update(Group group) throws GroupManagementException {
		if (group == null)
			throw new GroupManagementException("group", "group.update.group.required");
	}

	@Override
	public void deleteById(Long id) throws GroupManagementException {
		if (id == null)
			throw new GroupManagementException("id", "group.delete.id.required");
	}
}
