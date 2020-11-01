package com.robert.java.unioviscope.rest.courseSubject.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.robert.java.unioviscope.business.GenericService;
import com.robert.java.unioviscope.business.courseSubject.CourseSubjectService;
import com.robert.java.unioviscope.model.CourseSubject;
import com.robert.java.unioviscope.model.dto.editor.CourseSubjectEditor;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.types.key.CourseSubjectKey;
import com.robert.java.unioviscope.rest.GenericRestServiceImpl;
import com.robert.java.unioviscope.rest.courseSubject.CourseSubjectRestService;

/**
 * Clase que extiende la clase GenericRestServiceImpl e implementa la interfaz
 * CourseSubjectRestService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.rest.GenericRestServiceImpl
 * @see com.robert.java.unioviscope.rest.courseSubject.CourseSubjectRestService
 */
@RestController
@RequestMapping("courseSubject")
public class CourseSubjectRestServiceImpl extends GenericRestServiceImpl<CourseSubject, CourseSubjectKey>
		implements CourseSubjectRestService {

	@Autowired
	private CourseSubjectService courseSubjectService;

	@Override
	public GenericService<CourseSubject, CourseSubjectKey> getService() {
		return courseSubjectService;
	}

	@Override
	public void createNextCourseSubjectForAll() {
		courseSubjectService.createNextCourseSubjectForAll();
	}

	@Override
	public Boolean deleteLastCourseByYearAndSyllabus(@RequestParam("year") Long year,
			@RequestParam("syllabusId") Long syllabusId) throws BusinessException {
		return courseSubjectService.deleteLastCourseByYearAndSyllabus(year, syllabusId);
	}

	@Override
	public List<CourseSubject> findAllWithSyllabus() {
		return courseSubjectService.findAllWithSyllabus();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(CourseSubjectKey.class, new CourseSubjectEditor());
	}
}
