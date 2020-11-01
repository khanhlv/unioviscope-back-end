package com.robert.java.unioviscope.model.dto.editor;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robert.java.unioviscope.model.types.key.StudentGroupKey;

public class StudentGroupEditor extends PropertyEditorSupport  {
	
	private static final Logger log = LoggerFactory.getLogger(StudentGroupEditor.class);

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();

        StudentGroupKey studentGroupKey = null;
		try {
			studentGroupKey = mapper.readValue(text, StudentGroupKey.class);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		
        setValue(studentGroupKey);
    }
}