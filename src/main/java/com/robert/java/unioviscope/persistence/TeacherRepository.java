package com.robert.java.unioviscope.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.robert.java.unioviscope.model.Teacher;

/**
 * Interfaz que extiende la interfaz GenericRepository y define las operaciones
 * de acceso a datos específicas para la entidad "Teacher" (docente).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.persistence.GenericRepository
 */
@Repository
@Transactional(readOnly = true)
public interface TeacherRepository extends GenericRepository<Teacher, Long> {

	/**
	 * Método que devuelve un docente asociado a determinado un nombre de
	 * usuario.
	 * 
	 * @param userName
	 *            el nombre de usuario del docente.
	 * @return el docente asociado.
	 */
	Teacher findByUserName(String userName);
}