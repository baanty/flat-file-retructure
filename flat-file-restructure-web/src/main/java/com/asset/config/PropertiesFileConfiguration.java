package com.asset.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.asset.vo.ColumnConfigCollection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class PropertiesFileConfiguration {

	@Autowired
	Environment environment;
	
	@Value("${column.name.converter.file}")
	String columnNameConverterFile;
	
	@Bean
	public ColumnConfigCollection loadColumnNameConfiguration() {
		try {
			InputStream input = getClass().getClassLoader().getResourceAsStream(columnNameConverterFile);
			Properties properties = new Properties();
			properties.load(input);
			properties.stringPropertyNames()
		} catch (IOException exception) {
			log.error("The properties file can not be loaded");
			throw new RuntimeException(exception);
		}
	}
}
