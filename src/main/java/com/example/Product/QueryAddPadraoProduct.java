package com.example.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.Connect.Database;

public class QueryAddPadraoProduct {
	public void queryAddPadraoProduct(Connection connection, String tableName) throws SQLException {
		String update = "UPDATE ";
		try {
			PreparedStatement addStatement = connection
					.prepareStatement(update + tableName + " SET measure_unit = 'u'");
			addStatement.addBatch(update + tableName + " SET print_production = 1 ");
			addStatement.addBatch(update + tableName + " SET panel = 1 ");
			addStatement.addBatch(update + tableName + " SET active = 1 ");
			addStatement.addBatch(update + tableName + " SET delivery = 1 ");
			addStatement.addBatch(update + tableName + " SET card = 1 ");
			addStatement.addBatch(update + tableName + " SET hall_table = 1 ");
			addStatement.addBatch(update + tableName + " SET balcony  = 1 ");
			addStatement.addBatch(update + tableName + " SET parameters  = '' ");
			addStatement.execute();
			addStatement.executeBatch();
			System.out.println("Query padrão executada");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
