package com.robert.java.unioviscope.rest.group.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.business.group.GroupService;
import com.robert.java.unioviscope.model.Group;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.rest.GenericRestServiceImpl;
import com.robert.java.unioviscope.rest.group.GroupRestService;
import com.robert.java.unioviscope.rest.util.HttpUtil;

/**
 * Clase que extiende la clase GenericRestServiceImpl e implementa la interfaz
 * GroupRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.GenericRestServiceImpl
 * @see com.robert.java.unioviscope.rest.group.GroupRestService
 */
@RestController
@RequestMapping("group")
public class GroupRestServiceImpl extends GenericRestServiceImpl<Group, Long> implements GroupRestService {

	@Autowired
	private GroupService groupService;

	@Override
	public GenericService<Group, Long> getService() {
		return groupService;
	}

	@Override
	public ResponseEntity<byte[]> importGroups(MultipartFile groups) throws BusinessException {

		if (groups == null)
			throw new ImportFileException("groups", "api.import.file.required");

		InputStream file;
		try {
			file = groups.getInputStream();
		} catch (IOException e) {
			throw new ImportFileException("groups", "api.import.file.io.exception");
		}

		ByteArrayOutputStream exportFile = (ByteArrayOutputStream) groupService.importGroups(file);

		return new ResponseEntity<byte[]>(exportFile.toByteArray(),
				HttpUtil.getImportHeaders(exportFile.size(), groups.getName()), HttpStatus.OK);
	}
}
