package com.robert.java.unioviscope.rest.student.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.robert.java.unioviscope.business.student.StudentService;
import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.dto.AttendanceCertificate;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.exception.ProcessImageException;
import com.robert.java.unioviscope.model.types.GroupType;
import com.robert.java.unioviscope.rest.student.StudentRestService;

/**
 * Clase que implementa la interfaz StudentRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.student.StudentRestService
 */
@Controller
public class StudentRestServiceImpl implements StudentRestService {

	@Autowired
	private StudentService studentService;

	@Override
	public Boolean verifyAttendanceCertificate(@RequestBody AttendanceCertificate attendanceCertificate) {
		return studentService.verifyAttendanceCertificate(attendanceCertificate);
	}

	@Override
	public Boolean certifyAttendance(@RequestPart("certificate") AttendanceCertificate certificate, MultipartFile image)
			throws BusinessException {
		InputStream imageInputStream = null;

		if (image != null) {
			try {
				imageInputStream = image.getInputStream();
			} catch (IOException e) {
				throw new ProcessImageException("image", "student.certifyAttendance.image.io.exception");
			}
		}

		return studentService.certifyAttendance(certificate, imageInputStream);
	}

	@Override
	public List<Subject> findLastYearSubjects(Long studentId) {
		return studentService.findLastYearSubjects(studentId);
	}

	@Override
	public List<Session> findLastYearSubjectSessions(Long studentId, Long subjectId, GroupType groupType) {
		return studentService.findLastYearSubjectSessions(studentId, subjectId, groupType);
	}

	@Override
	public Attendance findStudentSessionAttendance(Long studentId, Long sessionId) {
		return studentService.findStudentSessionAttendance(studentId, sessionId);
	}
}
