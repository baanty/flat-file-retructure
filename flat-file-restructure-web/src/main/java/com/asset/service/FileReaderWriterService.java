package com.asset.service;

import static com.asset.util.Constants.NEW_LINE;
import static com.asset.util.Constants.TAB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	
	private String lineSeparator = NEW_LINE;
	
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
		    List<Integer> renamedColumnIndexes = new ArrayList<Integer>();
		    
		    while (sc.hasNextLine()) {
		    	String line = sc.nextLine();
		    	int numberOfColumns;
	    		String[] cellValues = line.split(cellSeparator);
	    		numberOfColumns = cellValues.length;		    	
    			String oldId = cellValues[0]; /* It is assumed that the id is at the first column. */

    			boolean writeLineBreak = rowIndex == 0 ||
    					                 acceptableIdsCollection.oldIdExists(oldId);
	    		for (int columnIndex = 0 ; columnIndex < numberOfColumns ; columnIndex++) {
	    			String cellValue = cellValues[columnIndex];
	    		
	    			if (rowIndex == 0) { /* Process the main header */
	    				ColumnConfig columnConfig = columnConfigCollection.getColumnConfigWithOldName(cellValue);
	    				
	    				if (columnConfig == null) {
	    					continue;
	    				}
	    				renamedColumnIndexes.add(columnIndex);
	    				columnConfigCollection.getColumnConfigWithOldName(cellValue).setColumnIndex(columnIndex);
	    				String header = columnConfigCollection.isColumnRenamed(cellValue) ? columnConfig.getNewColumnName() : cellValue ;
	    				outputStream.write(header.getBytes());
	    				outputStream.write(cellSeparator.getBytes());
	    				continue;
	    			}
	    			
	    			if (acceptableIdsCollection.oldIdExists(oldId) && renamedColumnIndexes.contains(columnIndex)) {
	    				String cellToWrite = columnIndex == 0 ? acceptableIdsCollection.getNewValueOfAcceptableId(cellValue) : cellValue ;
	    				outputStream.write(cellToWrite.getBytes());
	    				outputStream.write(cellSeparator.getBytes());
	    			}
	    		}
	    		
	    		if (writeLineBreak) {
	    			outputStream.write(lineSeparator.getBytes());	
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
