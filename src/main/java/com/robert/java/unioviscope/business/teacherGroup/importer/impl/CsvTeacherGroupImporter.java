package com.robert.java.unioviscope.business.teacherGroup.importer.impl;

import java.io.IOException;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robert.java.unioviscope.business.common.importer.AbstractImporter;
import com.robert.java.unioviscope.business.teacherGroup.importer.TeacherGroupImporter;
import com.robert.java.unioviscope.model.Group;
import com.robert.java.unioviscope.model.Teacher;
import com.robert.java.unioviscope.model.TeacherGroup;
import com.robert.java.unioviscope.model.types.key.TeacherGroupKey;
import com.robert.java.unioviscope.persistence.GenericRepository;
import com.robert.java.unioviscope.persistence.GroupRepository;
import com.robert.java.unioviscope.persistence.TeacherGroupRepository;
import com.robert.java.unioviscope.persistence.TeacherRepository;

/**
 * Clase que extiende la clase AbstractImporter e implementa la interfaz
 * TeacherGroupImporter.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.AbstractImporter
 * @see com.robert.java.unioviscope.business.teacherGroup.importer.TeacherGroupImporter
 */
@Component
public class CsvTeacherGroupImporter extends AbstractImporter<TeacherGroup, TeacherGroupKey>
		implements TeacherGroupImporter {

	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private TeacherGroupRepository teacherGroupRepository;

	@Override
	public void processRecord(CSVRecord record, CSVPrinter printer) throws IOException {
		Teacher teacher = validateTeacher(record.get(USERNAME), printer);
		Group group = validateGroup(record.get(GROUP_ID), printer);
		TeacherGroup teacherGroup = validateTeacherGroup(new TeacherGroup(teacher, group), printer);

		if (teacherGroup != null)
			addEntity(teacherGroup);
	}

	@Override
	public String[] getHeaders() {
		return HEADERS;
	}

	@Override
	public GenericRepository<TeacherGroup, TeacherGroupKey> getRepository() {
		return teacherGroupRepository;
	}

	private Teacher validateTeacher(String userName, CSVPrinter printer) throws IOException {
		if (userName == null || userName.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("teacherGroup.import.userName.file.required"));
			return null;
		}

		Teacher teacher = teacherRepository.findByUserName(userName);

		if (teacher == null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("teacherGroup.import.userName.db.invalid", userName));
			return null;
		}

		return teacher;
	}

	private Group validateGroup(String groupId, CSVPrinter printer) throws IOException {
		if (groupId == null || groupId.isEmpty()) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("teacherGroup.import.groupId.file.required"));
			return null;
		}

		Long id = null;

		try {
			id = Long.parseLong(groupId);
		} catch (NumberFormatException nfe) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("teacherGroup.import.groupId.file.invalid", groupId));
			return null;
		}

		Group group = groupRepository.findOne(id);

		if (group == null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("teacherGroup.import.groupId.db.invalid", groupId));
			return null;
		}

		return group;
	}

	private TeacherGroup validateTeacherGroup(TeacherGroup teacherGroup, CSVPrinter printer) throws IOException {
		Teacher teacher = teacherGroup.getTeacher();
		Group group = teacherGroup.getGroup();

		if (teacher == null || group == null)
			return null;

		for (TeacherGroup tG : getEntities()) {
			if (tG.getGroup().equals(group) && tG.getTeacher().equals(teacher)) {
				setImportError(true);
				printer.printComment(getMessageResolver().getMessage("teacherGroup.import.group.file.repeated",
						teacher.getUserName(), group.getId()));
				return null;
			}
		}

		if (teacherGroupRepository.findByTeacherUserNameAndGroupId(teacher.getUsername(), group.getId()) != null) {
			setImportError(true);
			printer.printComment(getMessageResolver().getMessage("teacherGroup.import.group.db.repeated",
					teacher.getUserName(), group.getId()));
			return null;
		}

		return teacherGroup;
	}
}
