package com.robert.java.unioviscope.rest.attendance.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.business.attendance.AttendanceService;
import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.dto.editor.AttendanceEditor;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.model.types.key.AttendanceKey;
import com.robert.java.unioviscope.rest.GenericRestServiceImpl;
import com.robert.java.unioviscope.rest.attendance.AttendanceRestService;
import com.robert.java.unioviscope.rest.util.HttpUtil;

/**
 * Clase que extiende la clase GenericRestServiceImpl e implementa la interfaz
 * AttendanceRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.GenericRestServiceImpl
 * @see com.robert.java.unioviscope.rest.attendance.AttendanceRestService
 */
@RestController
@RequestMapping("attendance")
public class AttendanceRestServiceImpl extends GenericRestServiceImpl<Attendance, AttendanceKey>
		implements AttendanceRestService {

	@Autowired
	private AttendanceService attendanceService;

	public GenericService<Attendance, AttendanceKey> getService() {
		return attendanceService;
	}

	@Override
	public ResponseEntity<byte[]> importAttendances(MultipartFile attendances) throws BusinessException {
		if (attendances == null)
			throw new ImportFileException("attendances", "api.import.file.required");

		InputStream file;
		try {
			file = attendances.getInputStream();
		} catch (IOException e) {
			throw new ImportFileException("attendances", "api.import.file.io.exception");
		}

		ByteArrayOutputStream exportFile = (ByteArrayOutputStream) attendanceService.importAttendances(file);

		return new ResponseEntity<byte[]>(exportFile.toByteArray(),
				HttpUtil.getImportHeaders(exportFile.size(), attendances.getName()), HttpStatus.OK);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(AttendanceKey.class, new AttendanceEditor());
	}
}
