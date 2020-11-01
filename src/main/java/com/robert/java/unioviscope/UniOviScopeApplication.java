package com.robert.java.unioviscope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * Punto de entrada para la aplicación UniOviSCOPE.
 *
 * @author Robert Ene
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan("com.robert.java.unioviscope")
@EntityScan(basePackages = { "com.robert.java.unioviscope.model" })
@EnableJpaRepositories(basePackages = { "com.robert.java.unioviscope.persistence" })
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class UniOviScopeApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniOviScopeApplication.class, args);
	}
}