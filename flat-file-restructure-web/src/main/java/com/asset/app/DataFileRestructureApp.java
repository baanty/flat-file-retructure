/**
 * 
 */
package com.asset.app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.asset.config.PropertiesFileConfiguration;
import com.asset.service.FileReaderWriterService;

/**
 * This is the main application to be by run by the 
 * Spring boot application runner.
 * 
 * @author Pijush
 *
 */
@SpringBootApplication(scanBasePackageClasses = {PropertiesFileConfiguration.class},
						scanBasePackages = {"com.asset.service"})
public class DataFileRestructureApp extends SpringBootServletInitializer {
	
	@Autowired
	FileReaderWriterService service;

	
	/**
	 * The {@link PostConstruct } ensures that the
	 * file copier is actually run at the startup of the 
	 * web application.
	 */
	@PostConstruct
	public void startApplication(){
		service.readInputAndGenerateOutputFile();
	}
	
	/**
	 * The main runner of the application.
	 * @param args
	 */
    public static void main(String[] args) {
    	ConfigurableApplicationContext context = SpringApplication.run(DataFileRestructureApp.class, args);
        SpringApplication.exit(context, () -> 0);
    }

}
