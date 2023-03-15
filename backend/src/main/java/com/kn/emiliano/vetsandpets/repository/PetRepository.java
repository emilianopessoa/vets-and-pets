package com.kn.emiliano.vetsandpets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kn.emiliano.vetsandpets.model.Pet;
import com.kn.emiliano.vetsandpets.model.User;

/**
 * Repository interface for managing PetDto entities.
 */
public interface PetRepository extends JpaRepository<Pet, Long> {

	/**
	 * Finds a pet by name.
	 * 
	 * @param name the name of the pet
	 * @return the pet with the specified name, or null if not found
	 */
	User findByName(String username);
}
