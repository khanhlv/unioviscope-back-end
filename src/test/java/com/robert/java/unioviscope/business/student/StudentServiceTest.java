package com.robert.java.unioviscope.business.student;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.robert.java.unioviscope.business.common.faceRecognition.FaceRecognizer;
import com.robert.java.unioviscope.business.teacher.attendanceProcess.impl.AttendanceProcessImpl;
import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.SessionToken;
import com.robert.java.unioviscope.model.Student;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.dto.AttendanceCertificate;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.types.GroupType;
import com.robert.java.unioviscope.persistence.AttendanceRepository;
import com.robert.java.unioviscope.persistence.SessionRepository;
import com.robert.java.unioviscope.persistence.SessionTokenRepository;
import com.robert.java.unioviscope.persistence.StudentRepository;
import com.robert.java.unioviscope.persistence.SubjectRepository;

/**
 * Clase test para la clase StudentServiceImpl.
 *
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.student.impl.StudentServiceImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class StudentServiceTest {

	@MockBean
	private SessionTokenRepository sessionTokenRepository;
	@MockBean
	private SubjectRepository subjectRepository;
	@MockBean
	private SessionRepository sessionRepository;
	@MockBean
	private AttendanceRepository attendanceRepository;
	@MockBean
	private StudentRepository studentRepository;
	@MockBean
	private AttendanceProcessImpl attendanceProcess;
	@MockBean
	private FaceRecognizer faceRecognizer;
	
	@Autowired
	private StudentService studentService;
	@Value("${api.session.token.valability.seconds}")
	private Long sessionTokenValability;

	private List<Subject> subjects;
	private List<Session> sessions;
	private Student student;
	private Session session;
	private Subject subject;
	private Attendance attendance;

	@Before
	public void setUp() throws Exception {
		sessionTokenValability *= 1000;

		student = new Student("S1");
		student.setId(1L);

		subject = new Subject("SUB1");
		subject.setId(1L);
		subject.setDenomination("Subject 1");

		subjects = new ArrayList<>();
		subjects.add(subject);

		session = new Session(new Date(), "Loc1");
		session.setId(1L);

		sessions = new ArrayList<>();
		sessions.add(session);

		attendance = new Attendance(student, session, new Date());
	}

	@Test
	public void testVerifyAttendanceCertificate() {
		SessionToken sessionToken = new SessionToken("token");
		sessionToken.setSession(session);
		sessionToken.setGenerated(new Date());

		AttendanceCertificate attendanceCertificate = new AttendanceCertificate(student.getUserName(),
				sessionToken.getToken(), sessionToken.getGenerated().getTime() + sessionTokenValability * 2);

		when(sessionTokenRepository.findByToken(sessionToken.getToken())).thenReturn(null);
		assertThat(false, is(studentService.verifyAttendanceCertificate(attendanceCertificate)));

		when(sessionTokenRepository.findByToken(sessionToken.getToken())).thenReturn(sessionToken);

		assertThat(false, is(studentService.verifyAttendanceCertificate(attendanceCertificate)));

		attendanceCertificate.setScanned(sessionToken.getGenerated().getTime() + sessionTokenValability + 1000);
		assertThat(false, is(studentService.verifyAttendanceCertificate(attendanceCertificate)));

		attendanceCertificate.setScanned(sessionToken.getGenerated().getTime() + sessionTokenValability);
		assertThat(true, is(studentService.verifyAttendanceCertificate(attendanceCertificate)));

		attendanceCertificate.setScanned(sessionToken.getGenerated().getTime() + sessionTokenValability - 1000);
		assertThat(true, is(studentService.verifyAttendanceCertificate(attendanceCertificate)));

		verify(sessionTokenRepository, times(5)).findByToken(attendanceCertificate.getToken());
	}

	@Test
	public void testCertifyAttendance() throws BusinessException {
		SessionToken sessionToken = new SessionToken("token");
		sessionToken.setSession(session);
		sessionToken.setGenerated(new Date());

		AttendanceCertificate attendanceCertificate = new AttendanceCertificate(student.getUserName(),
				sessionToken.getToken(), sessionToken.getGenerated().getTime() + sessionTokenValability * 2);
		
		when(sessionTokenRepository.findByToken(sessionToken.getToken())).thenReturn(null);
		assertThat(false, is(studentService.certifyAttendance(attendanceCertificate, null)));
		
		when(sessionTokenRepository.findByToken(sessionToken.getToken())).thenReturn(sessionToken);
		when(attendanceProcess.getProcess(session.getId())).thenReturn(faceRecognizer);
		
		InputStream image = new ByteArrayInputStream("image".getBytes());
		assertThat(false, is(studentService.certifyAttendance(attendanceCertificate, image)));
		
		when(attendanceProcess.getProcess(session.getId())).thenReturn(null);
		when(studentRepository.findByUserName(student.getUserName())).thenReturn(student);
		when(attendanceRepository.findByStudentIdAndSessionId(student.getId(), session.getId())).thenReturn(attendance);
		assertThat(false, is(studentService.certifyAttendance(attendanceCertificate, image)));
		
		when(attendanceRepository.findByStudentIdAndSessionId(student.getId(), session.getId())).thenReturn(null);
		assertThat(true, is(studentService.certifyAttendance(attendanceCertificate, image)));
		
		verify(attendanceRepository).save(any(Attendance.class));
	}

	@Test
	public void testFindLastYearSubjects() {
		when(subjectRepository.findLastYearByStudentId(student.getId())).thenReturn(subjects);
		assertThat(subjects, is(studentService.findLastYearSubjects(student.getId())));

		subjects.remove(subject);
		assertThat(subjects, is(studentService.findLastYearSubjects(student.getId())));

		verify(subjectRepository, times(2)).findLastYearByStudentId(student.getId());
	}

	@Test
	public void testFindLastYearSubjectSessions() {
		when(sessionRepository.findLastYearByStudentIdSubjectIdAndGroupType(student.getId(), subject.getId(),
				GroupType.THEORY)).thenReturn(sessions);
		assertThat(sessions,
				is(studentService.findLastYearSubjectSessions(student.getId(), subject.getId(), GroupType.THEORY)));

		Session s = new Session(new Date(), "Loc2");
		sessions.add(s);
		assertThat(sessions,
				is(studentService.findLastYearSubjectSessions(student.getId(), subject.getId(), GroupType.THEORY)));

		verify(sessionRepository, times(2)).findLastYearByStudentIdSubjectIdAndGroupType(student.getId(),
				subject.getId(), GroupType.THEORY);
	}

	@Test
	public void testFindStudentSessionAttendance() {
		when(attendanceRepository.findByStudentIdAndSessionId(student.getId(), session.getId())).thenReturn(attendance);
		assertThat(attendance, is(studentService.findStudentSessionAttendance(student.getId(), session.getId())));

		when(attendanceRepository.findByStudentIdAndSessionId(student.getId(), session.getId())).thenReturn(null);
		assertNull(studentService.findStudentSessionAttendance(student.getId(), session.getId()));

		verify(attendanceRepository, times(2)).findByStudentIdAndSessionId(student.getId(), session.getId());
	}
}
