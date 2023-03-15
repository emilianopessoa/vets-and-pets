package com.kn.emiliano.vetsandpets.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.kn.emiliano.vetsandpets.model.User;
import com.kn.emiliano.vetsandpets.model.UserRole;
import com.kn.emiliano.vetsandpets.repository.UserRepository;

/**
 * Initializes the application with default data on startup.
 * 
 * @author Emiliano Pessoa
 * 
 */
@Component
public class Initializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;

	/**
	 * Constructs an Initializer instance with a UserRepository and
	 * BCryptPasswordEncoder.
	 *
	 * @param userRepository the UserRepository instance.
	 * @param encoder        the BCryptPasswordEncoder instance.
	 */
	public Initializer(UserRepository userRepository, BCryptPasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}

	/**
	 * Runs the data initialization process on application startup.
	 *
	 * @param strings command-line arguments (not used in this implementation).
	 */
	@Override
	public void run(String... strings) {
		User user = userRepository.findByUsername("admin");
		if (user == null) {
			user = new User("System Administrator", "admin", encoder.encode("admin"), UserRole.Admin);
			userRepository.save(user);
		}
		System.out.println("User 'admin' registered  with password 'admin'.");
	}
}