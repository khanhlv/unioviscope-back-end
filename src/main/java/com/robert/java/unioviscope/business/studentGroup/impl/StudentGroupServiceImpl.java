package com.robert.java.unioviscope.business.studentGroup.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.robert.java.unioviscope.business.studentGroup.StudentGroupService;
import com.robert.java.unioviscope.business.studentGroup.importer.StudentGroupImporter;
import com.robert.java.unioviscope.business.studentGroup.validator.StudentGroupValidator;
import com.robert.java.unioviscope.model.CourseSubject;
import com.robert.java.unioviscope.model.StudentGroup;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.model.exception.StudentGroupManagementException;
import com.robert.java.unioviscope.model.types.GroupType;
import com.robert.java.unioviscope.model.types.key.StudentGroupKey;
import com.robert.java.unioviscope.persistence.CourseSubjectRepository;
import com.robert.java.unioviscope.persistence.StudentGroupRepository;

/**
 * Clase que implementa la interfaz StudentGroupService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.studentGroup.StudentGroupService
 */
@Service
public class StudentGroupServiceImpl implements StudentGroupService {

	@Autowired
	private StudentGroupRepository studentGroupRepository;
	@Autowired
	private StudentGroupImporter studentGroupImporter;
	@Autowired
	private StudentGroupValidator validator;
	@Autowired
	private CourseSubjectRepository courseSubjectRepository;

	@Override
	public StudentGroup find(StudentGroupKey id) throws StudentGroupManagementException {
		validator.deleteById(id);
		return studentGroupRepository.findOne(id);
	}

	@Override
	public List<StudentGroup> findAll() {
		return IteratorUtils.toList(studentGroupRepository.findAll().iterator());
	}

	@Override
	public StudentGroup save(StudentGroup studentGroup) throws StudentGroupManagementException {
		validator.save(studentGroup);
		return studentGroupRepository.save(studentGroup);
	}

	@Override
	public StudentGroup update(StudentGroup studentGroup) throws StudentGroupManagementException {
		validator.update(studentGroup);
		return studentGroupRepository.save(studentGroup);
	}

	@Override
	public Boolean deleteById(StudentGroupKey id) throws StudentGroupManagementException {
		validator.deleteById(id);
		try {
			studentGroupRepository.delete(id);
			return true;
		} catch (DataAccessException e) {
			throw new StudentGroupManagementException("id", "studentGroup.delete.id.exception");
		}
	}

	@Override
	public OutputStream importStudentGroups(InputStream file) throws ImportFileException {
		try {
			return studentGroupImporter.importFile(file);
		} catch (RuntimeException e) {
			throw new ImportFileException("file", "api.import.file.runtime.exception");
		} catch (IOException e) {
			throw new ImportFileException("file", "api.import.file.io.exception");
		}
	}

	@Override
	public StudentGroup findByStudentAndGroupTypeAndCourseSubject(Long studentId, GroupType groupType, Long courseId,
			Long subjectId) {
		CourseSubject courseSubject = courseSubjectRepository.findBySubjectIdAndCourseId(subjectId, courseId);
		return studentGroupRepository.findByStudentIdAndGroupTypeAndGroupSubject(studentId, groupType, courseSubject);
	}
}
