package com.robert.java.unioviscope.config.i18n;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Clase que representa el filtro de internacionalización de la aplicación que
 * responde a las peticiones en función del idioma del cliente.
 * 
 * @author Robert Ene
 */
@Component
public class I18nFilter implements Filter {

	@Autowired
	private LocaleResolver localeResolver;

	public I18nFilter() {
		super();
	}

	@Override
	public void init(FilterConfig filterConfig) {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		Enumeration<Locale> locales = req.getLocales();

		if (locales.hasMoreElements()) {
			Locale locale = locales.nextElement();
			localeResolver.setLocale(request, response, locale);
			LocaleContextHolder.setLocale(locale);
		}

		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {

	}
}