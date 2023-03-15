package com.kn.emiliano.vetsandpets.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity class that represents a UserDto.
 */
@Entity
@Table(name = "pets")
public class Pet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private PetType type;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * Default constructor.
	 */
	public Pet() {
	}

	/**
	 * @param id
	 * @param name
	 * @param type
	 * @param user
	 */
	public Pet(long id, String name, PetType type, User user) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.user = user;
	}

	/**
	 * Constructor that takes all PetDto properties.
	 * 
	 * @param name the pet`s name
	 * @param type the pet's type
	 */
	public Pet(String name, PetType type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public PetType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(PetType type) {
		this.type = type;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
