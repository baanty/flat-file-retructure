package com.asset.service;

import static com.asset.util.Constants.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.asset.vo.AcceptableIdsCollection;
import com.asset.vo.ColumnConfig;
import com.asset.vo.ColumnConfigCollection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileReaderWriterService {

	@Value(value = "${input.file.location}")
	String inputFileLocation;

	@Value(value = "${output.file.location}")
	String outputFileLocation;

	@Value(value = "${input.file.name}")
	String inputFileName;

	@Value(value = "${output.file.name}")
	String outputFileName;
	
	private String cellSeparator = TAB;
	
	@Autowired
	private AcceptableIdsCollection acceptableIdsCollection;
	
	@Autowired
	private ColumnConfigCollection columnConfigCollection;
	
	public void readInputAndGenerateOutputFile() {
		
		String seperator = File.separator;
		
		inputFileLocation = inputFileLocation.endsWith(seperator) ? inputFileLocation : inputFileLocation + seperator;
		String inputFileQualifiedName = inputFileLocation + inputFileName;
		
		outputFileLocation = outputFileLocation.endsWith(seperator) ? outputFileLocation : outputFileLocation + seperator;
		String outputFileQualifiedName = outputFileLocation + outputFileName;

		try ( FileInputStream inputStream= new FileInputStream(inputFileQualifiedName);
			  FileOutputStream outputStream= new FileOutputStream(outputFileQualifiedName);
			  Scanner sc = new Scanner(inputStream, "UTF-8");
				) {
		    int rowIndex = 0;
		    
		    while (sc.hasNextLine()) {
		    	String line = sc.nextLine();
		    	int numberOfColumns;
	    		String[] cellValues = line.split(cellSeparator);
	    		numberOfColumns = cellValues.length;		    	

	    		for (int columnIndex = 0 ; columnIndex < numberOfColumns ; columnIndex++) {
	    			String cellValue = cellValues[columnIndex];
	    		
	    			if (rowIndex == 0) { /* Process the main header */
	    				ColumnConfig columnConfig = columnConfigCollection.getColumnConfigWithOldName(cellValue);
	    				
	    				if (columnConfig == null) {
	    					continue;
	    				}
	    				columnConfigCollection.getColumnConfigWithOldName(cellValue).setColumnIndex(columnIndex);
	    				String header = columnConfigCollection.isColumnRenamed(cellValue) ? columnConfig.getNewColumnName() : cellValue ;
	    				outputStream.write(header.getBytes());
	    				outputStream.write(seperator.getBytes());
	    				continue;
	    			}
	    			String oldId = cellValues[0]; /* It is assumed that the id is at the first column. */
	    			
	    			if (acceptableIdsCollection.oldIdExists(oldId)) {
	    				outputStream.write(cellValue.getBytes());
	    				outputStream.write(seperator.getBytes());
	    			}
	    		}
	    		
	    		outputStream.flush();
		        rowIndex++;
		    }
		    
		} catch (IOException exception) {
			log.error("Inputoutput error occured. Detail is given in log");
			throw new RuntimeException(exception);
		}
	}
}
