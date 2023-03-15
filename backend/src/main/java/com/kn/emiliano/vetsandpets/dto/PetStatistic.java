package com.kn.emiliano.vetsandpets.dto;

import com.kn.emiliano.vetsandpets.model.PetType;

/**
 * Class that represents the Pets Statistics Data Transfer Object (DTO).
 * 
 * @author Emiliano Pessoa
 * 
 */
public class PetStatistic {

	private PetType type;

	private int count;

	/**
	 * Default constructor.
	 */
	public PetStatistic() {
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
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
