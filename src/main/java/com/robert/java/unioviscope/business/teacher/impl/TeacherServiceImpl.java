package com.robert.java.unioviscope.business.teacher.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.robert.java.unioviscope.business.teacher.TeacherService;
import com.robert.java.unioviscope.business.teacher.attendanceProcess.AttendanceProcess;
import com.robert.java.unioviscope.business.teacher.exporter.AttendanceExporter;
import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.Group;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.SessionToken;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.dto.AttendanceOptions;
import com.robert.java.unioviscope.model.exception.ExportFileException;
import com.robert.java.unioviscope.model.exception.TeacherManagementException;
import com.robert.java.unioviscope.persistence.AttendanceRepository;
import com.robert.java.unioviscope.persistence.GroupRepository;
import com.robert.java.unioviscope.persistence.SessionRepository;
import com.robert.java.unioviscope.persistence.SessionTokenRepository;
import com.robert.java.unioviscope.persistence.SubjectRepository;

/**
 * Clase que implementa la interfaz TeacherService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.teacher.TeacherService
 */
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private SessionTokenRepository sessionTokenRepository;
	@Autowired
	private AttendanceRepository attendanceRepository;
	@Autowired
	private AttendanceProcess attendanceProcess;
	@Autowired
	@Qualifier("XLSX")
	private AttendanceExporter attendanceExporter;

	@Override
	public List<Subject> findLastYearSubjects(Long teacherId) {
		return subjectRepository.findLastYearByTeacherId(teacherId);
	}

	@Override
	public List<Group> findLastYearSubjectGroups(Long teacherId, Long subjectId) {
		return groupRepository.findLastYearByTeacherIdAndSubjectId(teacherId, subjectId);
	}

	@Override
	public List<Session> findLastYearSubjectGroupSessions(Long teacherId, Long subjectId, Long groupId) {
		return sessionRepository.findLastYearByTeacherIdSubjectIdAndGroupId(teacherId, subjectId, groupId);
	}

	@Override
	public List<String> findLocationsBySubject(Long subjectId) {
		return sessionRepository.findLocationsBySubjectId(subjectId);
	}

	@Override
	public OutputStream createQrCodeForSession(Long sessionId) throws TeacherManagementException {

		if (attendanceProcess.getProcess(sessionId) == null)
			attendanceProcess.startProcess(sessionId);

		String token = null;
		do {
			token = randomToken();
		} while (sessionTokenRepository.findByToken(token) != null);

		SessionToken sessionToken = new SessionToken(token);
		Session session = sessionRepository.findOne(sessionId);
		session.addToken(sessionToken);
		sessionTokenRepository.save(sessionToken);

		try {
			return generateQrCode(token);
		} catch (WriterException | IOException e) {
			throw new TeacherManagementException("token", "teacher.createQrCodeForSession.token.exception");
		}
	}

	@Override
	public Boolean stopQrCodeProcessForSession(Long sessionId) {
		if (attendanceProcess.stopProcess(sessionId) != null) {
			sessionTokenRepository.deleteBySessionId(sessionId);
			return true;
		}

		return false;
	}

	@Override
	public List<Attendance> findAttendancesBySession(Long sessionId) {
		return attendanceRepository.findBySessionId(sessionId);
	}

	@Override
	public OutputStream exportAttendances(AttendanceOptions attendanceOptions)
			throws ExportFileException, TeacherManagementException {
		if (attendanceOptions.getFormat() == null || attendanceOptions.getTeacherId() == null
				|| attendanceOptions.getSubjectId() == null || attendanceOptions.getGroupId() == null
				|| attendanceOptions.getSessionId() == null)
			throw new TeacherManagementException("attendanceOptions",
					"teacher.exportAttendances.attendanceOptions.fields.required");

		try {
			return attendanceExporter.exportFile(attendanceOptions);
		} catch (RuntimeException e) {
			throw new ExportFileException("file", "api.export.file.runtime.exception");
		} catch (IOException e) {
			throw new ExportFileException("file", "api.export.file.io.exception");
		}
	}

	@Override
	public List<Attendance> findAttendances(AttendanceOptions aO) {
		Long subjectId = aO.getSubjectId();
		Long groupId = aO.getGroupId();
		Long sessionId = aO.getSessionId();

		if (subjectId != null && groupId != null && sessionId != null) {
			if (groupId == -1 && sessionId == -1) {
				return attendanceRepository.findBySubjectId(subjectId);
			} else if (groupId != -1 && sessionId == -1) {
				return attendanceRepository.findBySubjectIdAndGroupId(subjectId, groupId);
			} else {
				return attendanceRepository.findBySubjectIdAndGroupIdAndSessionId(subjectId, groupId, sessionId);
			}
		}

		return null;
	}

	private String randomToken() {
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 20; i++) {
			char c = (char) ((r.nextInt(128 - 32)) + 32);
			sb.append(c);
		}
		return sb.toString();
	}

	private OutputStream generateQrCode(String token) throws WriterException, IOException {
		QRCodeWriter qrWriter = new QRCodeWriter();
		Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 0);
		BitMatrix matrix = qrWriter.encode(token, BarcodeFormat.QR_CODE, 750, 750, hints);
		int matrixWidth = matrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();

		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		graphics.setColor(Color.BLACK);

		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (matrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}

		OutputStream qrCode = new ByteArrayOutputStream();
		ImageIO.write(image, "png", qrCode);
		return qrCode;
	}
}
