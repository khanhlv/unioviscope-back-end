package com.robert.java.unioviscope.business.teacher.attendanceProcess;

import com.robert.java.unioviscope.business.common.faceRecognition.FaceRecognizer;
import com.robert.java.unioviscope.model.exception.TeacherManagementException;

/**
 * Interfaz que define las operaciones para el mantenimiento del proceso de
 * confirmación de asistencias para una determinada sesión.
 * 
 * @author Robert Ene
 */
public interface AttendanceProcess {

	/**
	 * Método que inicializa el proceso de confirmación de asistencias para una
	 * determinada sesión.
	 * 
	 * @param sessionId
	 *            el id de la sesión.
	 */
	void startProcess(Long sessionId) throws TeacherManagementException;

	/**
	 * Método que detiene el proceso de confirmación de asistencias para una
	 * determinada sesión.
	 * 
	 * @param sessionId
	 *            el id de la sesión.
	 * @return el reconocedor de rostros.
	 */
	FaceRecognizer stopProcess(Long sessionId);

	/**
	 * Método que devuelve el reconocedor de rostros para un proceso de
	 * confirmación de asistencias para una determinada sesión.
	 * 
	 * @param sessionId
	 *            el id de la sesión.
	 * @return el reconocedor de rostros.
	 */
	FaceRecognizer getProcess(Long sessionId);
}
