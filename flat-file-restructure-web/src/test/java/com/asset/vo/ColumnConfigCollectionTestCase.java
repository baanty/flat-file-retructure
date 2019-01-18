package com.asset.vo;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ColumnConfigCollectionTestCase {
	
	ColumnConfigCollection collection = new ColumnConfigCollection();
	ColumnConfig columnConfig = new ColumnConfig("old21", "new21", 0);
	
	@Before
	public void setUp() {
		collection.addColumnConfig(columnConfig);
	}


	@Test
	public void testGetColumnConfigWithOldName() {
		assertTrue(collection.getColumnConfigWithOldName("old21").equals(columnConfig));
	}

	@Test
	public void testGetColumnConfigWithColumnIndex() {
		assertTrue(collection.getColumnConfigWithColumnIndex(0).equals(columnConfig));
	}

	@Test
	public void testGetAllColumnConfigs() {
		assertTrue(collection.getAllColumnConfigs().contains(columnConfig));
	}

	@Test
	public void testIsColumnRenamed() {
		assertTrue(collection.isColumnRenamed("old21"));
	}

	@Test
	public void testGetAllOldColumnNames() {
		assertTrue(collection.getAllOldColumnNames().contains("old21"));
	}

}
