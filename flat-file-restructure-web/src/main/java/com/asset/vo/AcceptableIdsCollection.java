package com.asset.vo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

/**
 * Use this class to hold all the acceptable identities of the 
 * second properties file.
 * 
 * @author Pijush
 *
 */
public class AcceptableIdsCollection {
	
	@NotNull
	private final Map<String, String> data;

	/**
	 * USe this constructor to compose the object of the
	 * Ids collection.
	 */
	public AcceptableIdsCollection() {
		super();
		this.data = new HashMap<String, String>();
	}
	

	/**
	 * Use this method to add a new Id configuration
	 * to the collection of the identity column, which are red from
	 * the properties file.
	 * 
	 * @param oldAcceptableId : This is the oldAcceptableId, or the old value of the acceptable
	 * ids.
	 * 
	 * @param newAcceptableId  : This is the newAcceptableId, or the new value of the acceptable
	 * ids.
	 */
	public synchronized void addIdConfig(String oldAcceptableId, String newAcceptableId) {
		this.data.put(newAcceptableId, newAcceptableId);
	}
	
	/**
	 * Use this method to get the new value of the 
	 * acceptable id. If any null or invalid value is passed as parameter,
	 * then the return will be null.
	 * 
	 * @param oldAcceptableId : The old value of the id.
	 * @return : The new value of the id. If a null or absent value is passed,
	 * then a null will be returned.
	 */
	public String getNewValueOfAcceptableId(String oldAcceptableId) {
		
		if (oldAcceptableId == null) {
			return null;
		}
		
		if (!data.containsKey(oldAcceptableId)){
			return null;
		}
		return data.get(oldAcceptableId);
	}
	
	/**
	 * This method gives all the acceptable old ids, which are given in the
	 * properties file. 
	 * 
	 * @return return a {@link List} of all the
	 * acceptable ids.
	 */
	public List<String> getAllTheOldIds() {
		return Collections.unmodifiableList(
				data
					.entrySet()
					.stream()
					.map(Map.Entry::getKey)
					.collect(Collectors.toList()));
	}
	
	/**
	 * This method gives all the acceptable new ids, which are given in the
	 * properties file. 
	 * 
	 * @return return a {@link List} of all the
	 * acceptable ids.
	 */
	public List<String> getAllTheNewIds() {
		return Collections.unmodifiableList(
				data
					.entrySet()
					.stream()
					.map(Map.Entry::getValue)
					.collect(Collectors.toList()));
	}
}
