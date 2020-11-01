package com.robert.java.unioviscope.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.types.key.AttendanceKey;

/**
 * Interfaz que extiende la interfaz GenericRepository y define las operaciones
 * de acceso a datos específicas para la entidad "Attendance" (asistencia).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.persistence.GenericRepository
 */
@Repository
@Transactional(readOnly = true)
public interface AttendanceRepository extends GenericRepository<Attendance, AttendanceKey> {

	/**
	 * Método que devuelve las asistencias asociadas a una determinada sesión.
	 * 
	 * @param sessionId
	 *            el id de la sesión.
	 * @return la lista de asistencias.
	 */
	List<Attendance> findBySessionId(Long sessionId);

	/**
	 * Método que devuelve las asistencias asociadas a las sesiones de un
	 * determinado grupo.
	 * 
	 * @param groupId
	 *            el id del grupo.
	 * @return la lista de asistencias.
	 */
	List<Attendance> findBySessionGroupId(Long groupId);

	/**
	 * Método que devuelve las asistencias asociadas a un determinado estudiante
	 * y un determinado grupo.
	 * 
	 * @param studentId
	 *            el id del estudiante.
	 * @param groupId
	 *            el id del grupo.
	 * @return la lista de asistencias.
	 */
	List<Attendance> findByStudentIdAndGroupId(Long studentId, Long groupId);

	/**
	 * Método que devuelve las asistencias asociadas a las sesiones
	 * pertenecientes a un grupo que a su vez pertenece a una determinada
	 * asignatura.
	 * 
	 * @param subjectId
	 *            el id de la asignatura.
	 * @return la lista de asistencias.
	 */
	List<Attendance> findBySubjectId(Long subjectId);

	/**
	 * Método que devuelve las asistencias de las sesiones asociadas a un
	 * determinado grupo y una determinada asignatura.
	 * 
	 * @param subjectId
	 *            el id de la asignatura.
	 * @param groupId
	 *            el id del grupo.
	 * @return la lista de asistencias.
	 */
	List<Attendance> findBySubjectIdAndGroupId(Long subjectId, Long groupId);

	/**
	 * Método que devuelve las asistencias asociadas a una determinada sesión
	 * perteneciente a un determinado grupo que a su vez pertenece a una
	 * determinada asignatura.
	 * 
	 * @param subjectId
	 *            el id de la asignatura.
	 * @param groupId
	 *            el id del grupo.
	 * @param sessionId
	 *            el id de la sesión.
	 * @return la lista de asistencias.
	 */
	List<Attendance> findBySubjectIdAndGroupIdAndSessionId(Long subjectId, Long groupId, Long sessionId);

	/**
	 * Método que devuelve la asistencia asociada a una determinada sesión y un
	 * determinado estudiante.
	 * 
	 * @param studentId
	 *            el id del estudiante.
	 * @param sessionId
	 *            el id de la sesión.
	 * @return la asistencia asociada.
	 */
	Attendance findByStudentIdAndSessionId(Long studentId, Long sessionId);
}