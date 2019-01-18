package com.asset.vo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 *
 */

public class ColumnConfigCollection {
	
	@NotNull
	private final List<ColumnConfig> data;

	/**
	 * USe this constructor to compose the object of the
	 * columns configuration collection.
	 */
	public ColumnConfigCollection() {
		super();
		this.data = new ArrayList<ColumnConfig>();
	}
	

	/**
	 * Use this method to add a new column configuration
	 * to the collection of the configuration column, which are red from
	 * the properties file.
	 * 
	 * @param columnConfig : Pass the {@link ColumnConfig}} object to
	 * the collection of all the column configurations. 
	 */
	public synchronized void addColumnConfig(ColumnConfig columnConfig) {
		this.data.add(columnConfig);
	}
	
	/**
	 * Use this method to get a column configuration
	 * value object, which has a matching old column name as
	 * the passed parameter.
	 *  
	 * @param columnConfig : It is the {@link String}} 
	 * object, that has the old column name.
	 * 
	 * 
	 */
	public ColumnConfig getColumnConfigWithOldName(String oldColumnName) {
		boolean columnConfigExists = data.stream().anyMatch(config -> config.getOldColumnName().equals(oldColumnName));
		
		if (columnConfigExists) {
			return data
						.stream()
						.filter(config -> config.getOldColumnName().equals(oldColumnName))
						.findFirst()
						.get();
		}
		return null;
	}
}
