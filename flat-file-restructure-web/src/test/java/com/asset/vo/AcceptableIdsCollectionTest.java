package com.asset.vo;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;



public class AcceptableIdsCollectionTest {

	AcceptableIdsCollection collection = new AcceptableIdsCollection();
	
	@Before
	public void setUpData() {
		collection.addIdConfig("old21", "new21");
	}
	
	@Test
	public void testGetNewValueOfAcceptableId() {
		assertTrue(collection.getNewValueOfAcceptableId("old21").equals("new21"));
	}

	@Test
	public void testGetAllTheOldIds() {
		assertTrue(collection.getAllTheOldIds().contains("old21"));
	}

	@Test
	public void testGetAllTheNewIds() {
		assertTrue(collection.getAllTheNewIds().contains("new21"));
	}

	@Test
	public void testOldIdExists() {
		assertTrue(collection.oldIdExists("old21"));
	}

}
