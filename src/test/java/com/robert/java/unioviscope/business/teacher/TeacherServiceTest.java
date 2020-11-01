package com.robert.java.unioviscope.business.teacher;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.robert.java.unioviscope.business.common.faceRecognition.FaceRecognizer;
import com.robert.java.unioviscope.business.teacher.attendanceProcess.impl.AttendanceProcessImpl;
import com.robert.java.unioviscope.business.teacher.exporter.AttendanceExporter;
import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.Course;
import com.robert.java.unioviscope.model.CourseSubject;
import com.robert.java.unioviscope.model.Group;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.SessionToken;
import com.robert.java.unioviscope.model.Student;
import com.robert.java.unioviscope.model.StudentGroup;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.Teacher;
import com.robert.java.unioviscope.model.TeacherGroup;
import com.robert.java.unioviscope.model.dto.AttendanceOptions;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.exception.ExportFileException;
import com.robert.java.unioviscope.model.exception.TeacherManagementException;
import com.robert.java.unioviscope.persistence.AttendanceRepository;
import com.robert.java.unioviscope.persistence.GroupRepository;
import com.robert.java.unioviscope.persistence.SessionRepository;
import com.robert.java.unioviscope.persistence.SessionTokenRepository;
import com.robert.java.unioviscope.persistence.SubjectRepository;

