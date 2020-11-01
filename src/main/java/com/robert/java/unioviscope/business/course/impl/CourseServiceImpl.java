package com.robert.java.unioviscope.business.course.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.robert.java.unioviscope.business.course.CourseService;
import com.robert.java.unioviscope.business.course.validator.CourseValidator;
import com.robert.java.unioviscope.model.Course;
import com.robert.java.unioviscope.model.exception.CourseManagementException;
import com.robert.java.unioviscope.persistence.CourseRepository;

/**
 * Clase que implementa la interfaz CourseService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.course.CourseService
 */
@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private CourseValidator validator;
	
	@Override
	public Course find(Long id) throws CourseManagementException {
		validator.findById(id);
		return courseRepository.findOne(id);
	}

	@Override
	public List<Course> findAll() {
		return IteratorUtils.toList(courseRepository.findAll().iterator());
	}

	@Override
	public Course save(Course course) throws CourseManagementException {
		validator.save(course);
		return courseRepository.save(course);
	}

	@Override
	public Course update(Course course) throws CourseManagementException {
		validator.update(course);
		return courseRepository.save(course);
	}

	@Override
	public Boolean deleteById(Long id) throws CourseManagementException {
		validator.findById(id);
		try {
			courseRepository.delete(id);
			return true;
		} catch (DataAccessException e) {
			throw new CourseManagementException("id", "course.delete.id.exception");
		}
	}

	@Override
	public Course findByYear(Long year) throws CourseManagementException {
		validator.findByYear(year);
		return courseRepository.findByYear(year);
	}

	@Override
	public Course findLast() {
		Course lastCourse = courseRepository.findByLastYear();
		
		if (lastCourse != null)
			return lastCourse;
		
		Long nextCourseYear = (long) (Calendar.getInstance().get(Calendar.YEAR) + 1);
		return new Course(nextCourseYear);
	}

	@Override
	public Boolean deleteByYear(Long year) throws CourseManagementException {
		validator.deleteByYear(year);
		try {
			courseRepository.deleteByYear(year);
			return true;
		} catch (DataAccessException e) {
			throw new CourseManagementException("year", "course.delete.year.exception");
		}
	}
}
