package com.robert.java.unioviscope.config.security.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Clase que representa el filtro CORS de la aplicación.
 * 
 * @author Robert Ene
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

	public CORSFilter() {
		super();
	}

	@Override
	public void init(FilterConfig filterConfig) {
		// not applicable
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"X-Requested-With, Authorization, Origin, Content-Type, Content-Length, Version, Content-Disposition, X-Filename, Accept-Language");
		response.setHeader("Access-Control-Expose-Headers",
				"X-Requested-With, Authorization, Origin, Content-Type, Content-Length, Version, Content-Disposition, X-Filename, Accept-Language");

		if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void destroy() {
		// not applicable
	}
}