/**
 * Clase test para la clase TeacherServiceImpl.
 *
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.teacher.impl.TeacherServiceImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class TeacherServiceTest {

	@MockBean
	private SubjectRepository subjectRepository;
	@MockBean
	private GroupRepository groupRepository;
	@MockBean
	private SessionRepository sessionRepository;
	@MockBean
	private AttendanceRepository attendanceRepository;
	@MockBean
	private AttendanceProcessImpl attendanceProcess;
	@MockBean
	private FaceRecognizer faceRecognizer;
	@MockBean
	private SessionTokenRepository sessionTokenRepository;
	@MockBean
	private AttendanceExporter attendanceExporter;

	@Autowired
	private TeacherService teacherService;

	private List<Subject> subjects;
	private List<Group> groups;
	private List<Session> sessions;
	private List<Attendance> attendances;
	private Subject subject;
	private Course course;
	private CourseSubject courseSubject;
	private Group group;
	private Teacher teacher;
	private Session session;
	private Student student;

	@Before
	public void setUp() throws Exception {
		subject = new Subject("SUB1");
		subject.setId(1L);
		subject.setDenomination("Subject 1");

		subjects = new ArrayList<>();
		subjects.add(subject);

		course = new Course(2015L);
		course.setId(1L);

		courseSubject = new CourseSubject(subject, course);

		group = new Group("GR1", courseSubject);
		group.setId(1L);
		groups = new ArrayList<>();
		groups.add(group);

		teacher = new Teacher("T1");
		teacher.setId(1L);

		new TeacherGroup(teacher, group);

		session = new Session(new Date(), "Loc1");
		session.setId(1L);
		group.addSession(session);

		sessions = new ArrayList<>();
		sessions.add(session);

		student = new Student("S1");
		student.setId(1L);

		new StudentGroup(student, group);

		Attendance attendance = new Attendance(student, session, new Date());

		attendances = new ArrayList<>();
		attendances.add(attendance);
	}

	@Test
	public void contextLoads() throws Exception {
		assertNotNull(teacherService);
	}

	@Test
	public void testFindLastYearSubjects() throws Exception {
		when(subjectRepository.findLastYearByTeacherId(teacher.getId())).thenReturn(subjects);
		assertThat(subjects, is(teacherService.findLastYearSubjects(teacher.getId())));

		subjects.remove(subject);
		assertThat(subjects, is(teacherService.findLastYearSubjects(teacher.getId())));

		verify(subjectRepository, times(2)).findLastYearByTeacherId(teacher.getId());
	}

	@Test
	public void testFindLastYearSubjectGroups() {
		when(groupRepository.findLastYearByTeacherIdAndSubjectId(teacher.getId(), subject.getId())).thenReturn(groups);
		assertThat(groups, is(teacherService.findLastYearSubjectGroups(teacher.getId(), subject.getId())));

		Group g = new Group("GR2", courseSubject);
		new TeacherGroup(teacher, g);
		groups.add(g);
		assertThat(groups, is(teacherService.findLastYearSubjectGroups(teacher.getId(), subject.getId())));

		verify(groupRepository, times(2)).findLastYearByTeacherIdAndSubjectId(teacher.getId(), subject.getId());
	}

	@Test
	public void testFindLastYearSubjectGroupSessions() {
		when(sessionRepository.findLastYearByTeacherIdSubjectIdAndGroupId(teacher.getId(), subject.getId(),
				group.getId())).thenReturn(sessions);
		assertThat(sessions,
				is(teacherService.findLastYearSubjectGroupSessions(teacher.getId(), subject.getId(), group.getId())));

		sessions.remove(session);
		assertThat(sessions,
				is(teacherService.findLastYearSubjectGroupSessions(teacher.getId(), subject.getId(), group.getId())));

		verify(sessionRepository, times(2)).findLastYearByTeacherIdSubjectIdAndGroupId(teacher.getId(), subject.getId(),
				group.getId());
	}

	@Test
	public void testFindLocationsBySubject() {
		List<String> locations = new ArrayList<>();
		locations.add("Loc1");
		locations.add("Loc2");

		when(sessionRepository.findLocationsBySubjectId(subject.getId())).thenReturn(locations);
		assertThat(locations, is(teacherService.findLocationsBySubject(subject.getId())));

		locations.add("Loc3");
		assertThat(locations, is(teacherService.findLocationsBySubject(subject.getId())));

		verify(sessionRepository, times(2)).findLocationsBySubjectId(subject.getId());
	}

	@Test
	public void testCreateQrCodeForSession() throws Exception {
		when(attendanceProcess.getProcess(session.getId())).thenReturn(faceRecognizer);

		SessionToken sessionToken = new SessionToken("token");
		sessionToken.setSession(session);

		when(sessionTokenRepository.findByToken(sessionToken.getToken())).thenReturn(sessionToken);
		when(sessionRepository.findOne(session.getId())).thenReturn(session);

		assertNotNull(teacherService.createQrCodeForSession(session.getId()));
		verify(sessionTokenRepository).save(any(SessionToken.class));

		when(attendanceProcess.getProcess(session.getId())).thenReturn(null);
		assertNotNull(teacherService.createQrCodeForSession(session.getId()));
		verify(attendanceProcess).startProcess(session.getId());

	}

	@Test
	public void testStopQrCodeProcessForSession() {
		when(attendanceProcess.stopProcess(session.getId())).thenReturn(faceRecognizer);
		assertThat(true, is(teacherService.stopQrCodeProcessForSession(session.getId())));
		verify(sessionTokenRepository).deleteBySessionId(session.getId());

		when(attendanceProcess.stopProcess(session.getId())).thenReturn(null);
		assertThat(false, is(teacherService.stopQrCodeProcessForSession(session.getId())));
		verify(sessionTokenRepository).deleteBySessionId(session.getId());
	}

	@Test
	public void testFindAttendancesBySession() {
		when(attendanceRepository.findBySessionId(session.getId())).thenReturn(attendances);
		assertThat(attendances, is(teacherService.findAttendancesBySession(session.getId())));

		Attendance a = new Attendance(null, session, new Date());
		attendances.add(a);
		assertThat(attendances, is(teacherService.findAttendancesBySession(session.getId())));

		verify(attendanceRepository, times(2)).findBySessionId(session.getId());
	}

	@Test(expected = TeacherManagementException.class)
	public void testExportAttendancesEmpty() throws BusinessException {
		AttendanceOptions attendanceOptions = new AttendanceOptions();
		assertNull(teacherService.exportAttendances(attendanceOptions));
	}

	@Test(expected = TeacherManagementException.class)
	public void testExportAttendancesNotNullFormat() throws BusinessException {
		AttendanceOptions attendanceOptions = new AttendanceOptions();
		attendanceOptions.setFormat("FORMAT");
		assertNull(teacherService.exportAttendances(attendanceOptions));
	}

	@Test(expected = TeacherManagementException.class)
	public void testExportAttendancesNotNullTeacher() throws BusinessException {
		AttendanceOptions attendanceOptions = new AttendanceOptions();
		attendanceOptions.setFormat("FORMAT");
		attendanceOptions.setTeacherId(teacher.getId());
		assertNull(teacherService.exportAttendances(attendanceOptions));
	}

	@Test(expected = TeacherManagementException.class)
	public void testExportAttendancesNotNullSubject() throws BusinessException {
		AttendanceOptions attendanceOptions = new AttendanceOptions();
		attendanceOptions.setFormat("FORMAT");
		attendanceOptions.setTeacherId(teacher.getId());
		attendanceOptions.setSubjectId(subject.getId());
		assertNull(teacherService.exportAttendances(attendanceOptions));
	}

	@Test(expected = TeacherManagementException.class)
	public void testExportAttendancesNotNullGroup() throws BusinessException {
		AttendanceOptions attendanceOptions = new AttendanceOptions();
		attendanceOptions.setFormat("FORMAT");
		attendanceOptions.setTeacherId(teacher.getId());
		attendanceOptions.setSubjectId(subject.getId());
		attendanceOptions.setGroupId(group.getId());
		assertNull(teacherService.exportAttendances(attendanceOptions));
	}

	@Test
	public void testExportAttendances() throws Exception {
		AttendanceOptions attendanceOptions = new AttendanceOptions();
		attendanceOptions.setFormat("FORMAT");
		attendanceOptions.setTeacherId(teacher.getId());
		attendanceOptions.setSubjectId(subject.getId());
		attendanceOptions.setGroupId(group.getId());
		attendanceOptions.setSessionId(session.getId());

		OutputStream outputStream = new ByteArrayOutputStream();
		when(attendanceExporter.exportFile(attendanceOptions)).thenReturn(outputStream);

		assertThat(outputStream, is(teacherService.exportAttendances(attendanceOptions)));
		verify(attendanceExporter).exportFile(attendanceOptions);
	}

	@Test(expected = ExportFileException.class)
	public void testExportAttendancesRuntimeException() throws Exception {
		AttendanceOptions attendanceOptions = new AttendanceOptions();
		attendanceOptions.setFormat("FORMAT");
		attendanceOptions.setTeacherId(teacher.getId());
		attendanceOptions.setSubjectId(subject.getId());
		attendanceOptions.setGroupId(group.getId());
		attendanceOptions.setSessionId(session.getId());

		when(attendanceExporter.exportFile(attendanceOptions)).thenThrow(new RuntimeException());
		assertNull(teacherService.exportAttendances(attendanceOptions));
		verify(attendanceExporter).exportFile(attendanceOptions);
	}

	@Test(expected = ExportFileException.class)
	public void testExportAttendancesIOException() throws Exception {
		AttendanceOptions attendanceOptions = new AttendanceOptions();
		attendanceOptions.setFormat("FORMAT");
		attendanceOptions.setTeacherId(teacher.getId());
		attendanceOptions.setSubjectId(subject.getId());
		attendanceOptions.setGroupId(group.getId());
		attendanceOptions.setSessionId(session.getId());

		when(attendanceExporter.exportFile(attendanceOptions)).thenThrow(new IOException());
		assertNull(teacherService.exportAttendances(attendanceOptions));
		verify(attendanceExporter).exportFile(attendanceOptions);
	}

	@Test
	public void testFindAttendances() {
		AttendanceOptions attendanceOptions = new AttendanceOptions();
		assertNull(teacherService.findAttendances(attendanceOptions));
		verifyZeroInteractions(attendanceRepository);

		attendanceOptions.setSubjectId(subject.getId());
		assertNull(teacherService.findAttendances(attendanceOptions));
		verifyZeroInteractions(attendanceRepository);

		attendanceOptions.setGroupId(group.getId());
		assertNull(teacherService.findAttendances(attendanceOptions));
		verifyZeroInteractions(attendanceRepository);

		attendanceOptions.setGroupId(-1L);
		attendanceOptions.setSessionId(-1L);
		when(attendanceRepository.findBySubjectId(subject.getId())).thenReturn(attendances);

		assertThat(attendances, is(teacherService.findAttendances(attendanceOptions)));
		verify(attendanceRepository).findBySubjectId(subject.getId());

		attendanceOptions.setGroupId(group.getId());
		when(attendanceRepository.findBySubjectIdAndGroupId(subject.getId(), group.getId())).thenReturn(attendances);

		assertThat(attendances, is(teacherService.findAttendances(attendanceOptions)));
		verify(attendanceRepository).findBySubjectIdAndGroupId(subject.getId(), group.getId());

		attendanceOptions.setSessionId(session.getId());
		when(attendanceRepository.findBySubjectIdAndGroupIdAndSessionId(subject.getId(), group.getId(),
				session.getId())).thenReturn(attendances);

		assertThat(attendances, is(teacherService.findAttendances(attendanceOptions)));
		verify(attendanceRepository).findBySubjectIdAndGroupIdAndSessionId(subject.getId(), group.getId(),
				session.getId());
	}
}
