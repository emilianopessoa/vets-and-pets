package com.kn.emiliano.vetsandpets.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the application, providing various bean
 * configurations.
 * 
 * @author Emiliano Pessoa
 * 
 */
@Configuration
public class ApplicationConfig {

	/**
	 * Creates a ModelMapper instance as a bean.
	 * 
	 * @return a new ModelMapper instance.
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
