package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.mysql.cj.jdbc.DatabaseMetaData;

public class methodon {
	private String url;
	private String username;
	private String password;
	private String table;
	private String db;

	public void method1() throws SQLException {
		Database data = new Database();
		data.connectionDatabase();
		url = data.getUrl();
		username = data.getUsername();
		password = data.getPassword();
		db = data.getDb();
		table = data.getTable();
		String defaultValue = "";
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			String[] excludedColumn = getExcludedColumn();
			StringBuilder insertQuery = new StringBuilder("INSERT INTO " + table);
			insertQuery.append(" (");
			for (int i = 0; i < excludedColumn.length; i++) {
				insertQuery.append(excludedColumn[i]);
				if(i < excludedColumn.length - 1) {
				insertQuery.append(", ");	
				}
			}
			insertQuery.append(") ");
			System.out.println(insertQuery);

			String[] excludedValue = getExcludedValues();			
			StringBuilder valuePlaceholders = new StringBuilder("VALUES");
			valuePlaceholders.append(" (");
			for (int i = 0; i < excludedValue.length; i++) {
				valuePlaceholders.append(excludedValue[i]);
				if(i < excludedColumn.length - 1) {
					valuePlaceholders.append(",");
				}
			}
			valuePlaceholders.append(")");
			System.out.println(valuePlaceholders);
			
			List<String> defaultValues = new ArrayList<>(0);
			DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
			ResultSet resultSet = metaData.getColumns(null, null, table, null);
			int totalColumnsInDatabase = 10;
			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME");
				if (!isExcludedColumn(columnName)) {
					if (totalColumnsInDatabase > 0) {
						insertQuery.append(",");
						valuePlaceholders.append(",");
					}
					insertQuery.append(columnName);
					valuePlaceholders.append("?");
					defaultValues.add(defaultValue);
					totalColumnsInDatabase++;
				}
			}
			resultSet.close();
			insertQuery.append(")");
			valuePlaceholders.append(")");
			insertQuery.append(valuePlaceholders);
		} catch (Error e) {
			throw new RuntimeErrorException(e);
		}
	}
	private String[] getExcludedColumn() {
		String[] excludedColumn = { "internal_code", "barcode", "name", "category_id", "description", "cost", "price",
				"current_stock", "type", "type2" };
		return excludedColumn;
	}

	private boolean isExcludedColumn(String columnName) {
		String[] excludedColumns = getExcludedColumn();
		for (String excludedColumn : excludedColumns) {
			if (columnName.equals(excludedColumn)) {
				return true;
			}
		}
		return false;
	}
	
	private String[] getExcludedValues() {
		String[] excludedValues = {"?,?,?,?,?,?,?,?,?,?"};
		return excludedValues;
	}
	
}
