package com.robert.java.unioviscope.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.types.GroupType;

/**
 * Interfaz que extiende la interfaz GenericRepository y define las operaciones
 * de acceso a datos específicas para la entidad "Session" (sesión).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.persistence.GenericRepository
 */
@Repository
@Transactional(readOnly = true)
public interface SessionRepository extends GenericRepository<Session, Long> {

	/**
	 * Método que devuelve las sesiones del último curso académico asociadas a
	 * un determinado docente, una determinada asignatura y un determinado
	 * grupo.
	 * 
	 * @param teacherId
	 *            el id del docente.
	 * @param subjectId
	 *            el id de la asignatura.
	 * @param groupId
	 *            el id del grupo.
	 * @return la lista de sesiones.
	 */
	List<Session> findLastYearByTeacherIdSubjectIdAndGroupId(Long teacherId, Long subjectId, Long groupId);

	/**
	 * Método que devuelve las sesiones del último curso académico asociadas a
	 * un determinado estudiante, una determinada asignatura y un determinado
	 * tipo de grupo.
	 * 
	 * @param studentId
	 *            el id del estudiante.
	 * @param subjectId
	 *            el id de la asignatura.
	 * @param groupType
	 *            el tipo de grupo.
	 * @return la lista de sesiones.
	 */
	List<Session> findLastYearByStudentIdSubjectIdAndGroupType(Long studentId, Long subjectId, GroupType groupType);

	/**
	 * Método que devuelve las ubicaciones (aulas) de las sesiones asociadas a
	 * una determinada asignatura.
	 * 
	 * @param subjectId
	 *            el id de la asignatura.
	 * @return la lista de ubicaciones (aulas).
	 */
	List<String> findLocationsBySubjectId(Long subjectId);

	/**
	 * Método que devuelve las sesiones asociadas a un determinado grupo y
	 * ordenadas por la fecha de inicio.
	 * 
	 * @param groupId
	 *            el id del grupo.
	 * @return la lista de sesiones.
	 */
	List<Session> findByGroupIdOrderByStartAsc(Long groupId);

	/**
	 * Método que devuelve las sesiones asociadas a una determinada ubicación
	 * (aula).
	 * 
	 * @param location
	 *            la ubicación (aula).
	 * @return la lista de sesiones.
	 */
	List<Session> findByLocation(String location);
}