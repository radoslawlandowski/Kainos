package com.example.model;

public class SQLTable {
	
	private String tableName;
	private int numberOfColumns;
	private String[] columnNames;
	private DataTypes[] columnTypes; /* enter all types in order as they appear in the file for each column */
	private int[] sizes;
	
	public SQLTable(String tableName, String[] columnNames, DataTypes[] columnTypes, int[] sizes) {
		this.tableName = tableName;
		this.columnNames = columnNames;
		this.columnTypes = columnTypes;
		this.numberOfColumns = columnNames.length;
		this.sizes = sizes;
	}
	
	public int[] getSizes() {
		return sizes;
	}
	public void setSizes(int[] sizes) {
		this.sizes = sizes;
	}
	public String[] getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	public DataTypes[] getColumnTypes() {
		return columnTypes;
	}
	public void setColumnTypes(DataTypes[] columnTypes) {
		this.columnTypes = columnTypes;
	}

}
