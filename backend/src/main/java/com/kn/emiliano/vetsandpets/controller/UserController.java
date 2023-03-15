package com.kn.emiliano.vetsandpets.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kn.emiliano.vetsandpets.dto.UserDto;
import com.kn.emiliano.vetsandpets.model.User;
import com.kn.emiliano.vetsandpets.model.UserRole;
import com.kn.emiliano.vetsandpets.service.UserService;

/**
 * A REST controller that handles user authentication and registration.
 * 
 * @author Emiliano Pessoa
 * 
 */
@RestController
@RequestMapping("/api")
public class UserController {

	private final UserService userService;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	BCryptPasswordEncoder encoder;

	/**
	 * Creates a new UserController instance with the provided UserService
	 * dependency.
	 *
	 * @param userService an instance of the UserService interface.
	 */
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * HTTP GET method that returns all users.
	 * 
	 * @return a List of all UserDto objects
	 */
	@GetMapping("/users")
	public List<UserDto> getAllUsers() {
		List<User> users = userService.findAll();
		if (getLoggedUser() != null && !getLoggedUser().getRole().equals(UserRole.Admin))
			users = users.stream().filter(e -> e.getId() == getLoggedUser().getId()).toList();
		return Arrays.asList(mapper.map(users, UserDto[].class));
	}

	/**
	 * HTTP GET method that returns a user by ID.
	 * 
	 * @param id the ID of the user to retrieve
	 * @return the UserDto object with the specified ID
	 */
	@GetMapping("/user/{id}")
	public UserDto getUserById(@PathVariable long id) {
		User user = userService.findById(id);
		return mapper.map(user, UserDto.class);
	}

	/**
	 * HTTP POST method that creates a new user.
	 * 
	 * @param user the UserDto object to create
	 * @return the created UserDto object
	 */
	@PostMapping("/user")
	public UserDto createUser(@RequestBody UserDto userDto) {
		User user = userService.save(mapper.map(userDto, User.class));
		return mapper.map(user, UserDto.class);
	}

	/**
	 * HTTP PUT method that updates an existing user.
	 * 
	 * @param id   the ID of the user to update
	 * @param user the updated UserDto object
	 * @return the updated UserDto object
	 */
	@PutMapping("/user/{id}")
	public UserDto updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
		User userOld = userService.findById(id);
		userDto.setId(id);
		User userToUpdate = mapper.map(userDto, User.class);
		if (getLoggedUser() != null && getLoggedUser().getRole().equals(UserRole.Admin)
				&& Strings.isNotEmpty(userDto.getPassword())) {
			userToUpdate.setPassword(encoder.encode(userDto.getPassword()));
		} else {
			userToUpdate.setPassword(userOld.getPassword());
		}
		User user = userService.save(userToUpdate);
		return mapper.map(user, UserDto.class);
	}

	/**
	 * HTTP DELETE method that deletes a user by ID.
	 * 
	 * @param id the ID of the user to delete
	 */
	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable long id) {
		userService.deleteById(id);
	}

	/**
	 * 
	 * @return Logged User.
	 */
	private User getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) principal;
				return userService.findByUsername(userDetails.getUsername());
			}
		}
		return null;
	}
}