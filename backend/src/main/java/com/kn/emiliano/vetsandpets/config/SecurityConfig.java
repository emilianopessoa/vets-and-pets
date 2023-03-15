package com.kn.emiliano.vetsandpets.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.kn.emiliano.vetsandpets.security.jwt.AuthEntryPointJwt;
import com.kn.emiliano.vetsandpets.security.jwt.AuthTokenFilter;
import com.kn.emiliano.vetsandpets.security.service.UserDetailsServiceImpl;

/**
 * Configuration class for Spring Security, defining beans and settings related
 * to authentication and authorization.
 * 
 * @author Emiliano Pessoa
 * 
 */
@Configuration
public class SecurityConfig {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	/**
	 * Creates a new AuthTokenFilter instance as a bean.
	 *
	 * @return a new AuthTokenFilter instance.
	 */
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	/**
	 * Creates a new DaoAuthenticationProvider instance as a bean.
	 *
	 * @return a new DaoAuthenticationProvider instance.
	 */
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(getBCryptPasswordEncoder());

		return authProvider;
	}

	/**
	 * Creates a new AuthenticationManager instance as a bean.
	 *
	 * @param authConfig the AuthenticationConfiguration instance.
	 * @return a new AuthenticationManager instance.
	 * @throws Exception if an error occurs while creating the
	 *                   AuthenticationManager.
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	/**
	 * Creates a new BCryptPasswordEncoder instance as a bean.
	 *
	 * @return a new BCryptPasswordEncoder instance.
	 */
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configures and builds the SecurityFilterChain.
	 *
	 * @param http the HttpSecurity instance.
	 * @return the built SecurityFilterChain.
	 * @throws Exception if an error occurs while configuring the
	 *                   SecurityFilterChain.
	 */
	@Bean
	@SuppressWarnings("deprecation")
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated();

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
