package com.robert.java.unioviscope.config.servlet;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.robert.java.unioviscope.UniOviScopeApplication;

/**
 * Clase que inicializa el servlet de la aplicación.
 * 
 * @author Robert Ene
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(UniOviScopeApplication.class);
	}
}
