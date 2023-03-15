package com.kn.emiliano.vetsandpets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kn.emiliano.vetsandpets.model.Pet;
import com.kn.emiliano.vetsandpets.repository.PetRepository;

/**
 * Service class for managing PetDto entities.
 * 
 * @author Emiliano Pessoa
 * 
 */
@Service
public class PetService {

	private final PetRepository petRepository;

	@Autowired
	public PetService(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	/**
	 * Retrieves all pets from the database.
	 * 
	 * @return a list of all pets
	 */
	public List<Pet> findAll() {
		return petRepository.findAll();
	}

	/**
	 * Retrieves a pet by its ID.
	 * 
	 * @param id the ID of the pet to retrieve
	 * @return the pet with the specified ID, or null if it does not exist
	 */
	public Pet findById(long id) {
		return petRepository.findById(id).get();
	}

	/**
	 * Saves a new pet to the database.
	 * 
	 * @param pet the pet to save
	 * @return the saved pet
	 */
	public Pet save(Pet pet) {
		return petRepository.save(pet);
	}

	/**
	 * Deletes a pet from the database by its ID.
	 * 
	 * @param id the ID of the pet to delete
	 */
	public void deleteById(long id) {
		petRepository.deleteById(id);
	}
}
