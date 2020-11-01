package com.robert.java.unioviscope.rest.student;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.dto.AttendanceCertificate;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.types.GroupType;

/**
 * Interfaz que define las operaciones REST específicas para el rol
 * "Estudiante".
 * 
 * @author Robert Ene
 */
@RequestMapping("student")
public interface StudentRestService {

	/**
	 * Método que valida el certificado de una confirmación de asistencia.
	 * 
	 * @param attendanceCertificate
	 *            el certificado de la confirmación de asistencia.
	 * @return true si el certificado es válido, false de lo contrario.
	 */
	@PostMapping("verifyAttendanceCertificate")
	@ResponseBody
	Boolean verifyAttendanceCertificate(@RequestBody AttendanceCertificate attendanceCertificate);

	/**
	 * Método que cerifica la asistencia de un estudiante para una determinada
	 * sesión.
	 * 
	 * @param certificate
	 *            el certificado de la asistencia.
	 * @param image
	 *            la imagen con el rostro del estudiante para el proceso de
	 *            reconocimiento facial.
	 * @return true si el estudiante ha superado el proceso de confirmación de
	 *         asistencia, false de lo contrario.
	 */
	@PostMapping("certifyAttendance")
	@ResponseBody
	Boolean certifyAttendance(@RequestPart("certificate") AttendanceCertificate certificate,
			@RequestPart("image") MultipartFile image) throws BusinessException;

	/**
	 * Método que devuelve las asignaturas asociadas a un determinado estudiante
	 * para el último curso académico.
	 * 
	 * @param studentId
	 *            el id del estudiante.
	 * @return la lista de asignaturas.
	 */
	@GetMapping("findLastYearSubjects")
	@ResponseBody
	List<Subject> findLastYearSubjects(@RequestParam("studentId") Long studentId);

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
	@GetMapping("findLastYearSubjectSessions")
	@ResponseBody
	List<Session> findLastYearSubjectSessions(@RequestParam("studentId") Long studentId,
			@RequestParam("subjectId") Long subjectId, @RequestParam("groupType") GroupType groupType);

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
	@GetMapping("findStudentSessionAttendance")
	@ResponseBody
	Attendance findStudentSessionAttendance(@RequestParam("studentId") Long studentId,
			@RequestParam("sessionId") Long sessionId);
}
