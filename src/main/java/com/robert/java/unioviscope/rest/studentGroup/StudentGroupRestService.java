package com.robert.java.unioviscope.rest.studentGroup;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.robert.java.unioviscope.model.StudentGroup;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.types.GroupType;

/**
 * Interfaz que define las operaciones REST específicas para la entidad
 * "StudentGroup" (estudiante asignado a grupo).
 * 
 * @author Robert Ene
 */
public interface StudentGroupRestService {

	/**
	 * Método que importa las asignaciones de estudiantes a grupos de un fichero
	 * de importación y que, si se da el caso, devuelve otro fichero que
	 * contiene los errores de formato detectados en dicho fichero de
	 * importación.
	 * 
	 * @param studentGroups
	 *            el fichero que se importa.
	 * @return el fichero que contiene los errores de formato detectados en el
	 *         fichero de importación.
	 */
	@PostMapping("import")
	@ResponseBody
	ResponseEntity<byte[]> importStudentGroups(@RequestPart("studentGroups") MultipartFile studentGroups)
			throws BusinessException;

	/**
	 * Método que devuelve la asignación de un estudiante a un grupo asociada a
	 * un determinado estudiante, un determinado tipo de grupo, un determinado
	 * curso y una determinada asignatura.
	 * 
	 * @param studentId
	 *            el id del estudiante.
	 * @param groupType
	 *            el tipo de grupo.
	 * @param courseId
	 *            el id del curso.
	 * @param subjectId
	 *            el id de la asignatura.
	 * @return la asignación del estudiante al grupo.
	 */
	@GetMapping("findByStudentAndGroupTypeAndCourseAndSubject")
	@ResponseBody
	StudentGroup findByStudentAndGroupTypeAndCourseSubject(@RequestParam("studentId") Long studentId,
			@RequestParam("groupType") GroupType groupType, @RequestParam("courseId") Long courseId,
			@RequestParam("subjectId") Long subjectId);
}
