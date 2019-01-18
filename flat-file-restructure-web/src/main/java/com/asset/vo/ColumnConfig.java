package com.asset.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Use this value object to build a 
 * single representation of the new column and
 * old column combination.
 */
@Data
@AllArgsConstructor
public class ColumnConfig {
	private String oldColumnName;
	private String newColumnName;
	private int columnIndex;
}
