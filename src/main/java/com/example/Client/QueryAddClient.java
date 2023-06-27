package com.example.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryAddClient {
	public void queryAddClient(Connection connection, int tableName, String sheetAcess) {
		if (tableName == 1) {
			String table = "client";
			try {
				String inserAdd = "UPDATE ";
				PreparedStatement addStatement = connection.prepareStatement("UPDATE " + table + " SET phone = ''");
				addStatement.addBatch(inserAdd + table + " SET doc_name = ''");
				addStatement.addBatch(inserAdd + table + " SET owner_name = ''");
				addStatement.addBatch(inserAdd + table + " SET occupation = ''");
				addStatement.addBatch(inserAdd + table + " SET phone2 = ''");
				addStatement.addBatch(inserAdd + table + " SET street_name = ''");
				addStatement.addBatch(inserAdd + table + " SET street_number = ''");
				addStatement.addBatch(inserAdd + table + " SET zipcode = ''");
				addStatement.addBatch(inserAdd + table + " SET complement = ''");
				addStatement.addBatch(inserAdd + table + " SET notes = ''");
				addStatement.addBatch(inserAdd + table + " SET coordinates = ''");
				addStatement.addBatch(inserAdd + table + " SET expiration_day = 10");
				addStatement.addBatch(inserAdd + table + " SET active = 1");
				addStatement.addBatch(inserAdd + table + " SET locked = 1");
				addStatement.addBatch(inserAdd + table + " SET payments = ''");
				addStatement.addBatch(inserAdd + table + " SET external_id = ''");
				addStatement.addBatch(inserAdd + table + " SET badge = ''");
				addStatement.addBatch(inserAdd + table + " SET external_system_id = ''");
				addStatement.executeUpdate();
				addStatement.executeBatch();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
