package com.robert.java.unioviscope.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.robert.java.unioviscope.business.common.CommonService;
import com.robert.java.unioviscope.config.security.jwt.JWTAuthenticationFilter;
import com.robert.java.unioviscope.config.security.jwt.JWTLoginFilter;
import com.robert.java.unioviscope.model.types.Role;

/**
 * Clase que define la capa de seguridad de la aplicación así como el acceso de
 * los diferentes roles a los recursos que les corresponden.
 * 
 * @author Robert Ene
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.cors()
			.and()
			.authorizeRequests()
			.antMatchers("/console/**").permitAll()
	        .antMatchers(HttpMethod.POST, "/common/**").permitAll()
	        .antMatchers("/admin/**", "/syllabus/**", "subject/**", "course/**", "courseSubject/**", 
	        		"group/**", "teachers/**", "students/**", "teacherGroup/**", 
	        		"studentGroup/**").hasAuthority(Role.ADMIN.toString())
	        .antMatchers("/teacher/**").hasAuthority(Role.TEACHER.toString())
	        .antMatchers("/student/**").hasAuthority(Role.STUDENT.toString())
	        .antMatchers("/session/**").hasAnyAuthority(Role.ADMIN.toString(), Role.TEACHER.toString())
	        .antMatchers("/attendance/**").hasAnyAuthority(Role.ADMIN.toString(), Role.TEACHER.toString())
	        .anyRequest().authenticated()
	        .and()
	        .logout()
	        .permitAll()
	        .and()
	        .addFilterBefore(new JWTLoginFilter("/common/logIn", authenticationManager()),
	        		UsernamePasswordAuthenticationFilter.class)
	        .addFilterBefore(new JWTAuthenticationFilter(), 
	        		UsernamePasswordAuthenticationFilter.class)
	        .headers()
			.frameOptions().sameOrigin()
			.httpStrictTransportSecurity().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(commonService);
	    authProvider.setPasswordEncoder(passwordEncoder);
	    auth.authenticationProvider(authProvider);
	}
}