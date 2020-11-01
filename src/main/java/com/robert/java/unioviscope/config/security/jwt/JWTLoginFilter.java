package com.robert.java.unioviscope.config.security.jwt;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robert.java.unioviscope.config.security.service.TokenAuthenticationService;
import com.robert.java.unioviscope.model.dto.AccountCredentials;

/**
 * Clase que representa el filtro de inicio de sesión en la aplicación.
 * 
 * @author Robert Ene
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		AccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(creds.getUserName(),
				creds.getPassword(), Collections.emptyList());

		Authentication auth = null;

		if (creds != null
				&& (creds.getUserName() != null && creds.getPassword() != null && !creds.getUserName().isEmpty())
				&& !creds.getPassword().isEmpty()) {
			try {
				auth = getAuthenticationManager().authenticate(token);
			} catch (Exception e) {
				return null;
			}
		}

		return auth;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		TokenAuthenticationService.addAuthentication(res, auth.getName(),
				((GrantedAuthority) auth.getAuthorities().toArray()[0]).getAuthority());
	}
}