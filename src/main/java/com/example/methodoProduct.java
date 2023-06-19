package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.poi.sl.usermodel.Placeholder;

import com.mysql.cj.jdbc.DatabaseMetaData;

public class methodoProduct {
	private String url;
	private String username;
	private String password;
	private String table;
	private String db;
	private StringBuilder insertQuery = new StringBuilder();

	public methodoProduct() throws SQLException {
		Database data = new Database();
		data.connectionDatabase();
		url = data.getUrl();
		username = data.getUsername();
		password = data.getPassword();
		db = data.getDb();
		table = data.getTable();
		String defaultValue = "";
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			// Tratamento das colunas
			String[] excludedColumn = getColumnProduct();
			insertQuery.append("INSERT INTO ").append(table).append(" (");
			for (int i = 0; i < excludedColumn.length; i++) {
				insertQuery.append(excludedColumn[i]);
				if (i < excludedColumn.length - 1) {
					insertQuery.append(",");
				}
			}
			List<String> defaultValues = new ArrayList<>(0);
			DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
			ResultSet resultSet = metaData.getColumns(null, null, table, null);
			int totalColumnsInDatabase = excludedColumn.length;

			// Loop de inclusão das demais colunas dentro do banco
			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME");
				if (!isExcludedProduct(columnName)) {
					if (totalColumnsInDatabase > 0) {
						insertQuery.append(",");
					}
					insertQuery.append(columnName);
					defaultValues.add(defaultValue);
					totalColumnsInDatabase++;
				}
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
			System.out.println(insertQuery.toString());
		} catch (Error e) {
			throw new RuntimeErrorException(e);
		}
}

	private String[] getColumnProduct() {
		String[] columnProduct = { "internal_code", "barcode", "name", "category_id", "description", "cost", "price",
				"current_stock", "type", "type2" };
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
}
