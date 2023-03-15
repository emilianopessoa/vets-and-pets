package com.kn.emiliano.vetsandpets.dto;

import com.kn.emiliano.vetsandpets.model.PetType;

/**
 * Class that represents a Pet Data Transfer Object (DTO).
 * 
 * @author Emiliano Pessoa
 * 
 */
public class PetDto {

	private long id;

	private String name;

	private PetType type;

	private String username;

	private String userFullname;

	/**
	 * Default constructor.
	 */
	public PetDto() {
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the userFullname
	 */
	public String getUserFullname() {
		return userFullname;
	}

	/**
	 * @param userFullname the userFullname to set
	 */
	public void setUserFullname(String userFullname) {
		this.userFullname = userFullname;
	}

}
