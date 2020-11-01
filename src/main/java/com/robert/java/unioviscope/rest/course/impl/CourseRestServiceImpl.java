package com.robert.java.unioviscope.rest.course.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.business.course.CourseService;
import com.robert.java.unioviscope.model.Course;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.rest.GenericRestServiceImpl;
import com.robert.java.unioviscope.rest.course.CourseRestService;

/**
 * Clase que extiende la clase GenericRestServiceImpl e implementa la interfaz
 * CourseRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.GenericRestServiceImpl
 * @see com.robert.java.unioviscope.rest.course.CourseRestService
 */
@RestController
@RequestMapping("course")
public class CourseRestServiceImpl extends GenericRestServiceImpl<Course, Long> implements CourseRestService {

	@Autowired
	private CourseService courseService;

	public GenericService<Course, Long> getService() {
		return courseService;
	}

	@Override
	public Course findByYear(Long year) throws BusinessException {
		return courseService.findByYear(year);
	}

	@Override
	public Course findLast() {
		return courseService.findLast();
	}

	@Override
	public Boolean deleteByYear(Long year) throws BusinessException {
		return courseService.deleteByYear(year);
	}
}
