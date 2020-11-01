package com.robert.java.unioviscope.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.robert.java.unioviscope.model.Student;

/**
 * Interfaz que extiende la interfaz GenericRepository y define las operaciones
 * de acceso a datos específicas para la entidad "Student" (estudiante).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.persistence.GenericRepository
 */
@Repository
@Transactional(readOnly = true)
public interface StudentRepository extends GenericRepository<Student, Long> {

	/**
	 * Método que devuelve un estudiante asociado a determinado un nombre de
	 * usuario.
	 * 
	 * @param userName
	 *            el nombre de usuario del estudiante.
	 * @return el estudiante asociado.
	 */
	Student findByUserName(String userName);

	/**
	 * Método que devuelve los estudiante asociados a un determinado grupo.
	 * 
	 * @param groupId el id del grupo.
	 * @return la lista de estudiantes.
	 */
	List<Student> findByGroupId(Long groupId);
}