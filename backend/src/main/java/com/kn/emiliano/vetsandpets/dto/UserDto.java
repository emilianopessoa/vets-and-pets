package com.kn.emiliano.vetsandpets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.kn.emiliano.vetsandpets.model.UserRole;

/**
 * Class that represents a User Data Transfer Object (DTO).
 * 
 * @author Emiliano Pessoa
 * 
 */
public class UserDto {

	private long id;

	private String fullname;

	private String username;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	private String token;

	private UserRole role;

	/**
	 * Default constructor.
	 */
	public UserDto() {
	}

	/**
	 * Constructor with parameters.
	 *
	 * @param fullname the user's fullname
	 * @param username the user's username
	 * @param token    the user's token
	 * @param role     the user's role
	 */
	public UserDto(String fullname, String username, String token, UserRole role) {
		super();
		this.fullname = fullname;
		this.username = username;
		this.token = token;
		this.role = role;
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
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * @param fullname the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the role
	 */
	public UserRole getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}

	

}
