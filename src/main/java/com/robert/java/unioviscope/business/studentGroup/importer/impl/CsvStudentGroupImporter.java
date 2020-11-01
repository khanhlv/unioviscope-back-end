package com.robert.java.unioviscope.business.studentGroup.importer.impl;

import java.io.IOException;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.common.importer.AbstractImporter;
import com.robert.java.unioviscope.business.studentGroup.importer.StudentGroupImporter;
import com.robert.java.unioviscope.model.Group;
import com.robert.java.unioviscope.model.Student;
import com.robert.java.unioviscope.model.StudentGroup;
import com.robert.java.unioviscope.model.types.key.StudentGroupKey;
import com.robert.java.unioviscope.persistence.GenericRepository;
import com.robert.java.unioviscope.persistence.GroupRepository;
import com.robert.java.unioviscope.persistence.StudentGroupRepository;
import com.robert.java.unioviscope.persistence.StudentRepository;

/**
 * Clase que extiende la clase AbstractImporter e implementa la interfaz
 * StudentGroupImporter.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.AbstractImporter
 * @see com.robert.java.unioviscope.business.studentGroup.importer.StudentGroupImporter
 */
@Component
public class CsvStudentGroupImporter extends AbstractImporter<StudentGroup, StudentGroupKey>
		implements StudentGroupImporter {

	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private StudentGroupRepository studentGroupRepository;

	@Override
	public void processRecord(CSVRecord record, CSVPrinter printer) throws IOException {
		Student student = validateStudent(record.get(USERNAME), printer);
		Group group = validateGroup(record.get(GROUP_ID), printer);
		StudentGroup studentGroup = validateStudentGroup(new StudentGroup(student, group), printer);

		if (studentGroup != null)
			addEntity(studentGroup);
	}

	@Override
	public String[] getHeaders() {
		return HEADERS;
	}

	@Override
	public GenericRepository<StudentGroup, StudentGroupKey> getRepository() {
		return studentGroupRepository;
	}

	private Student validateStudent(String userName, CSVPrinter printer) throws IOException {
		if (userName == null || userName.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("studentGroup.import.userName.file.required"));
			return null;
		}

		Student student = studentRepository.findByUserName(userName);

		if (student == null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("studentGroup.import.userName.db.invalid", userName));
			return null;
		}

		return student;
	}

	private Group validateGroup(String groupId, CSVPrinter printer) throws IOException {
		if (groupId == null || groupId.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("studentGroup.import.groupId.file.required"));
			return null;
		}

		Long id = null;

		try {
			id = Long.parseLong(groupId);
		} catch (NumberFormatException nfe) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("studentGroup.import.groupId.file.invalid", groupId));
			return null;
		}

		Group group = groupRepository.findOne(id);

		if (group == null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("studentGroup.import.groupId.db.invalid", groupId));
			return null;
		}

		return group;
	}

	private StudentGroup validateStudentGroup(StudentGroup studentGroup, CSVPrinter printer) throws IOException {
		Student student = studentGroup.getStudent();
		Group group = studentGroup.getGroup();

		if (student == null || group == null)
			return null;

		if (!validateFileStudentGroups(group, student, printer))
			return null;

		if (!validateDbStudentGroups(group, student, printer))
			return null;

		return studentGroup;
	}

	private Boolean validateFileStudentGroups(Group group, Student student, CSVPrinter printer) throws IOException {
		for (StudentGroup sG : getEntities()) {
			if (sG.getGroup().equals(group) && sG.getStudent().equals(student)) {
				setImportError(true);
				printer.printComment(getMessageResolver().getMessage("studentGroup.import.group.file.repeated",
						student.getUserName(), group.getId()));
				return false;
			}

			if (sG.getGroup().getType().equals(group.getType()) && sG.getGroup().getSubject().equals(group.getSubject())
					&& sG.getStudent().equals(student)) {
				setImportError(true);
				printer.printComment(getMessageResolver().getMessage("studentGroup.import.group.type.file.repeated",
						student.getUserName(), sG.getGroup().getId(), group.getType(),
						sG.getGroup().getSubject().getSubject().getDenomination()));
				return false;
			}
		}

		return true;
	}

	private Boolean validateDbStudentGroups(Group group, Student student, CSVPrinter printer) throws IOException {
		if (studentGroupRepository.findByStudentUserNameAndGroupId(student.getUsername(), group.getId()) != null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("studentGroup.import.group.db.repeated",
					student.getUserName(), group.getId()));
			return false;
		}

		for (StudentGroup sG : studentGroupRepository.findByStudentUserName(student.getUserName())) {
			if (sG.getGroup().getType().equals(group.getType()) && sG.getGroup().getSubject().equals(group.getSubject())
					&& sG.getStudent().equals(student)) {
				setImportError(true);
				printer.printComment(getMessageResolver().getMessage("studentGroup.import.group.type.db.repeated",
						student.getUserName(), sG.getGroup().getId(), group.getType(),
						sG.getGroup().getSubject().getSubject().getDenomination()));
				return false;
			}
		}

		return true;
	}
}
