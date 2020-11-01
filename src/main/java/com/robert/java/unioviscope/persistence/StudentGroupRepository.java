package com.robert.java.unioviscope.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.robert.java.unioviscope.model.CourseSubject;
import com.robert.java.unioviscope.model.StudentGroup;
import com.robert.java.unioviscope.model.types.GroupType;
import com.robert.java.unioviscope.model.types.key.StudentGroupKey;

/**
 * Interfaz que extiende la interfaz GenericRepository y define las operaciones
 * de acceso a datos específicas para la entidad "StudentGroup" (estudiante
 * asignado a grupo).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.persistence.GenericRepository
 */
@Repository
@Transactional(readOnly = true)
public interface StudentGroupRepository extends GenericRepository<StudentGroup, StudentGroupKey> {

	/**
	 * Método que devuelve la asignación de un estudiante a un grupo asociada a
	 * un determinado estudiante y un determinado grupo.
	 * 
	 * @param userName
	 *            el nombre de usuario del estudiante.
	 * @param groupId
	 *            el id del grupo.
	 * @return la asignación del estudiante al grupo.
	 */
	StudentGroup findByStudentUserNameAndGroupId(String userName, Long groupId);

	/**
	 * Método que devuelve todas las asignaciones a grupos asociadas a un
	 * determinado estudiante.
	 * 
	 * @param userName
	 *            el nombre de usuario del estudiante.
	 * @return la lista de asignaciones del estudiante a grupos.
	 */
	List<StudentGroup> findByStudentUserName(String userName);

	/**
	 * Método que devuelve un la asignación de un estudiante a un grupo asociada
	 * a un determinado estudiante, un determinado tipo de grupo y un
	 * determinado curso académico.
	 * 
	 * @param studentId
	 *            el id del estudiante.
	 * @param groupType
	 *            el tipo de grupo.
	 * @param subject
	 *            el curso académico.
	 * @return la asignación del estudiante al grupo.
	 */
	StudentGroup findByStudentIdAndGroupTypeAndGroupSubject(Long studentId, GroupType groupType, CourseSubject subject);
}