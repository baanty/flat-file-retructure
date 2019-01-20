package com.asset.service;

import static com.asset.util.Constants.NEW_LINE;
import static com.asset.util.Constants.TAB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.asset.app.DataFileRestructureApp;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest( classes = {DataFileRestructureApp.class})
public class FileReaderWriterServiceTest {
	

	@Autowired
	FileReaderWriterService service;
	
	static String FILE_LOCATION = System.getProperty("java.io.tmpdir");
	
	static String[] INPUT_FILE_CONTENT = new String[] { "COL0"+TAB+"COL1"+TAB+"COL2"+TAB+"COL3",
													  "ID1"+TAB+"VAL11"+TAB+"VAL12"+TAB+"VAL12",
													  "ID2"+TAB+"VAL21"+TAB+"VAL22"+TAB+"VAL23"};

	static String INPUT_FILE_NAME = "input_data.txt";

	static String OUTPUT_FILE_NAME = "output_data.txt"; 
	
	static String INPUT_FILE_FULLY_QUALIFIED_NAME = null;
	
	static String OUTPUT_FILE_FULLY_QUALIFIED_NAME = null;

	@Test
	public void testReadInputAndGenerateOutputFile() {
		service.readInputAndGenerateOutputFile();
		
		if (OUTPUT_FILE_FULLY_QUALIFIED_NAME == null) {
			fail("The output file is  not created.");
			return;
		}
		
		File outputFile = new File(OUTPUT_FILE_FULLY_QUALIFIED_NAME);
		if (!outputFile.exists()) {
			fail("The output file is not created but the fileName is present.");
			return;
		}
		
		try (FileInputStream fis = new FileInputStream(outputFile);
				Scanner scanner = new Scanner(fis, "UTF-8");) {
			
			int expectedOutputLineCount = 2;
			int index = 0;
			while (scanner.hasNextLine()) {
				
				if (index >= expectedOutputLineCount ) {
					fail("There are more than two lines in the output.");
					return;
				}
				
				if (index == 0) {
					String actualFirstLine = scanner.nextLine();
					String[] actualFirstLineArray = actualFirstLine.split(TAB);
					
					assertEquals("Wrong number of headers generated. Actually Generated Header is - " + actualFirstLine , 3, actualFirstLineArray.length);
					
					assertEquals("First Header does not match", "OURID", actualFirstLineArray[0]);
					assertEquals("Second Header does not match", "OURCOL1", actualFirstLineArray[1]);
					assertEquals("Third Header does not match", "OURCOL3", actualFirstLineArray[2]);
				}
				
				if (index == 1) {
					String actualSecondLine = scanner.nextLine();
					String[] actualSecondLineArray = actualSecondLine.split(TAB);
					
					assertEquals("Wrong number of columns generated. Actually Generated data line is - " + actualSecondLine, 3, actualSecondLineArray.length);
					
					assertEquals("First column does not match", "OURID2", actualSecondLineArray[0]);
					assertEquals("Second column does not match", "VAL21", actualSecondLineArray[1]);
					assertEquals("Third column does not match", "VAL23", actualSecondLineArray[2]);
				}
				
				index++;
			}
			
		} catch (IOException exception) {
			log.error("Can not test the application because of File read/write issues.");
			fail("Integration tests failed.");
			throw new RuntimeException(exception);
		} 
		
		log.info("The service is tested sucessfully.");
		assertTrue(true);
	}
	
	@BeforeClass
	public static void setUpAndCopyFiles() {
		FILE_LOCATION = FILE_LOCATION.endsWith(File.separator) ? FILE_LOCATION : FILE_LOCATION + File.separator;
		INPUT_FILE_FULLY_QUALIFIED_NAME = FILE_LOCATION + INPUT_FILE_NAME;
		OUTPUT_FILE_FULLY_QUALIFIED_NAME = FILE_LOCATION + OUTPUT_FILE_NAME;
		
		try (FileOutputStream fileOutputStream = new FileOutputStream(INPUT_FILE_FULLY_QUALIFIED_NAME)) {

			fileOutputStream.write(INPUT_FILE_CONTENT[0].getBytes());
			fileOutputStream.write(NEW_LINE.getBytes());
			fileOutputStream.write(INPUT_FILE_CONTENT[1].getBytes());
			fileOutputStream.write(NEW_LINE.getBytes());
			fileOutputStream.write(INPUT_FILE_CONTENT[2].getBytes());
		} catch (IOException exception) {
			log.error("Can not test the application because of File read/write issues.");
			fail("Integration tests failed.");
			throw new RuntimeException(exception);
		}

	}
	
	@After
	public void closeDownAndRemoveFiles() {
		
		if (INPUT_FILE_FULLY_QUALIFIED_NAME != null) {
			File tempInputFile = new File(INPUT_FILE_FULLY_QUALIFIED_NAME);
			
			if (tempInputFile.exists()) {
				tempInputFile.delete();
			}
		}
		
		if (OUTPUT_FILE_FULLY_QUALIFIED_NAME != null) {
			File tempOutputFile = new File(OUTPUT_FILE_FULLY_QUALIFIED_NAME);
			
			if (tempOutputFile.exists()) {
				tempOutputFile.delete();
			}
		}
	}

}
