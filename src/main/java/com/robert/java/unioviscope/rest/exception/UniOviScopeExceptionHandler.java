package com.robert.java.unioviscope.rest.exception;

import java.util.List;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.robert.java.unioviscope.business.common.i18n.I18nMessageResolver;
import com.robert.java.unioviscope.model.exception.BusinessException;
import com.robert.java.unioviscope.model.exception.ExceptionResponse;

/**
 * Clase que representa el manejador de excepciones para toda la aplicación. Es
 * el punto central en el que se atrapan y devuelven las excepciones producidas
 * en el sistema.
 * 
 * @author Robert Ene
 */
@ControllerAdvice
public class UniOviScopeExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(UniOviScopeExceptionHandler.class);

	@Autowired
	private I18nMessageResolver messageResolver;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> exception(Exception e) throws Exception {
		ExceptionResponse eR = new ExceptionResponse();
		eR.setField("");
		eR.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		eR.setException(e.getClass().getSimpleName());
		eR.setMessage(e.getMessage());
		log.error(e.toString());
		return new ResponseEntity<ExceptionResponse>(eR, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ServletException.class)
	public @ResponseBody ResponseEntity<ExceptionResponse> servletException(ServletException e) {
		ExceptionResponse eR = new ExceptionResponse();
		eR.setField("");
		eR.setCode(HttpStatus.BAD_REQUEST.toString());
		eR.setException(e.getClass().getSimpleName());
		eR.setMessage(e.getLocalizedMessage());
		log.error(e.toString());
		return new ResponseEntity<ExceptionResponse>(eR, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ExceptionResponse> httpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException e) throws HttpRequestMethodNotSupportedException {
		ExceptionResponse eR = new ExceptionResponse();
		eR.setField("");
		eR.setCode(HttpStatus.METHOD_NOT_ALLOWED.toString());
		eR.setException(e.getClass().getSimpleName());
		eR.setMessage(messageResolver.getMessage("api.method.not.suported.exception", e.getMethod(),
				String.join(",", e.getSupportedMethods())));
		return new ResponseEntity<ExceptionResponse>(eR, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public @ResponseBody ResponseEntity<ExceptionResponse> typeMismatchException(
			MethodArgumentTypeMismatchException e) {
		ExceptionResponse eR = new ExceptionResponse();
		eR.setField(e.getName());
		eR.setCode(HttpStatus.NOT_ACCEPTABLE.toString());
		eR.setException(e.getClass().getSimpleName());
		eR.setMessage(messageResolver.getMessage("entity.conversion.id.exception", e.getValue(),
				e.getRequiredType().getSimpleName()));
		return new ResponseEntity<ExceptionResponse>(eR, HttpStatus.OK);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ExceptionResponse> businessException(BusinessException e) throws BusinessException {
		ExceptionResponse eR = new ExceptionResponse();
		eR.setField(e.getField());
		eR.setCode(HttpStatus.BAD_REQUEST.toString());
		eR.setException(e.getClass().getSimpleName());
		eR.setMessage(messageResolver.getMessage(e.getKey(), e.getArgs()));
		return new ResponseEntity<ExceptionResponse>(eR, HttpStatus.OK);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public @ResponseBody ResponseEntity<List<ExceptionResponse>> constraintViolationException(
			ConstraintViolationException e) {
		List<ExceptionResponse> errors = ExceptionResponse.getErrors(e.getConstraintViolations());
		return new ResponseEntity<List<ExceptionResponse>>(errors, HttpStatus.OK);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public @ResponseBody ResponseEntity<ExceptionResponse> dataIntegrityViolationException(
			DataIntegrityViolationException e) {
		ExceptionResponse eR = new ExceptionResponse();
		eR.setCode(e.getClass().getSimpleName());
		eR.setMessage("Could not execute statement because constraint");
		return new ResponseEntity<ExceptionResponse>(eR, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public @ResponseBody ResponseEntity<ExceptionResponse> emptyResultDataAccessException(
			EmptyResultDataAccessException e) {
		ExceptionResponse eR = new ExceptionResponse();
		eR.setField("");
		eR.setCode(HttpStatus.CONFLICT.toString());
		eR.setException(e.getClass().getSimpleName());
		eR.setMessage(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(eR, HttpStatus.CONFLICT);
	}
}
