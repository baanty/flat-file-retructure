package com.asset.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Use this value object to build a single representation of
 * the main input file. The column value is the 
 * value of the column. But the {@link ColumnConfig} represents the
 * column to which the value belongs.
 * 
 */
@Data
@AllArgsConstructor
public class DataFileCell {
	private String columnValue;
	private ColumnConfig columnconfig;
	private int rowNumber;
	private String oldIdValue;
}
