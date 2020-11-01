package com.robert.java.unioviscope.business.student;

import java.io.InputStream;
import java.util.List;

import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.dto.AttendanceCertificate;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.types.GroupType;

/**
 * Interfaz que define los servicios específicos para el rol "Estudiante".
 * 
 * @author Robert Ene
 */
public interface StudentService {

	/**
	 * Método que valida el certificado de una confirmación de asistencia.
	 * 
	 * @param attendanceCertificate
	 *            el certificado de la confirmación de asistencia.
	 * @return true si el certificado es válido, false de lo contrario.
	 */
	Boolean verifyAttendanceCertificate(AttendanceCertificate attendanceCertificate);

	/**
	 * Método que cerifica la asistencia de un estudiante para una determinada
	 * sesión.
	 * 
	 * @param attendanceCertificate
	 *            el certificado de la asistencia.
	 * @param image
	 *            la imagen con el rostro del estudiante para el proceso de
	 *            reconocimiento facial.
	 * @return true si el estudiante ha superado el proceso de confirmación de
	 *         asistencia, false de lo contrario.
	 */
	Boolean certifyAttendance(AttendanceCertificate attendanceCertificate, InputStream image) throws BusinessException;

	/**
	 * Método que devuelve las asignaturas asociadas a un determinado estudiante
	 * para el último curso académico.
	 * 
	 * @param studentId
	 *            el id del estudiante.
	 * @return la lista de asignaturas.
	 */
	List<Subject> findLastYearSubjects(Long studentId);

	/**
	 * Método que devuelve las sesiones asociadas a un determinado estudiante,
	 * una determinada asignatura y un determinado tipo de grupo, para el último
	 * curso académico.
	 * 
	 * @param studentId
	 *            el id del estudiante.
	 * @param subjectId
	 *            el id de la asignatura.
	 * @param groupType
	 *            el tipo de grupo.
	 * @return la lista de sesiones.
	 */
	List<Session> findLastYearSubjectSessions(Long studentId, Long subjectId, GroupType groupType);

	/**
	 * Método que devuelve una asistencia para una determinada sesión y un
	 * determinado estudiante.
	 * 
	 * @param studentId
	 *            el id del estudiante.
	 * @param sessionId
	 *            el id de la sesión.
	 * @return la asistencia asociada al estudiante y sesión establecidos.
	 */
	Attendance findStudentSessionAttendance(Long studentId, Long sessionId);
}
