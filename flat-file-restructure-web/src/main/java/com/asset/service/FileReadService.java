package com.asset.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileReadService {

	@Value(value = "${input.file.location}")
	String inputFileLocation;

	@Value(value = "${output.file.location}")
	String outputFileLocation;

	@Value(value = "${input.file.name}")
	String inputFileName;

	@Value(value = "${output.file.name}")
	String outputFileName;
	
	public void readInputAndGenerateOutputFile() {
		
		String seperator = File.separator;
		
		inputFileLocation = inputFileLocation.endsWith(seperator) ? inputFileLocation : inputFileLocation + seperator;
		String inputFileQualifiedName = inputFileLocation + inputFileLocation;
		
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
		    inputStream = new FileInputStream(inputFileQualifiedName);
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        // System.out.println(line);
		    }
		    // note that Scanner suppresses exceptions
		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (inputStream != null) {
		        inputStream.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
	}
}
