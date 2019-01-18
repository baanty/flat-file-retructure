package com.asset.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.asset.vo.ColumnConfig;
import com.asset.vo.ColumnConfigCollection;

import lombok.extern.slf4j.Slf4j;

/**
 * This {@link Configuration} class loads all the
 * beans of the application. The beasn will be used to read
 * and write the data.
 * 
 * @author Pijush
 *
 */
@Slf4j
@Configuration
public class PropertiesFileConfiguration {

	@Autowired
	Environment environment;
	
	@Value("${column.name.converter.file}")
	String columnNameConverterFile;
	
	/**
	 * This Spring call back method will be used  to
	 * load the {@link ColumnConfigCollection}s from 
	 * the properties file.
	 * 
	 * @return : The {@link ColumnConfigCollection} object, which is
	 * used as the repository of the firsp properties file.
	 * 
	 */
	@Bean
	public ColumnConfigCollection loadColumnNameConfiguration() {
		
		ColumnConfigCollection columnConfigCollection = new ColumnConfigCollection();
		try {
			InputStream input = getClass().getClassLoader().getResourceAsStream(columnNameConverterFile);
			Properties properties = new Properties();
			properties.load(input);
			int index = 0; 
			for (Entry<Object, Object> entry : properties.entrySet()) {
				ColumnConfig columnConfig = new ColumnConfig(entry.getKey().toString(), entry.getValue().toString(), index);
				columnConfigCollection.addColumnConfig(columnConfig);
				index++;
			}
		} catch (IOException exception) {
			log.error("The properties file can not be loaded");
			throw new RuntimeException(exception);
		}
		return columnConfigCollection;
		
	}
}
