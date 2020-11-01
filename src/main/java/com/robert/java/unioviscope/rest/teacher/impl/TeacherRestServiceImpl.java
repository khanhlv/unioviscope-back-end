package com.robert.java.unioviscope.rest.teacher.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.robert.java.unioviscope.business.attendance.AttendanceService;
import com.robert.java.unioviscope.business.subject.SubjectService;
import com.robert.java.unioviscope.business.teacher.TeacherService;
import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.Group;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.dto.AttendanceDto;
import com.robert.java.unioviscope.model.dto.AttendanceOptions;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.exception.TeacherManagementException;
import com.robert.java.unioviscope.model.types.key.AttendanceKey;
import com.robert.java.unioviscope.rest.teacher.TeacherRestService;
import com.robert.java.unioviscope.rest.util.HttpUtil;

/**
 * Clase que implementa la interfaz TeacherRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.teacher.TeacherRestService
 */
@RestController
public class TeacherRestServiceImpl implements TeacherRestService {

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private AttendanceService attendanceService;

	@Override
	public List<Subject> findLastYearSubjects(Long teacherId) {
		return teacherService.findLastYearSubjects(teacherId);
	}

	@Override
	public List<Group> findLastYearSubjectGroups(Long teacherId, Long subjectId) {
		return teacherService.findLastYearSubjectGroups(teacherId, subjectId);
	}

	@Override
	public List<Session> findLastYearSubjectGroupSessions(Long teacherId, Long subjectId, Long groupId) {
		return teacherService.findLastYearSubjectGroupSessions(teacherId, subjectId, groupId);
	}

	@Override
	public List<String> findLocationsBySubject(Long subjectId) {
		return teacherService.findLocationsBySubject(subjectId);
	}

	@Override
	public ResponseEntity<byte[]> createQrCodeForSession(Long sessionId) throws BusinessException {
		ByteArrayOutputStream qrCode = (ByteArrayOutputStream) teacherService.createQrCodeForSession(sessionId);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		headers.setContentLength(qrCode.size());

		return new ResponseEntity<byte[]>(qrCode.toByteArray(), headers, HttpStatus.OK);
	}

	@Override
	public Boolean stopQrCodeProcessForSession(Long sessionId) {
		return teacherService.stopQrCodeProcessForSession(sessionId);
	}

	@Override
	public Long findAttendancesNoBySession(Long sessionId) {
		return (long) teacherService.findAttendancesBySession(sessionId).size();
	}

	@Override
	public ResponseEntity<byte[]> exportAttendances(@RequestBody AttendanceOptions attendanceOptions)
			throws BusinessException {

		if (attendanceOptions == null)
			throw new TeacherManagementException("attendanceOptions",
					"teacher.exportAttendances.attendanceOptions.required");

		ByteArrayOutputStream exportFile = (ByteArrayOutputStream) teacherService.exportAttendances(attendanceOptions);
		Subject subject = subjectService.find(attendanceOptions.getSubjectId());

		return new ResponseEntity<byte[]>(exportFile.toByteArray(),
				HttpUtil.getExportHeaders(exportFile.size(), subject.getDenomination(), attendanceOptions.getFormat()),
				HttpStatus.OK);
	}

	@Override
	public List<Attendance> findAttendances(@RequestBody AttendanceOptions attendanceOptions) {
		return teacherService.findAttendances(attendanceOptions);
	}

	@Override
	public Attendance updateAttendance(@RequestBody AttendanceDto attendanceDto) throws BusinessException {
		Attendance attendance = attendanceService
				.find(new AttendanceKey(attendanceDto.getStudent(), attendanceDto.getSession()));
		attendance.setConfirmed(attendanceDto.getConfirmed());
		attendance.setComment(attendanceDto.getComment());

		return attendanceService.save(attendance);
	}
}
