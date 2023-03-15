package com.kn.emiliano.vetsandpets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kn.emiliano.vetsandpets.model.User;

/**
 * Repository interface for managing UserDto entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Finds a user by name.
	 * 
	 * @param username the name of the user
	 * @return the user with the specified name, or null if not found
	 */
	User findByUsername(String username);
	
	/**
	 * Finds a user by name and password
	 * 
	 * @param username the name of the user
	 * @param password the password of the user
	 * @return the user with the specified name and password, or null if not found
	 */
	User findByUsernameAndPassword(String username, String password);
}
