package com.kn.emiliano.vetsandpets.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kn.emiliano.vetsandpets.dto.PetDto;
import com.kn.emiliano.vetsandpets.dto.PetStatistic;
import com.kn.emiliano.vetsandpets.model.Pet;
import com.kn.emiliano.vetsandpets.model.PetType;
import com.kn.emiliano.vetsandpets.model.User;
import com.kn.emiliano.vetsandpets.model.UserRole;
import com.kn.emiliano.vetsandpets.service.PetService;
import com.kn.emiliano.vetsandpets.service.UserService;

/**
 * A REST controller that handles pet authentication and registration.
 * 
 * @author Emiliano Pessoa
 * 
 */
@RestController
@RequestMapping("/api")
public class PetController {

	private final PetService petService;

	private final UserService userService;

	@Autowired
	private ModelMapper mapper;

	/**
	 * Creates a new PetController instance with the provided PetService dependency.
	 *
	 * @param petService an instance of the PetService interface.
	 */
	@Autowired
	public PetController(PetService petService, UserService userService) {
		this.petService = petService;
		this.userService = userService;
	}

	/**
	 * HTTP GET method that returns all pets.
	 * 
	 * @return a List of all PetDto objects
	 */
	@GetMapping("/pets")
	public List<PetDto> getAllPets() {
		List<Pet> pets = petService.findAll();
		if (getLoggedUser() != null && !getLoggedUser().getRole().equals(UserRole.Admin))
			pets = pets.stream().filter(e -> e.getUser().getId() == getLoggedUser().getId()).toList();
		return Arrays.asList(mapper.map(pets, PetDto[].class));
	}

	/**
	 * HTTP GET method that returns the pets statistics.
	 * 
	 * @return a List of Pet Statistics
	 */
	@GetMapping("/petstatistics")
	public List<PetStatistic> getPetStatistics() {
		if (getLoggedUser() != null && getLoggedUser().getRole().equals(UserRole.Admin)) {
			List<Pet> pets = petService.findAll();
			Map<PetType, List<PetType>> groupedPets = new HashMap<>();
			for (Pet pet : pets) {
				PetType petType = pet.getType();
				if (!groupedPets.containsKey(petType)) {
					groupedPets.put(petType, new ArrayList<>());
				}
				groupedPets.get(petType).add(petType);
			}
			List<PetStatistic> list = new ArrayList<>();
			groupedPets.forEach((k, v) -> {
				PetStatistic ps = new PetStatistic();
				ps.setCount(v.size());
				ps.setType(k);
				list.add(ps);
			});
			return list;
		}
		return null;
	}

	/**
	 * HTTP GET method that returns a pet by ID.
	 * 
	 * @param id the ID of the pet to retrieve
	 * @return the PetDto object with the specified ID
	 */
	@GetMapping("/pet/{id}")
	public PetDto getPetById(@PathVariable long id) {
		Pet pet = petService.findById(id);
		return mapper.map(pet, PetDto.class);
	}

	/**
	 * HTTP POST method that creates a new pet.
	 * 
	 * @param pet the PetDto object to create
	 * @return the created PetDto object
	 */
	@PostMapping("/pet")
	public PetDto createPet(@RequestBody PetDto petDto) {
		Pet pet = mapper.map(petDto, Pet.class);
		pet.setUser(userService.findByUsername(petDto.getUsername()));
		return mapper.map(petService.save(pet), PetDto.class);
	}

	/**
	 * HTTP PUT method that updates an existing pet.
	 * 
	 * @param id  the ID of the pet to update
	 * @param pet the updated PetDto object
	 * @return the updated PetDto object
	 */
	@PutMapping("/pet/{id}")
	public PetDto updatePet(@PathVariable long id, @RequestBody PetDto petDto) {
		petDto.setId(id);
		Pet pet = mapper.map(petDto, Pet.class);
		pet.setUser(userService.findByUsername(petDto.getUsername()));
		return mapper.map(petService.save(pet), PetDto.class);
	}

	/**
	 * HTTP DELETE method that deletes a pet by ID.
	 * 
	 * @param id the ID of the pet to delete
	 */
	@DeleteMapping("/pet/{id}")
	public void deletePet(@PathVariable long id) {
		petService.deleteById(id);
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