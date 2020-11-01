package com.robert.java.unioviscope.business.courseSubject.impl;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.robert.java.unioviscope.business.courseSubject.CourseSubjectService;
import com.robert.java.unioviscope.business.courseSubject.validator.CourseSubjectValidator;
import com.robert.java.unioviscope.model.Course;
import com.robert.java.unioviscope.model.CourseSubject;
import com.robert.java.unioviscope.model.Subject;
import com.robert.java.unioviscope.model.Syllabus;
import com.robert.java.unioviscope.model.exception.CourseSubjectManagementException;
import com.robert.java.unioviscope.model.types.key.CourseSubjectKey;
import com.robert.java.unioviscope.persistence.CourseRepository;
import com.robert.java.unioviscope.persistence.CourseSubjectRepository;
import com.robert.java.unioviscope.persistence.SubjectRepository;

/**
 * Clase que implementa la interfaz CourseSubjectService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.courseSubject.CourseSubjectService
 */
@Service
public class CourseSubjectServiceImpl implements CourseSubjectService {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private CourseSubjectRepository courseSubjectRepository;
	@Autowired
	private CourseSubjectValidator validator;

	@Override
	public CourseSubject find(CourseSubjectKey id) throws CourseSubjectManagementException {
		validator.findById(id);
		return courseSubjectRepository.findOne(id);
	}

	@Override
	public List<CourseSubject> findAll() {
		return IteratorUtils.toList(courseSubjectRepository.findAll().iterator());
	}

	@Override
	public CourseSubject save(CourseSubject courseSubject) throws CourseSubjectManagementException {
		validator.save(courseSubject);
		return courseSubjectRepository.save(courseSubject);
	}

	@Override
	public CourseSubject update(CourseSubject courseSubject) throws CourseSubjectManagementException {
		validator.update(courseSubject);
		return courseSubjectRepository.save(courseSubject);
	}

	@Override
	public Boolean deleteById(CourseSubjectKey id) throws CourseSubjectManagementException {
		validator.deleteById(id);
		try {
			courseSubjectRepository.delete(id);
			return true;
		} catch (DataAccessException e) {
			throw new CourseSubjectManagementException("id", "courseSubject.delete.id.exception");
		}
	}

	@Override
	public void createNextCourseSubjectForAll() {
		Course lastCourse = courseRepository.findByLastYear();
		Long nextCourseYear = null;

		if (lastCourse != null)
			nextCourseYear = lastCourse.getYear() + 1;
		else
			nextCourseYear = (long) (Calendar.getInstance().get(Calendar.YEAR) + 1);

		Course nextCourse = new Course(nextCourseYear);
		courseRepository.save(nextCourse);

		for (Subject subject : subjectRepository.findAll())
			courseSubjectRepository.save(new CourseSubject(subject, nextCourse));
	}

	@Override
	public Boolean deleteLastCourseByYearAndSyllabus(Long year, Long syllabusId)
			throws CourseSubjectManagementException {
		validator.deleteByYearAndSyllabus(year, syllabusId);
		try {
			courseSubjectRepository.deleteByCourseYearAndSubjectSyllabusId(year, syllabusId);
			return true;
		} catch (DataAccessException e) {
			throw new CourseSubjectManagementException("year", "courseSubject.delete.id.exception");
		}
	}

	@Override
	public List<CourseSubject> findAllWithSyllabus() {
		Course lastCourse = courseRepository.findByLastYear();
		Long nextCourseYear = null;

		if (lastCourse != null) {
			nextCourseYear = lastCourse.getYear();
		} else {
			nextCourseYear = (long) Calendar.getInstance().get(Calendar.YEAR);
		}

		List<CourseSubject> courseSubjects = courseSubjectRepository.findAllByCourseYear(nextCourseYear);

		if (courseSubjects.isEmpty()) {
			courseRepository.delete(lastCourse);
			courseSubjects = courseSubjectRepository.findAllByCourseYear(nextCourseYear - 1);
		}

		return StreamSupport.stream(courseSubjects.spliterator(), false)
				.filter(distinctSyllabus(cS -> cS.getSubject().getSyllabus())).collect(Collectors.toList());
	}

	private <T> Predicate<T> distinctSyllabus(Function<? super T, ?> keyExtractor) {
		Set<Syllabus> syllabuses = new HashSet<>();
		return t -> syllabuses.add((Syllabus) keyExtractor.apply(t));
	}
}
