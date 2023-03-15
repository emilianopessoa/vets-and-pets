package com.kn.emiliano.vetsandpets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The main application class for the Vets and Pets project, which extends
 * SpringBootServletInitializer for deploying the application as a Servlet in a
 * Servlet container.
 * 
 * @author Emiliano Pessoa
 * 
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	/**
	 * The main method of the application, which runs the Spring Boot application.
	 * 
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		try {
			SpringApplication.run(Application.class, args);
		} catch (Throwable e) {
			if (!e.getClass().getName().contains("SilentExitException")) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Configures the SpringApplicationBuilder for the application. This method
	 * allows building the application as a Servlet for deployment in a Servlet
	 * container.
	 * 
	 * @param application the SpringApplicationBuilder instance
	 * @return the configured SpringApplicationBuilder instance
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

}
