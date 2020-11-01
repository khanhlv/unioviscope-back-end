package com.robert.java.unioviscope.business.courseSubject.validator;

import com.robert.java.unioviscope.business.common.validator.GenericValidator;
import com.robert.java.unioviscope.model.CourseSubject;
import com.robert.java.unioviscope.model.exception.CourseSubjectManagementException;
import com.robert.java.unioviscope.model.types.key.CourseSubjectKey;

/**
 * Interfaz que extiende la interfaz GenericValidator y define las validaciones
 * de las operaciones específicas para la entidad "CourseSubject" (curso
 * académico).
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.validator.GenericValidator
 */
public interface CourseSubjectValidator
		extends GenericValidator<CourseSubject, CourseSubjectKey, CourseSubjectManagementException> {

	void deleteByYearAndSyllabus(Long year, Long syllabusId) throws CourseSubjectManagementException;

}
