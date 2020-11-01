package com.robert.java.unioviscope.model.dto.editor;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robert.java.unioviscope.model.types.key.AttendanceKey;

public class AttendanceEditor extends PropertyEditorSupport  {
	
	private static final Logger log = LoggerFactory.getLogger(AttendanceEditor.class);

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();

        AttendanceKey attendanceKey = null;
		try {
			attendanceKey = mapper.readValue(text, AttendanceKey.class);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		
        setValue(attendanceKey);
    }
}