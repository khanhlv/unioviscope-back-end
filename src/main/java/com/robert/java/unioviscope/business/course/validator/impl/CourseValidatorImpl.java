package com.robert.java.unioviscope.business.course.validator.impl;

import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.course.validator.CourseValidator;
import com.robert.java.unioviscope.model.Course;
import com.robert.java.unioviscope.model.exception.CourseManagementException;

/**
 * Clase que implementa la interfaz CourseValidator.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.course.validator.CourseValidator
 */
@Component
public class CourseValidatorImpl implements CourseValidator {

	@Override
	public void findById(Long id) throws CourseManagementException {
		if (id == null)
			throw new CourseManagementException("id", "course.find.id.required");
	}

	@Override
	public void save(Course course) throws CourseManagementException {
		if (course == null)
			throw new CourseManagementException("course", "course.save.course.required");
	}

	@Override
	public void update(Course course) throws CourseManagementException {
		if (course == null)
			throw new CourseManagementException("course", "course.update.course.required");
	}

	@Override
	public void deleteById(Long id) throws CourseManagementException {
		if (id == null)
			throw new CourseManagementException("id", "course.delete.id.required");
	}

	@Override
	public void findByYear(Long year) throws CourseManagementException {
		if (year == null)
			throw new CourseManagementException("year", "course.find.year.required");
	}

	@Override
	public void deleteByYear(Long year) throws CourseManagementException {
		if (year == null)
			throw new CourseManagementException("year", "course.delete.year.required");
	}
}