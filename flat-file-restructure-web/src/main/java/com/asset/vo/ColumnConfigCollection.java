package com.asset.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Use this class to hold all the columns configurations of the 
 * first properties file.
 * 
 * @author pijush
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
	 * @param columnConfig : Pass the {@link ColumnConfig} object to
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
	 * @param columnConfig : It is the {@link String}
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
	
	
	/**
	 * Use this method to get a column configuration
	 * value object, which has a matching column index as
	 * the passed parameter.
	 *  
	 * @param columnConfig : It is the {@link integer}
	 * object, that has the column index of the input.
	 * 
	 * 
	 */
	public ColumnConfig getColumnConfigWithColumnIndex(int columnIndex) {
		boolean columnConfigExists = data.stream().anyMatch(config -> config.getColumnIndex() == columnIndex );
		
		if (columnConfigExists) {
			return data
						.stream()
						.filter(config -> config.getColumnIndex() == columnIndex )
						.findFirst()
						.get();
		}
		return null;
	}
	
	
	/**
	 * Use this method to return the data as an unmodifiable 
	 * collection.
	 * @return : Gives the unmodifiable collection of column 
	 * configurations.
	 */
	public synchronized List<ColumnConfig> getAllColumnConfigs() {
		return Collections.unmodifiableList(data);
	}
	
	/**
	 * Use this method to check if the column is really renamed.
	 * @param oldColumnName : The cold column name, which is checked for 
	 * renaming.
	 * 
	 * @return : The boolean to check if the old column exists.
	 */
	public boolean isColumnRenamed(String oldColumnName) {
		
		if (oldColumnName != null && !CollectionUtils.isEmpty(data)) {
			return getAllOldColumnNames().contains(oldColumnName);
		}
		return false;
	}
	
	/**
	 * Use this method to get all the
	 * old column names of the Column COllections Value object.
	 * 
	 * @return : A {@link List} of {@link String} objects. These are the old
	 * column names.
	 */
	public List<String> getAllOldColumnNames() {
		
		if (!CollectionUtils.isEmpty(data)) {
			return data
					.stream()
					.filter(config -> (
							config != null &&
							StringUtils.hasText(config.getOldColumnName())) )
					.map(ColumnConfig::getOldColumnName)
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
		
	}
}
