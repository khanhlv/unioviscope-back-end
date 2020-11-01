package com.robert.java.unioviscope.business.teacher;

import java.io.OutputStream;
import java.util.List;

import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.Group;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.dto.AttendanceOptions;
import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz que define los servicios específicos para el rol "Docente".
 * 
 * @author Robert Ene
 */
public interface TeacherService {

	/**
	 * Método que devuelve las asignaturas asociadas a un determinado docente
	 * para el último curso académico.
	 * 
	 * @param teacherId
	 *            el id del docente.
	 * @return la lista de asignaturas.
	 */
	List<Subject> findLastYearSubjects(Long teacherId);

	/**
	 * Método que devuelve los grupos asociados a una determinada asignatura y
	 * un determinado docente, para el último curso académico.
	 * 
	 * @param teacherId
	 *            el id del docente.
	 * @param subjectId
	 *            el id de la asignatura.
	 * @return la lista de grupos.
	 */
	List<Group> findLastYearSubjectGroups(Long teacherId, Long subjectId);

	/**
	 * Método que devuelve las sesiones asociadas a un determinado grupo, una
	 * determinada asignatura y un determinado docente, para el último curso
	 * académico.
	 * 
	 * @param teacherId
	 *            el id del docente.
	 * @param subjectId
	 *            el id de la asignatura.
	 * @param groupId
	 *            el id del grupo.
	 * @return la lista de sesiones.
	 */
	List<Session> findLastYearSubjectGroupSessions(Long teacherId, Long subjectId, Long groupId);

	/**
	 * Método que devuelve las localizaciones (aulas) para una determinada
	 * asignatura.
	 * 
	 * @param subjectId
	 *            el id de la asignatura.
	 * @return la lista de localizaciones.
	 */
	List<String> findLocationsBySubject(Long subjectId);

	/**
	 * Método que crea y devuelve un código QR para una determinada sesión
	 * durante el proceso de confirmación de asistencias.
	 * 
	 * @param sessionId
	 *            el id de la sesión.
	 * @return la imagen del código QR que representa el token para la sesión.
	 */
	OutputStream createQrCodeForSession(Long sessionId) throws BusinessException;

	/**
	 * Método que detiene el proceso de confirmación de asistencias para una
	 * determinada sesión.
	 * 
	 * @param sessionId
	 *            el id de la sesión.
	 * @return true si el proceso de confirmación de asistencias ha sido
	 *         detenido, false de lo contrario.
	 */
	Boolean stopQrCodeProcessForSession(Long sessionId);

	/**
	 * Método que devuelve el número de asistencias para una determinada sesión.
	 * 
	 * @param sessionId
	 *            el id de la sesión.
	 * @return el número de asistencias asociadas a la sesión establecida.
	 */
	List<Attendance> findAttendancesBySession(Long sessionId);

	/**
	 * Método que exporta las asistencias asociadas a unos determinados
	 * parámetros (formato del fichero de exportación, el id del docente, el id
	 * de la asignatura, el id del grupo y el id de la sesión).
	 * 
	 * @param attendanceOptions
	 *            los parámetros de las asistencias.
	 * @return el fichero que representa las asistencias solicitadas.
	 */
	OutputStream exportAttendances(AttendanceOptions attendanceOptions) throws BusinessException;

	/**
	 * Método que devuelve las asistencias asociadas a unos determinados
	 * parámetros (el id del docente, el id de la asignatura, el id del grupo y
	 * el id de la sesión).
	 * 
	 * @param attendanceOptions
	 *            los parámetros de las asistencias.
	 * @return la lista de asistencias que cumplen con los parámetros
	 *         establecidos.
	 */
	List<Attendance> findAttendances(AttendanceOptions attendanceOptions);
}
