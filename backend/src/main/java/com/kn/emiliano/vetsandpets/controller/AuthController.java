package com.kn.emiliano.vetsandpets.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kn.emiliano.vetsandpets.dto.UserDto;
import com.kn.emiliano.vetsandpets.model.User;
import com.kn.emiliano.vetsandpets.model.UserRole;
import com.kn.emiliano.vetsandpets.repository.UserRepository;
import com.kn.emiliano.vetsandpets.security.jwt.JwtUtil;
import com.kn.emiliano.vetsandpets.security.service.UserDetailsImpl;

/**
 * Controller for handling authentication-related requests.
 * 
 * @author Emiliano Pessoa
 * 
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	JwtUtil jwtUtil;

	/**
	 * Authenticates a user with the provided username and password.
	 *
	 * @param userDto the user's credentials.
	 * @return a ResponseEntity containing the authenticated user details or an
	 *         error message.
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody UserDto userDto) {

		User user = userRepository.findByUsername(userDto.getUsername());

		if (user != null && encoder.matches(userDto.getPassword(), user.getPassword())) {

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtil.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return ResponseEntity.ok(new UserDto(userDetails.getFullname(), userDetails.getUsername(), jwt,
					UserRole.valueOf(roles.get(0))));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
		}
	}

	/**
	 * Registers a new user with the provided credentials.
	 *
	 * @param userDto the new user's credentials.
	 * @return a ResponseEntity containing the registered user details or an error
	 *         message.
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
		if (userRepository.findByUsername(userDto.getUsername()) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Username is already taken!");
		}

		User user = new User(userDto.getFullname(), userDto.getUsername(), encoder.encode(userDto.getPassword()),
				UserRole.User);
		userRepository.save(user);

		return authenticateUser(userDto);
	}

	/**
	 * Validates a JWT token.
	 *
	 * @param token the JWT token.
	 * @return true if the token is valid, false otherwise.
	 */
	@PostMapping("/validate")
	public boolean validateToken(@RequestBody String token) {
		System.out.println(token);
		return jwtUtil.validateJwtToken(token);
	}
}
