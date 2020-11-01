package com.robert.java.unioviscope.business.teacher.exporter.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.teacher.exporter.AttendanceExporter;
import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.Group;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.Student;
import com.robert.java.unioviscope.model.dto.AttendanceOptions;
import com.robert.java.unioviscope.persistence.AttendanceRepository;
import com.robert.java.unioviscope.persistence.GroupRepository;
import com.robert.java.unioviscope.persistence.SessionRepository;
import com.robert.java.unioviscope.persistence.StudentRepository;

/**
 * Clase que implementa la interfaz AttendanceExporter.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.teacher.exporter.AttendanceExporter
 */
@Component("XLSX")
public class XlsxAttendanceExporter implements AttendanceExporter {

	@Autowired
	private AttendanceRepository attendanceRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private SessionRepository sessionRepository;

	private XSSFWorkbook workbook;
	private CreationHelper creationHelper;
	private XSSFFont boldFont;

	@Override
	public OutputStream exportFile(AttendanceOptions aO) throws IOException {
		workbook = new XSSFWorkbook();
		creationHelper = workbook.getCreationHelper();
		boldFont = workbook.createFont();
		boldFont.setBold(true);

		if (aO.getGroupId() == -1 && aO.getSessionId() == -1) {
			List<Group> groups = groupRepository.findLastYearByTeacherIdAndSubjectId(aO.getTeacherId(),
					aO.getSubjectId());

			for (Group group : groups) {
				List<Session> sessions = sessionRepository.findByGroupIdOrderByStartAsc(group.getId());
				getAttendances(group, sessions);
			}
		} else if (aO.getGroupId() != -1 && aO.getSessionId() == -1) {
			Group group = groupRepository.findOne(aO.getGroupId());
			List<Session> sessions = sessionRepository.findByGroupIdOrderByStartAsc(group.getId());
			getAttendances(group, sessions);
		} else {
			List<Session> sessions = new ArrayList<>();
			sessions.add(sessionRepository.findOne(aO.getSessionId()));
			Group group = groupRepository.findOne(aO.getGroupId());
			getAttendances(group, sessions);
		}

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		outputStream.close();
		workbook.close();

		return outputStream;
	}

	private void getAttendances(Group group, List<Session> sessions) {
		List<Student> students = studentRepository.findByGroupId(group.getId());

		if (!attendanceRepository.findBySessionGroupId(group.getId()).isEmpty()) {
			XSSFSheet sheet = workbook.createSheet(group.getCode());
			int rowNo = 0;
			Row row = sheet.createRow(rowNo++);

			setHeaders(sessions, row);

			for (Student student : students) {
				row = sheet.createRow(rowNo++);
				int colNum = 0;
				Cell cell = row.createCell(colNum++);
				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setFont(boldFont);
				cell.setCellValue(student.getLastName().concat(", ").concat(student.getFirstName()));
				cell.setCellStyle(cellStyle);

				List<Attendance> attendances = attendanceRepository.findByStudentIdAndGroupId(student.getId(),
						group.getId());

				Integer totalAttendances = 0;

				for (int i = 0; i < sessions.size(); i++) {
					cell = row.createCell(colNum++);
					cell.setCellType(CellType.STRING);
					cell.setCellStyle(getAttendanceCellStyle());

					Attendance attendance = getAttendance(sessions.get(i), attendances);
					
					if (attendance != null) {
						if (attendance.isConfirmed() != null && attendance.isConfirmed()) {
							cell.setCellValue("X");
							totalAttendances++;
						} else {
							cell.setCellValue("-");
						}
					} else {
						cell.setCellValue("-");
					}
				}
				cell = row.createCell(colNum++);
				cell.setCellType(CellType.NUMERIC);
				cellStyle.setAlignment(HorizontalAlignment.RIGHT);
				cellStyle.setFont(boldFont);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(totalAttendances);
			}
			autoSizeColumn(sheet);
		}
	}

	private Attendance getAttendance(Session session, List<Attendance> attendances) {
		for (Attendance attendance : attendances)
			if (attendance != null && attendance.getSession().equals(session))
				return attendance;

		return null;
	}

	private void setHeaders(List<Session> sessions, Row row) {
		int colNum = 1;
		for (Session session : sessions) {
			Cell cell = row.createCell(colNum++);
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(SESSION_DATE_FORMAT));
			cellStyle.setFont(boldFont);
			cellStyle.setAlignment(HorizontalAlignment.RIGHT);
			cell.setCellValue(session.getStart());
			cell.setCellStyle(cellStyle);
		}

		Cell cell = row.createCell(colNum++);
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(boldFont);
		cellStyle.setAlignment(HorizontalAlignment.RIGHT);
		cell.setCellValue("TOTAL");
		cell.setCellStyle(cellStyle);
	}

	private CellStyle getAttendanceCellStyle() {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		return cellStyle;
	}

	private void autoSizeColumn(XSSFSheet sheet) {
		XSSFRow row = sheet.getRow(0);
		for (int column = 0; column < row.getLastCellNum(); column++)
			sheet.autoSizeColumn(column);
	}
}
