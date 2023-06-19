package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.management.RuntimeErrorException;

public class methodoClient {
	private String url;
	private String username;
	private String password;
	private String table;
	private String db;
	public void methodoClient() throws SQLException {
		Database data = new Database();
		data.connectionDatabase();
		url = data.getUrl();
		username = data.getUsername();
		password = data.getPassword();
		db = data.getDb();
		table = data.getTable();
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			String[] excludedColumn = getColumnCliente();
			StringBuilder insertQuery = new StringBuilder("INSERT INTO " + table);
			insertQuery.append(" (");

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

}
