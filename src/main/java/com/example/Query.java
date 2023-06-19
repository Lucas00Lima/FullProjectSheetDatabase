package com.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Query {
	private Connection connection;

	public void query() throws SQLException {
		Database data = new Database();
		try (Connection connection = data.connectionDatabase()) {

		}

	}

}
