/**
 * 
 */
package com.asset.app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.asset.service.FileReaderWriterService;

/**
 * This is the main application to be by run by the 
 * Spring boot application runner.
 * 
 * @author Pijush
 *
 */
@SpringBootApplication()
public class DataFileRestructureApp {
	
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
        SpringApplication.run(DataFileRestructureApp.class, args);
    }
}
