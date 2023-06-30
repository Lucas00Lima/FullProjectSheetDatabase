package com.example.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.DatabaseMetaData;

public class MethodoCategory {
	private List<String> defaultList = new ArrayList<>();
	private String defaultLists = "";

	public String methodoCategory(Connection connection) {
		String tableName = "category";
		ResultSet resultSet;
		StringBuilder insertQuery = new StringBuilder();
		List<String> columnNames = new ArrayList<>();
		try {
			DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
			resultSet = metaData.getColumns(null, null, tableName, null);
			insertQuery.append("INSERT INTO " + tableName + " (");
			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME");
				columnNames.add(columnName);
				getDefaultList().add(defaultLists);
				if (columnName.equals("kitchen_notes")) {
					break;
				}
			}
			for (int i = 0; i < columnNames.size(); i++) {
				insertQuery.append(columnNames.get(i));
				if (i < columnNames.size() - 1) {
					insertQuery.append(",");
				}
			}
			insertQuery.append(")").append(" VALUES (");
			resultSet.close();
			for (int i = 0; i < columnNames.size(); i++) {
				insertQuery.append("?");
				if (i < columnNames.size() - 1)
					insertQuery.append(",");
			}
			insertQuery.append(")");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertQuery.toString();
	}

	public List<String> getDefaultList() {
		return defaultList;
	}

	public String getDefaultLists() {
		return defaultLists;
	}
}
