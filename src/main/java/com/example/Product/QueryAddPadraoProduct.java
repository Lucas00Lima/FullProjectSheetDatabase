package com.example.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.Connect.Database;

public class QueryAddPadraoProduct {
	public void queryPadraoProduct(Connection connection, int tableName) throws SQLException {
		String update = "UPDATE ";
		try {
			PreparedStatement addStatement = connection.prepareStatement(update);
			addStatement.addBatch(update + tableName + "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
