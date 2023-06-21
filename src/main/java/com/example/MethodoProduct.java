package com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.mysql.cj.jdbc.DatabaseMetaData;

public class MethodoProduct {
	private String table;
	private StringBuilder insertQuery = new StringBuilder();
	private List<String> defaultValues = new ArrayList<>(0);
	private int totalColumnsInDatabase;

	public String methodoProduct() throws SQLException {
		Database data = new Database();
		try (Connection connection = data.connectionDatabase()) {
			table = data.getTable();
			String defaultValue = "";

			// Tratamento das colunas
			String[] excludedColumn = getColumnProduct();
			insertQuery.append("INSERT INTO ").append(table).append(" (");
			for (int i = 0; i < excludedColumn.length; i++) {
				insertQuery.append(excludedColumn[i]);
				if (i < excludedColumn.length - 1) {
					insertQuery.append(",");
				}
			}
			DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
			ResultSet resultSet = metaData.getColumns(null, null, table, null);
			totalColumnsInDatabase = excludedColumn.length;
			// Loop de inclusão das demais colunas dentro do banco
			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME");
				if (!columnName.equals("id") && !columnName.equals("validity") && !columnName.equals("deleted_at")
						&& !isExcludedProduct(columnName)) {
					if (totalColumnsInDatabase > 0) {
						insertQuery.append(",");
					}
					insertQuery.append(columnName);
					getDefaultValues().add(defaultValue);
					totalColumnsInDatabase++;
				}
				if (columnName.equals("deleted_at")) {
					break;
				}
			}
			resultSet.close();

			// Tratamento dos placeHolder
			insertQuery.append(")").append(" VALUES (");
			for (int i = 0; i < totalColumnsInDatabase; i++) {
				insertQuery.append("?");
				if (i < totalColumnsInDatabase - 1) {
					insertQuery.append(",");
				}
			}
			insertQuery.append(")");
			connection.close();
		} catch (Error e) {
			throw new RuntimeErrorException(e);
		}
		return insertQuery.toString();
	}

	private String[] getColumnProduct() {
		String[] columnProduct = { "barcode", "name", "cost", "price", "current_stock" };
		return columnProduct;
	}

	private boolean isExcludedProduct(String columnName) {
		String[] excludedColumns = getColumnProduct();
		for (String excludedColumn : excludedColumns) {
			if (columnName.equals(excludedColumn)) {
				return true;
			}
		}
		return false;
	}

	public List<String> getDefaultValues() {
		return defaultValues;
	}

	public int getTotalColumnsInDatabase() {
		return totalColumnsInDatabase;
	}

}
