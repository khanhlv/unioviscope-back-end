package com.robert.java.unioviscope.business.session.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.robert.java.unioviscope.business.session.SessionService;
import com.robert.java.unioviscope.business.session.importer.SessionImporter;
import com.robert.java.unioviscope.business.session.validator.SessionValidator;
import com.robert.java.unioviscope.model.Session;
import com.robert.java.unioviscope.model.exception.ImportFileException;
import com.robert.java.unioviscope.model.exception.SessionManagementException;
import com.robert.java.unioviscope.persistence.SessionRepository;

/**
 * Clase que implementa la interfaz SessionService.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.session.SessionService
 */
@Service
public class SessionServiceImpl implements SessionService {

	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private SessionValidator validator;
	@Autowired
	private SessionImporter sessionImporter;

	@Override
	public Session find(Long id) throws SessionManagementException {
		validator.findById(id);
		return sessionRepository.findOne(id);
	}

	@Override
	public List<Session> findAll() {
		return IteratorUtils.toList(sessionRepository.findAll().iterator());
	}

	@Override
	public Session save(Session session) throws SessionManagementException {
		validator.save(session);
		return sessionRepository.save(session);
	}

	@Override
	public Session update(Session session) throws SessionManagementException {
		validator.update(session);
		return sessionRepository.save(session);
	}

	@Override
	public Boolean deleteById(Long id) throws SessionManagementException {
		validator.deleteById(id);
		try {
			sessionRepository.delete(id);
			return true;
		} catch (DataAccessException e) {
			throw new SessionManagementException("id", "session.delete.id.exception");
		}
	}

	@Override
	public OutputStream importSessions(InputStream file) throws ImportFileException {
		try {
			return sessionImporter.importFile(file);
		} catch (RuntimeException e) {
			throw new ImportFileException("file", "api.import.file.runtime.exception");
		} catch (IOException e) {
			throw new ImportFileException("file", "api.import.file.io.exception");
		}
	}
}
