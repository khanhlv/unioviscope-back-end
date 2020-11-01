package com.robert.java.unioviscope.business.group.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.robert.java.unioviscope.business.group.GroupService;
import com.robert.java.unioviscope.business.group.importer.GroupImporter;
import com.robert.java.unioviscope.business.group.validator.GroupValidator;
import com.robert.java.unioviscope.model.Group;
import com.robert.java.unioviscope.model.exception.GroupManagementException;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.persistence.GroupRepository;

/**
 * Clase que implementa la interfaz GroupService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.group.GroupService
 */
@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private GroupValidator validator;
	@Autowired
	private GroupImporter groupImporter;

	@Override
	public Group find(Long id) throws GroupManagementException {
		validator.findById(id);
		return groupRepository.findOne(id);
	}

	@Override
	public List<Group> findAll() {
		return IteratorUtils.toList(groupRepository.findAll().iterator());
	}

	@Override
	public Group save(Group group) throws GroupManagementException {
		validator.save(group);
		return groupRepository.save(group);
	}

	@Override
	public Group update(Group group) throws GroupManagementException {
		validator.update(group);
		return groupRepository.save(group);
	}

	@Override
	public Boolean deleteById(Long id) throws GroupManagementException {
		validator.deleteById(id);
		try {
			groupRepository.delete(id);
			return true;
		} catch (DataAccessException e) {
			throw new GroupManagementException("id", "group.delete.id.exception");
		}
	}

	@Override
	public OutputStream importGroups(InputStream file) throws ImportFileException {
		try {
			return groupImporter.importFile(file);
		} catch (RuntimeException e) {
			throw new ImportFileException("file", "api.import.file.runtime.exception");
		} catch (IOException e) {
			throw new ImportFileException("file", "api.import.file.io.exception");
		}
	}
}
