package com.robert.java.unioviscope.business.attendance.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.robert.java.unioviscope.business.attendance.AttendanceService;
import com.robert.java.unioviscope.business.attendance.importer.AttendanceImporter;
import com.robert.java.unioviscope.business.attendance.validator.AttendanceValidator;
import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.exception.AttendanceManagementException;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.model.types.key.AttendanceKey;
import com.robert.java.unioviscope.persistence.AttendanceRepository;

/**
 * Clase que implementa la interfaz AttendanceService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.attendance.AttendanceService
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	private AttendanceRepository attendanceRepository;
	@Autowired
	private AttendanceValidator validator;
	@Autowired
	private AttendanceImporter attendanceImporter;

	@Override
	public Attendance find(AttendanceKey id) throws AttendanceManagementException {
		validator.findById(id);
		return attendanceRepository.findOne(id);
	}

	@Override
	public List<Attendance> findAll() {
		return IteratorUtils.toList(attendanceRepository.findAll().iterator());
	}

	@Override
	public Attendance save(Attendance attendance) throws AttendanceManagementException {
		validator.save(attendance);
		return attendanceRepository.save(attendance);
	}

	@Override
	public Attendance update(Attendance attendance) throws AttendanceManagementException {
		validator.update(attendance);
		return attendanceRepository.save(attendance);
	}

	@Override
	public Boolean deleteById(AttendanceKey id) throws AttendanceManagementException {
		validator.deleteById(id);
		try {
			attendanceRepository.delete(id);
			return true;
		} catch (DataAccessException e) {
			throw new AttendanceManagementException("id", "attendance.delete.id.exception");
		}
	}

	@Override
	public OutputStream importAttendances(InputStream file) throws ImportFileException {
		try {
			return attendanceImporter.importFile(file);
		} catch (RuntimeException e) {
			throw new ImportFileException("file", "api.import.file.runtime.exception");
		} catch (IOException e) {
			throw new ImportFileException("file", "api.import.file.io.exception");
		}
	}
}
