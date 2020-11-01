package com.robert.java.unioviscope.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.robert.java.unioviscope.model.Group;

/**
 * Interfaz que extiende la interfaz GenericRepository y define las operaciones
 * de acceso a datos específicas para la entidad "Group" (grupo).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.persistence.GenericRepository
 */
@Repository
@Transactional(readOnly = true)
public interface GroupRepository extends GenericRepository<Group, Long> {

	/**
	 * Método que devuelve los grupos del último curso académico asociados a un
	 * determinado docente y una determinada asignatura.
	 * 
	 * @param teacherId
	 *            el id del docente.
	 * @param subjectId
	 *            el id de la asignatura.
	 * @return la lista de grupos.
	 */
	List<Group> findLastYearByTeacherIdAndSubjectId(Long teacherId, Long subjectId);
}