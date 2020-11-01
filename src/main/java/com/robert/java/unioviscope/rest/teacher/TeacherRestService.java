package com.robert.java.unioviscope.rest.teacher;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.robert.java.unioviscope.model.Attendance;
import com.robert.java.unioviscope.model.Group;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.dto.AttendanceDto;
import com.robert.java.unioviscope.model.dto.AttendanceOptions;
import com.robert.java.unioviscope.model.exception.BusinessException;

/**
 * Interfaz que define las operaciones REST específicas para el rol "Docente".
 * 
 * @author Robert Ene
 */
@RequestMapping("teacher")
public interface TeacherRestService {

	/**
	 * Método que devuelve las asignaturas asociadas a un determinado docente
	 * para el último curso académico.
	 * 
	 * @param teacherId
	 *            el id del docente.
	 * @return la lista de asignaturas.
	 */
	@GetMapping("findLastYearSubjects")
	@ResponseBody
	List<Subject> findLastYearSubjects(@RequestParam("teacherId") Long teacherId);

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
	@GetMapping("findLastYearSubjectGroups")
	@ResponseBody
	List<Group> findLastYearSubjectGroups(@RequestParam("teacherId") Long teacherId,
			@RequestParam("subjectId") Long subjectId);

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
	@GetMapping("findLastYearSubjectGroupSessions")
	@ResponseBody
	List<Session> findLastYearSubjectGroupSessions(@RequestParam("teacherId") Long teacherId,
			@RequestParam("subjectId") Long subjectId, @RequestParam("groupId") Long groupId);

	/**
	 * Método que devuelve las localizaciones (aulas) para una determinada
	 * asignatura.
	 * 
	 * @param subjectId
	 *            el id de la asignatura.
	 * @return la lista de localizaciones.
	 */
	@GetMapping("findLocationsBySubject")
	@ResponseBody
	List<String> findLocationsBySubject(@RequestParam("subjectId") Long subjectId);

	/**
	 * Método que crea y devuelve un código QR para una determinada sesión
	 * durante el proceso de confirmación de asistencias.
	 * 
	 * @param sessionId
	 *            el id de la sesión.
	 * @return la imagen del código QR que representa el token para la sesión.
	 */
	@GetMapping("createQrCodeForSession")
	@ResponseBody
	ResponseEntity<byte[]> createQrCodeForSession(@RequestParam("sessionId") Long sessionId) throws BusinessException;

	/**
	 * Método que detiene el proceso de confirmación de asistencias para una
	 * determinada sesión.
	 * 
	 * @param sessionId
	 *            el id de la sesión.
	 * @return true si el proceso de confirmación de asistencias ha sido
	 *         detenido, false de lo contrario.
	 */
	@GetMapping("stopQrCodeProcessForSession")
	@ResponseBody
	Boolean stopQrCodeProcessForSession(@RequestParam("sessionId") Long sessionId);

	/**
	 * Método que devuelve el número de asistencias para una determinada sesión.
	 * 
	 * @param sessionId
	 *            el id de la sesión.
	 * @return el número de asistencias asociadas a la sesión establecida.
	 */
	@GetMapping("findAttendancesNoBySession")
	@ResponseBody
	Long findAttendancesNoBySession(@RequestParam("sessionId") Long sessionId);

	/**
	 * Método que exporta las asistencias asociadas a unos determinados
	 * parámetros (formato del fichero de exportación, el id del docente, el id
	 * de la asignatura, el id del grupo y el id de la sesión).
	 * 
	 * @param attendanceOptions
	 *            los parámetros de las asistencias.
	 * @return el fichero que representa las asistencias solicitadas.
	 */
	@PostMapping("exportAttendances")
	@ResponseBody
	ResponseEntity<byte[]> exportAttendances(@RequestBody AttendanceOptions attendanceOptions) throws BusinessException;

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
	@PostMapping("findAttendances")
	@ResponseBody
	List<Attendance> findAttendances(@RequestBody AttendanceOptions attendanceOptions);

	/**
	 * Método que actualiza parcialmente los datos de una determinada asistencia.
	 * 
	 * @param attendance
	 *            la asistencia a modificar.
	 * @return la asistencia con los datos modificados.
	 */
	@PutMapping("updateAttendance")
	@ResponseBody
	Attendance updateAttendance(@RequestBody AttendanceDto attendance) throws BusinessException;
}
