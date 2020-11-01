package com.robert.java.unioviscope.business.student.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.robert.java.unioviscope.business.common.faceRecognition.FaceRecognizer;
import com.robert.java.unioviscope.business.student.StudentService;
import com.robert.java.unioviscope.business.teacher.attendanceProcess.AttendanceProcess;
import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.SessionToken;
import com.robert.java.unioviscope.model.Student;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.dto.AttendanceCertificate;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.exception.StudentManagementException;
import com.robert.java.unioviscope.model.types.GroupType;
import com.robert.java.unioviscope.persistence.AttendanceRepository;
import com.robert.java.unioviscope.persistence.SessionRepository;
import com.robert.java.unioviscope.persistence.SessionTokenRepository;
import com.robert.java.unioviscope.persistence.StudentRepository;
import com.robert.java.unioviscope.persistence.SubjectRepository;

/**
 * Clase que implementa la interfaz StudentService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.student.StudentService
 */
@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private AttendanceProcess attendanceProcess;
	@Autowired
	private SessionTokenRepository sessionTokenRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private AttendanceRepository attendanceRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private SessionRepository sessionRepository;
	@Value("${api.session.token.valability.seconds}")
	private Long sessionTokenValability;

	@Override
	public Boolean verifyAttendanceCertificate(AttendanceCertificate attendanceCertificate) {
		SessionToken sessionToken = sessionTokenRepository.findByToken(attendanceCertificate.getToken());

		if (sessionToken == null)
			return false;

		Date generated = sessionToken.getGenerated();
		Long scanned = attendanceCertificate.getScanned();
		Long difference = (scanned - generated.getTime()) / 1000;

		if (difference > sessionTokenValability)
			return false;

		return true;
	}

	@Override
	public Boolean certifyAttendance(AttendanceCertificate attendanceCertificate, InputStream image)
			throws BusinessException {

		SessionToken sessionToken = sessionTokenRepository.findByToken(attendanceCertificate.getToken());

		if (sessionToken == null)
			return false;

		Session session = sessionToken.getSession();
		FaceRecognizer process = null;

		if (session != null)
			process = attendanceProcess.getProcess(session.getId());

		try {
			if (image != null && process != null && process.recognize(image) != -1)
				return false;
		} catch (IOException e) {
			throw new StudentManagementException("image", "student.certifyAttendance.io.exception");
		}

		Student student = studentRepository.findByUserName(attendanceCertificate.getUserName());

		if (student != null && session != null
				&& attendanceRepository.findByStudentIdAndSessionId(student.getId(), session.getId()) != null)
			return false;

		Attendance attendance = new Attendance(student, session, new Date());

		if (image == null)
			attendance.setFaceRecognitionOff(true);

		attendanceRepository.save(attendance);
		return true;
	}

	@Override
	public List<Subject> findLastYearSubjects(Long studentId) {
		return subjectRepository.findLastYearByStudentId(studentId);
	}

	@Override
	public List<Session> findLastYearSubjectSessions(Long studentId, Long subjectId, GroupType groupType) {
		return sessionRepository.findLastYearByStudentIdSubjectIdAndGroupType(studentId, subjectId, groupType);
	}

	@Override
	public Attendance findStudentSessionAttendance(Long studentId, Long sessionId) {
		return attendanceRepository.findByStudentIdAndSessionId(studentId, sessionId);
	}
}
