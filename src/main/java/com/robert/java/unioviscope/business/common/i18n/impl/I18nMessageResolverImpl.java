package com.robert.java.unioviscope.business.common.i18n.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.robert.java.unioviscope.business.common.i18n.I18nMessageResolver;

/**
 * Clase que implementa la interfaz I18nMessageResolver.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.i18n.I18nMessageResolver
 */
@Service
public class I18nMessageResolverImpl implements I18nMessageResolver {

	@Autowired
	private MessageSource messageSource;

	@Override
	public String getMessage(String key, Object... args) {
		return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
	}
}
