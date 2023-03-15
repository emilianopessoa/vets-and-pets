package com.kn.emiliano.vetsandpets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kn.emiliano.vetsandpets.model.User;
import com.kn.emiliano.vetsandpets.repository.UserRepository;

/**
 * Service class for managing UserDto entities.
 * 
 * @author Emiliano Pessoa
 * 
 */
@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Retrieves all users from the database.
	 * 
	 * @return a list of all users
	 */
	public List<User> findAll() {
		return userRepository.findAll();
	}

	/**
	 * Retrieves a user by its ID.
	 * 
	 * @param id the ID of the user to retrieve
	 * @return the user with the specified ID, or null if it does not exist
	 */
	public User findById(long id) {
		return userRepository.findById(id).get();
	}

	/**
	 * Retrieves a user by its username.
	 * 
	 * @param id the username of the user to retrieve
	 * @return the user with the specified username, or null if it does not exist
	 */
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	/**
	 * Saves a new user to the database.
	 * 
	 * @param user the user to save
	 * @return the saved user
	 */
	public User save(User user) {
		return userRepository.save(user);
	}

	/**
	 * Deletes a user from the database by its ID.
	 * 
	 * @param id the ID of the user to delete
	 */
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}

}
