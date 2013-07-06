package de.rmuselmann.entity.impl;

public class TableConfiguration {

	private String reflectionName;
	private String columnName;
	private long id;

	public TableConfiguration(long id, String columnName, String reflectionName) {
		this.id = id;
		this.columnName = columnName;
		this.reflectionName = reflectionName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getReflectionName() {
		return reflectionName;
	}

	public void setReflectionName(String reflectionName) {
		this.reflectionName = reflectionName;
	}

}
