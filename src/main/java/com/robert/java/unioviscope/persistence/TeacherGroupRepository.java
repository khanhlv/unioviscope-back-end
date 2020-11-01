package com.robert.java.unioviscope.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.robert.java.unioviscope.model.TeacherGroup;
import com.robert.java.unioviscope.model.types.key.TeacherGroupKey;

/**
 * Interfaz que extiende la interfaz GenericRepository y define las operaciones
 * de acceso a datos específicas para la entidad "TeacherGroup" (docente
 * asignado a grupo).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.persistence.GenericRepository
 */
@Repository
@Transactional(readOnly = true)
public interface TeacherGroupRepository extends GenericRepository<TeacherGroup, TeacherGroupKey> {

	/**
	 * Método que devuelve la asignación de un docente a un grupo asociada a un
	 * determinado docente y un determinado grupo.
	 * 
	 * @param userName
	 *            el nombre de usuario del docente.
	 * @param groupId
	 *            el id del grupo.
	 * @return la asignación del docente al grupo.
	 */
	TeacherGroup findByTeacherUserNameAndGroupId(String userName, Long groupId);
}