package com.robert.java.unioviscope.business.courseSubject.validator.impl;

import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.courseSubject.validator.CourseSubjectValidator;
import com.robert.java.unioviscope.model.CourseSubject;
import com.robert.java.unioviscope.model.exception.CourseSubjectManagementException;
import com.robert.java.unioviscope.model.types.key.CourseSubjectKey;

/**
 * Clase que implementa la interfaz CourseSubjectValidator.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.courseSubject.validator.CourseSubjectValidator
 */
@Component
public class CourseSubjectValidatorImpl implements CourseSubjectValidator {

	@Override
	public void findById(CourseSubjectKey id) throws CourseSubjectManagementException {
		if (id == null || id.getSubject() == null || id.getCourse() == null)
			throw new CourseSubjectManagementException("id", "courseSubject.find.id.required");
	}

	@Override
	public void save(CourseSubject courseSubject) throws CourseSubjectManagementException {
		if (courseSubject == null)
			throw new CourseSubjectManagementException("courseSubject", "courseSubject.save.courseSubject.required");
	}

	@Override
	public void update(CourseSubject courseSubject) throws CourseSubjectManagementException {
		if (courseSubject == null)
			throw new CourseSubjectManagementException("courseSubject", "courseSubject.update.courseSubject.required");
	}

	@Override
	public void deleteById(CourseSubjectKey id) throws CourseSubjectManagementException {
		if (id == null || id.getSubject() == null || id.getCourse() == null)
			throw new CourseSubjectManagementException("id", "courseSubject.delete.id.required");
	}

	@Override
	public void deleteByYearAndSyllabus(Long year, Long syllabusId) throws CourseSubjectManagementException {
		if (year == null)
			throw new CourseSubjectManagementException("year", "courseSubject.delete.year.required");
		
		if (syllabusId == null)
			throw new CourseSubjectManagementException("syllabusId", "courseSubject.delete.syllabus.id.required");
	}
}
