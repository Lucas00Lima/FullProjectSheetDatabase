package com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.RuntimeErrorException;

import com.mysql.cj.jdbc.DatabaseMetaData;

public class MethodoClient {
	private String table;
	private StringBuilder insertQuery = new StringBuilder();
	private int totalColumnsInDatabase;
	private List<String> defaultValues = new ArrayList<>(0);
	private String defaultValue = "";
	private Set<String> columnNames = new HashSet();

	public void methodoClient() throws SQLException {
		Database data = new Database();
		data.connectionDatabase();
		table = data.getTable();
		try (Connection connection = data.connectionDatabase()) {
			DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
			ResultSet resultSet = metaData.getColumns(null, null, table, null);
			String[] excludedColumns = getColumnCliente();
			insertQuery.append("INSERT INTO ").append(table).append(" (");
			for (int i = 0; i < excludedColumns.length; i++) {
				insertQuery.append(excludedColumns[i]);
				if (i < excludedColumns.length - 1) {
					insertQuery.append(",");
				}
			}
			totalColumnsInDatabase = (excludedColumns.length);

			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME");
				if (!isExcludedClient(columnName)) {
					columnNames.add(columnName);
				}
			}
			for (String columnName : columnNames) {
				if (totalColumnsInDatabase > 0) {
					insertQuery.append(",");
				}
				insertQuery.append(columnName);
				getDefaultValues().add(defaultValue);
				totalColumnsInDatabase++;
			}
			resultSet.close();

			insertQuery.append(")").append(" VALUES (");
			for (int i = 0; i < totalColumnsInDatabase; i++) {
				insertQuery.append("?");
				if (i < totalColumnsInDatabase - 1) {
					insertQuery.append(",");
				}
			}
			insertQuery.append(")");
			connection.close();
			System.out.println(insertQuery.toString());
		} catch (Error e) {
			throw new RuntimeErrorException(e);
		}
	}

	private String[] getColumnCliente() {
		String[] columnClient = { "id", "name", "type", "id_doc_number2", "id_doc_number3", "id_doc_number4",
				"cell_phone", "cell_phone2", "gender", "email", "birthday", "register" };
		return columnClient;
	}

	private boolean isExcludedClient(String columnName) {
		String[] excludedColumns = getColumnCliente();
		for (String excludedColumn : excludedColumns) {
			if (columnName.equals(excludedColumn)) {
				return true;
			}
		}
		return false;
	}

	private String[] getExcludedClient() {
		String[] excludedValues = { "?,?,?,?,?,?,?,?,?,?" };
		return excludedValues;

	}

	public int getTotalColumnsInDatabase() {
		return totalColumnsInDatabase;
	}

	public List<String> getDefaultValues() {
		return defaultValues;
	}
}
