package com.robert.java.unioviscope.business.teacher.attendanceProcess.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.common.faceRecognition.FaceRecognizer;
import com.robert.java.unioviscope.business.common.faceRecognition.impl.OpenCVFaceRecognizer;
import com.robert.java.unioviscope.business.teacher.attendanceProcess.AttendanceProcess;
import com.robert.java.unioviscope.model.exception.TeacherManagementException;

/**
 * Clase que implementa la interfaz AttendanceProcess.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.teacher.attendanceProcess.AttendanceProcess
 */
@Component
public class AttendanceProcessImpl implements AttendanceProcess {

	private Map<Long, FaceRecognizer> processes = new HashMap<>();

	public void startProcess(Long sessionId) throws TeacherManagementException {
		try {
			processes.put(sessionId, new OpenCVFaceRecognizer());
		} catch (IOException e) {
			throw new TeacherManagementException("sessionId", "teacher.attendanceProcess.startProcess");
		}
	}

	public FaceRecognizer stopProcess(Long sessionId) {
		return processes.remove(sessionId);
	}

	public FaceRecognizer getProcess(Long sessionId) {
		return processes.get(sessionId);
	}
}
